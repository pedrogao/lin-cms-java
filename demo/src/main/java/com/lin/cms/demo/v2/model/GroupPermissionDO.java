package com.lin.cms.demo.v2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pedro
 * @since 2019-11-30
 */
@TableName("lin_group_permission")
@Data
public class GroupPermissionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 权限id
     */
    private Long permissionId;

    public GroupPermissionDO(Long groupId, Long permissionId) {
        this.groupId = groupId;
        this.permissionId = permissionId;
    }
}
