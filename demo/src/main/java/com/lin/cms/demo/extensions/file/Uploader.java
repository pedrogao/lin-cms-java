package com.lin.cms.demo.extensions.file;

import com.lin.cms.demo.bo.UploadFileBO;
import com.lin.cms.exception.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {
    List<UploadFileBO> upload(MultiValueMap<String, MultipartFile> fileMap);
}
