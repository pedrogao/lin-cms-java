package com.lin.cms.demo.v2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lin.cms.demo.extensions.file.File;
import com.lin.cms.demo.extensions.file.Uploader;
import com.lin.cms.demo.v2.mapper.FileMapper;
import com.lin.cms.demo.v2.model.FileDO;
import com.lin.cms.demo.v2.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("fileServiceImpl-v2")
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDO> implements FileService {

    @Autowired
    private Uploader uploader;

    /**
     * 为什么不做批量插入
     * 1. 文件上传的数量一般不多，3个左右
     * 2. 批量插入不能得到数据的id字段，不利于直接返回数据
     * 3. 批量插入也仅仅只是一条sql语句的事情，如果真的需要，可以自行尝试一下
     */
    @Override
    public List<FileDO> upload(MultiValueMap<String, MultipartFile> fileMap) {
        List<File> files = uploader.upload(fileMap, file -> !this.checkFileExistByMd5(file.getMd5()));
        List<FileDO> res = files.stream().map(file -> {
            FileDO fileDO = new FileDO();
            BeanUtil.copyProperties(file, fileDO);
            this.getBaseMapper().insert(fileDO);
            return fileDO;
        }).collect(Collectors.toList());
        return res;
    }

    @Override
    public boolean checkFileExistByMd5(String md5) {
        return this.getBaseMapper().selectCountByMd5(md5) > 0;
    }
}
