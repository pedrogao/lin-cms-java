package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface UserRepository extends SoftCrudRepository<UserDO, Integer> {

    // native sql 查询支持不好，得自己去convert，SPql的语法太麻烦，学习成本太高
    // jpa 的 page 是从 0 开始序列的，而 mybatis-pagehelper是从 1 开始序列的
    @Query(value = "SELECT u.*,g.name as group_name FROM lin_user u " +
            "LEFT JOIN lin_group g ON u.group_id = g.id " +
            "WHERE u.delete_time IS NULL AND u.admin=1",
            countQuery = "SELECT COUNT(*) FROM lin_user u WHERE u.admin=1 AND u.delete_time IS NULL",
            nativeQuery = true)
    Page<Map> findUsersAndGroupName(Pageable pageable);

    @Query(value = "SELECT u.*,g.name as group_name FROM lin_user u " +
            "LEFT JOIN lin_group g ON u.group_id = g.id " +
            "WHERE u.delete_time IS NULL AND u.admin=1 AND u.group_id=?1",
            countQuery = "SELECT COUNT(*) FROM lin_user u WHERE u.admin=1 AND u.group_id=?1 AND u.delete_time IS NULL",
            nativeQuery = true)
    Page<Map> findUsersAndGroupName(Integer groupId, Pageable pageable);

    UserDO findByIdAndDeleteTimeIsNull(Integer id);

    UserDO findByEmailAndDeleteTimeIsNull(String email);

    UserDO findFirstByGroupIdAndDeleteTimeIsNull(Integer groupId);
}
