package com.lin.cms.demo.extensions.file;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {
    List<File> upload(MultiValueMap<String, MultipartFile> fileMap);
}
