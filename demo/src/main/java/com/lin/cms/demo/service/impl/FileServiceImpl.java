package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.extensions.file.Uploader;
import com.lin.cms.demo.mapper.FileMapper;
import com.lin.cms.demo.model.FileDO;
import com.lin.cms.demo.service.FileService;
import com.lin.cms.demo.view.UploadFileVO;
import com.lin.cms.base.service.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class FileServiceImpl extends AbstractService<FileDO> implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private Uploader uploader;

    @Override
    public List<UploadFileVO> upload(MultiValueMap<String, MultipartFile> fileMap) throws Exception {
        List<UploadFileVO> res = uploader.upload(fileMap);
        return res;
    }

}
