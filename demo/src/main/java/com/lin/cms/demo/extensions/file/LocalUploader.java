package com.lin.cms.demo.extensions.file;

import com.lin.cms.demo.mapper.FileMapper;
import com.lin.cms.demo.model.FileDO;
import com.lin.cms.demo.bo.UploadFileBO;
import com.lin.cms.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocalUploader implements Uploader {
    @Autowired
    private FileMapper fileMapper;

    @Value("${lin.cms.file.store-dir}")
    private String dir;

    @Value("${lin.cms.file.single-limit}")
    private String singleLimit;

    // @Value("${file.total-limit}")
    // private Integer totalLimit;

    @Value("${lin.cms.file.nums}")
    private Integer nums;

    @Value("${lin.cms.file.exclude}")
    private String[] exclude;

    @Value("${lin.cms.file.include}")
    private String[] include;

    @Value("${lin.cms.file.domain}")
    private String domain;

    private long slBytes = 0;

    private String absDir;

    public List<UploadFileBO> upload(MultiValueMap<String, MultipartFile> fileMap) throws NotFound, Parameter, FileTooMany, FileExtension, FileTooLarge {

        if (fileMap.isEmpty()) {
            throw new NotFound("未找到文件");
        }
        int size = fileMap.size();
        if (size > this.nums) {
            throw new FileTooMany("文件过多");
        }

        this.parseDataSize();
        this.initStoreDir();

        MultipartFile file;
        BufferedOutputStream stream;
        List<UploadFileBO> res = new ArrayList<>();
        String[] keys = fileMap.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; ++i) {
            List<MultipartFile> multipartFiles = fileMap.get(keys[i]);
            for (int j = 0; j < multipartFiles.size(); j++) {
                file = multipartFiles.get(j);
                if (!file.isEmpty()) {
                    byte[] bytes;
                    try {
                        bytes = file.getBytes();
                    } catch (Exception e) {
                        throw new Parameter("读取文件数据失败");
                    }
                    // 写到了本地
                    String originName = file.getOriginalFilename();
                    String ext = this.getExt(originName);
                    // 检测扩展
                    if (!this.checkExt(ext)) {
                        throw new FileExtension(ext + "文件类型不支持");
                    }
                    // 检测单个大小
                    if (bytes.length > this.slBytes) {
                        throw new FileTooLarge(originName + "文件不能超过" + this.singleLimit);
                    }
                    // 生成文件的md5值
                    String md5 = this.getFileMD5(bytes);
                    // 检查文件是否存在
                    FileDO exist = this.checkFileIsExist(md5);
                    if (exist != null) {
                        UploadFileBO item = this.genFileView(exist, keys[i]);
                        res.add(item);
                    } else {
                        // 随机生成名字
                        String uuid = UUID.randomUUID().toString();
                        String newFilename = uuid + ext;
                        String storePath = FileSystems.getDefault().getPath(this.absDir, newFilename).toAbsolutePath().toString();
                        try {
                            stream = new BufferedOutputStream(new FileOutputStream(
                                    new java.io.File(storePath)));
                            stream.write(bytes);
                            stream.close();
                        } catch (Exception e) {
                            throw new Parameter("读取文件数据失败");
                        }
                        FileDO record = new FileDO();
                        record.setMd5(md5);
                        record.setName(newFilename);
                        record.setPath(storePath);
                        record.setSize(bytes.length);
                        // type = 1 时 为本地，= 2 时 为remote
                        // record.setType();
                        record.setExtension(ext);
                        fileMapper.insertSelective(record);
                        UploadFileBO item = this.genFileView(record, keys[i]);
                        res.add(item);
                    }
                }
            }
        }
        return res;
    }

    private UploadFileBO genFileView(FileDO record, String key) {
        UploadFileBO item = new UploadFileBO();
        item.setId(record.getId());
        item.setKey(key);
        String url = getServerDir() + record.getName();
        item.setUrl(url);
        return item;
    }

    private FileDO checkFileIsExist(String md5) {
        FileDO exist = fileMapper.findOneByMd5(md5);
        if (exist == null) {
            return null;
        }
        return exist;
    }

    private String getFileMD5(byte[] bytes) {
        String md5 = DigestUtils.md5DigestAsHex(bytes);
        return md5;
    }

    private boolean checkExt(String ext) {
        // 如果两者都有取 include，有一者则用一者
        if (this.include.length > 0 && this.exclude.length > 0) {
            return this.findInInclude(ext);
        } else if (this.include.length > 0 && this.exclude.length == 0) {
            // 有include，无exclude
            return this.findInInclude(ext);
        } else if (this.include.length == 0 && this.exclude.length > 0) {
            // 有exclude，无include
            return this.findInExclude(ext);
        } else {
            // 二者都没有
            return true;
        }
    }

    private boolean findInInclude(String ext) {
        for (int i = 0; i < this.include.length; i++) {
            if (this.include[i].equals(ext)) {
                return true;
            }
        }
        return false;
    }

    private boolean findInExclude(String ext) {
        for (int i = 0; i < this.exclude.length; i++) {
            if (this.exclude[i].equals(ext)) {
                return true;
            }
        }
        return false;
    }

    private String getExt(String filename) {
        int index = filename.lastIndexOf('.');
        String ext = filename.substring(index);
        return ext;
    }

    private void parseDataSize() {
        if (this.slBytes > 0) {
            return;
        }
        DataSize singleLimitData = DataSize.parse(singleLimit);
        long slBytes = singleLimitData.toBytes();
        this.slBytes = slBytes;
    }

    public void initStoreDir() {
        if (absDir != null) {
            return;
        }
        if (this.isAbsolute(this.dir)) {
            this.absDir = this.dir;
        } else {
            String cmd = System.getProperty("user.dir");
            Path path = FileSystems.getDefault().getPath(cmd, this.dir);
            this.absDir = path.toAbsolutePath().toString();
        }
        new java.io.File(this.absDir).mkdirs();
    }

    private boolean isAbsolute(String str) {
        Path path = FileSystems.getDefault().getPath(str);
        return path.isAbsolute();
    }

    private String getServerDir() {
        String serverDir = this.domain + this.dir;
        return serverDir;
    }

    public String getAbsDir() {
        return absDir;
    }

    public void setAbsDir(String absDir) {
        this.absDir = absDir;
    }
}
