package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.SpuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Spu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface ISpuService extends IService<Spu> {

    void createSpu(SpuCreateOrUpdateDTO dto);

    void updateSpu(SpuCreateOrUpdateDTO dto, Long id);

    void deleteSpu(Long id);

    PageResult<Spu> getSpuByPage(Long count, Long page);
}
