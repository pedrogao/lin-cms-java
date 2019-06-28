package com.lin.cms.demo.api.cms;

import com.lin.cms.core.exception.*;
import com.lin.cms.demo.view.UploadFileVO;
import com.lin.cms.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cms/file")
public class FileApi {

    @Autowired
    private FileService fileService;

    @PostMapping("/")
    public List<UploadFileVO> upload(HttpServletRequest request) throws NotFound, Parameter, FileExtension, FileTooLarge, FileTooMany {
        MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
        MultiValueMap<String, MultipartFile> fileMap = multipartHttpServletRequest.getMultiFileMap();
        List<UploadFileVO> res = fileService.upload(fileMap);
        return res;
    }

}
