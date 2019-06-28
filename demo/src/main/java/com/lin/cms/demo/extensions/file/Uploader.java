package com.lin.cms.demo.extensions.file;

import com.lin.cms.core.exception.*;
import com.lin.cms.demo.view.UploadFileVO;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Uploader {
    List<UploadFileVO> upload(MultiValueMap<String, MultipartFile> fileMap) throws NotFound, Parameter, FileTooMany, FileExtension, FileTooLarge;
}
