package com.lin.cms.demo.service;

import com.lin.cms.core.exception.*;
import com.lin.cms.demo.db.Service;
import com.lin.cms.demo.model.File;
import com.lin.cms.demo.view.UploadFileResult;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
public interface FileService extends Service<File> {
    List<UploadFileResult> upload(MultiValueMap<String, MultipartFile> fileMap) throws NotFound, Parameter, FileTooMany, FileExtension, FileTooLarge;
}
