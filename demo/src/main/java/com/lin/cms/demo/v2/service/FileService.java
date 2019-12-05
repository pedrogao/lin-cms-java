package com.lin.cms.demo.v2.service;

import com.lin.cms.demo.extensions.file.File;
import com.lin.cms.demo.v2.model.FileDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
public interface FileService extends IService<FileDO> {
    List<File> upload(MultiValueMap<String, MultipartFile> fileMap);
}
