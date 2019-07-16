package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.AuthDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface AuthRepository extends CrudRepository<AuthDO, Integer> {
    AuthDO findTopByGroupIdAndAuthAndModule(Integer groupId, String auth, String module);

    void deleteAllByGroupIdAndAuthIsIn(Integer groupId, List<String> auths);

    AuthDO findTopByGroupIdAndAuth(Integer groupId, String auth);

    void deleteByGroupId(Integer groupId);

    @Query(value = "SELECT a.auth,a.module FROM lin_auth a WHERE a.group_id=?1",
            nativeQuery = true)
    List<Map> findByGroupId(Integer groupId);
}
