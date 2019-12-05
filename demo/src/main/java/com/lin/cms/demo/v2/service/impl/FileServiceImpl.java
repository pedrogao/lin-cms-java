package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.v2.mapper.FileMapper;
import com.lin.cms.demo.v2.model.FileDO;
import com.lin.cms.demo.v2.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("fileServiceImpl-v2")
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDO> implements FileService {

}
