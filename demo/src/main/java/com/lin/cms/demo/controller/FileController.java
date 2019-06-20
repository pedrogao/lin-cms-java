package com.lin.cms.demo.controller;

import com.lin.cms.core.exception.*;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.demo.service.FileService;
import com.lin.cms.demo.view.UploadFileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cms/file")
@ControllerAdvice
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public List<UploadFileView> upload(HttpServletRequest request) throws NotFound, Parameter, FileExtension, FileTooLarge, FileTooMany {
        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
        MultiValueMap<String, MultipartFile> fileMap = multipartHttpServletRequest.getMultiFileMap();
        List<UploadFileView> res = fileService.upload(fileMap);
        return res;
    }

}
