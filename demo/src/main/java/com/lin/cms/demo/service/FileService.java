package com.lin.cms.demo.service;

import com.lin.cms.demo.model.FileDO;
import com.lin.cms.demo.view.UploadFileVO;
import com.lin.cms.base.service.base.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
public interface FileService extends Service<FileDO> {
    List<UploadFileVO> upload(MultiValueMap<String, MultipartFile> fileMap) throws Exception;
}
