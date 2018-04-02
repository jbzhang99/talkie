package com.meta.dao.user;

import com.meta.model.user.User;
import com.meta.model.user.UserGroup;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lhq on 2017/10/23.
 */
public interface UserGroupReposirory extends BaseRepository<UserGroup, Long> {

List<UserGroup>   findByGroupId(@Param("groupId") Long groupId);

}
