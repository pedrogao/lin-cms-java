package com.lin.cms.demo.controller;

import com.lin.cms.core.exception.HttpException;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cms/file")
public class FileController {

    @Value("${file.store-dir}")
    private String dir;

    @Value("${file.single-limit}")
    private String singleLimit;

    // @Value("${file.total-limit}")
    // private Integer totalLimit;

    @Value("${file.nums}")
    private Integer nums;

    @Value("${file.exclude}")
    private String[] exclude;

    @Value("${file.include}")
    private String[] include;

    private long slBytes = 0;

    @PostMapping("/")
    public Result upload(HttpServletRequest request) throws NotFound, Parameter {
        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
        MultiValueMap<String, MultipartFile> fileMap = multipartHttpServletRequest.getMultiFileMap();
        if (fileMap.isEmpty()) {
            throw new NotFound("未找到文件");
        }
        int size = fileMap.size();
        if (size > this.nums) {
            throw new Parameter("文件过多");
        }

        this.parseDataSize();

        MultipartFile file;
        BufferedOutputStream stream;
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
                        throw new Parameter("类型不支持");
                    }
                    // 检测单个大小
                    if (bytes.length > this.slBytes) {
                        throw new Parameter(originName + "太大了!");
                    }
                    // 随机生成名字
                    String uuid = UUID.randomUUID().toString();
                    String newFilename = uuid + ext;

                    try {
                        stream = new BufferedOutputStream(new FileOutputStream(
                                new File(dir + newFilename)));
                        stream.write(bytes);
                        stream.close();
                    } catch (Exception e) {
                        throw new Parameter("读取文件数据失败");
                    }
                }
            }
        }
        // TODO: 总体文件太大的异常特殊返回
        return ResultGenerator.genSuccessResult("上传文件成功！");
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
}
