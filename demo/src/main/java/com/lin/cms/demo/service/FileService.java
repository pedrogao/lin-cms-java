package com.lin.cms.demo.service;

import com.lin.cms.demo.extensions.file.File;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
public interface FileService {
    List<File> upload(MultiValueMap<String, MultipartFile> fileMap);
}
