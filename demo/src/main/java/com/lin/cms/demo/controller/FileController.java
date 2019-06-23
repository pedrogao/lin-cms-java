package com.lin.cms.demo.controller;

import com.lin.cms.core.exception.*;
import com.lin.cms.demo.service.FileService;
import com.lin.cms.demo.view.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cms/file")
@ControllerAdvice
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public List<UploadFileResult> upload(HttpServletRequest request) throws NotFound, Parameter, FileExtension, FileTooLarge, FileTooMany {
        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
        MultiValueMap<String, MultipartFile> fileMap = multipartHttpServletRequest.getMultiFileMap();
        List<UploadFileResult> res = fileService.upload(fileMap);
        return res;
    }

}
