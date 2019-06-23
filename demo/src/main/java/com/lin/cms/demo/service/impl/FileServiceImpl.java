package com.lin.cms.demo.service.impl;

import com.lin.cms.core.exception.*;
import com.lin.cms.demo.db.AbstractService;
import com.lin.cms.demo.extensions.file.LocalUploader;
import com.lin.cms.demo.mapper.FileMapper;
import com.lin.cms.demo.model.File;
import com.lin.cms.demo.service.FileService;
import com.lin.cms.demo.view.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class FileServiceImpl extends AbstractService<File> implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private LocalUploader uploader;

    @Override
    public List<UploadFileResult> upload(MultiValueMap<String, MultipartFile> fileMap) throws NotFound, Parameter, FileTooMany, FileExtension, FileTooLarge {
        List<UploadFileResult> res = uploader.upload(fileMap);
        return res;
    }

}
