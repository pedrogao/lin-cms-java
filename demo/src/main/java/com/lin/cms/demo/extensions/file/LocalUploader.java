package com.lin.cms.demo.extensions.file;

import com.lin.cms.demo.v2.model.FileDO;
import com.lin.cms.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LocalUploader implements Uploader {

    @Value("${lin.cms.file.store-dir}")
    private String dir;

    @Value("${lin.cms.file.single-limit}")
    private String singleLimit;

    @Value("${lin.cms.file.nums}")
    private Integer nums;

    @Value("${lin.cms.file.exclude}")
    private String[] exclude;

    @Value("${lin.cms.file.include}")
    private String[] include;

    @Value("${lin.cms.file.domain}")
    private String domain;

    @Value("${spring.profiles.active}")
    private String profile;

    private String remotePrefix = "https://talelin.coding.net/p/sleeve/git/raw/master/assets/";

    private String absDir;

    public List<File> upload(MultiValueMap<String, MultipartFile> fileMap) {
        if (fileMap.isEmpty()) {
            throw new NotFound("未找到文件");
        }
        int size = fileMap.size();
        if (size > this.nums) {
            throw new FileTooMany("文件太多，文件总数不可超过" + this.nums);
        }
        long singleFileLimit = FileUtil.parseSize(this.singleLimit);
        FileUtil.initStoreDir(this.dir);

        MultipartFile file;
        BufferedOutputStream stream;
        List<File> res = new ArrayList<>();

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
                    String ext = FileUtil.getFileExt(originName);
                    // 检测扩展
                    if (!this.checkExt(ext)) {
                        throw new FileExtension(ext + "文件类型不支持");
                    }
                    // 检测单个大小
                    if (bytes.length > singleFileLimit) {
                        throw new FileTooLarge(originName + "文件不能超过" + this.singleLimit);
                    }
                    // 生成文件的md5值
                    String md5 = FileUtil.getFileMD5(bytes);
                    // 检查文件是否存在
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
                    // record.setPath(storePath);
                    record.setSize(bytes.length);
                    // type = 1 时 为本地，= 2 时 为remote
                    record.setType(FileConsts.REMOTE);
                    record.setPath(remotePrefix + newFilename);
                    record.setExtension(ext);
                    // res.add(record);
                }
            }
        }
        return res;
    }

    private File genFileView(FileDO record, String key) {
        File item = new File();
        item.setKey(key);
        String url;
        if (record.getType() == 1) {
            url = getServerDir() + record.getName();
        } else {
            url = record.getPath();
        }
        item.setUrl(url);
        item.setPath(record.getPath());
        return item;
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


    private String getServerDir() {
        String serverDir = this.domain + this.dir;
        return serverDir;
    }
}
