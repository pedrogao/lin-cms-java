package com.lin.cms.demo.v2.service.impl;

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

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("fileServiceImpl-v2")
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDO> implements FileService {

    @Autowired
    private Uploader uploader;

    @Override
    public List<File> upload(MultiValueMap<String, MultipartFile> fileMap) {
        // List<FileDO> res = uploader.upload(fileMap);
        return null;
    }
}
