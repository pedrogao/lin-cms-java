package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.db.AbstractService;
import com.lin.cms.demo.mapper.FileMapper;
import com.lin.cms.demo.model.File;
import com.lin.cms.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class FileServiceImpl extends AbstractService<File> implements FileService {
    @Autowired
    private FileMapper fileMapper;

}
