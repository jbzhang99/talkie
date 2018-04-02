package com.meta.feignfallback.user;

import com.meta.Result;
import com.meta.feignclient.user.UserClient;
import com.meta.model.group.MGroupUser;
import com.meta.model.user.MUser;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * create by lhq
 * create date on  18-2-11上午9:35
 *
 * @version 1.0
 **/
@Component
public class UserFallBack implements FallbackFactory<UserClient> {
    private static final Logger log = LoggerFactory.getLogger(UserFallBack.class);
    @Override
    public UserClient create(Throwable cause) {
        return new UserClient(){

            @Override
            public Result<MUser> loginByAccount(String account, String password) {
                System.out.println("fffffffff");
                return null;
            }

            @Override
            public Result<MUser> search(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MUser> findByAccount(String account) {
                return null;
            }

            @Override
            public Integer countCompayByParentId(Long parentId, Integer isDel) {
                return null;
            }

            @Override
            public Result<MUser> create(MUser mUser) {
                return null;
            }

            @Override
            public Result delete(Long id, String merchantLevel, Long currentUserId) {
                return null;
            }

            @Override
            public Result modifyUserById(Long id, String status) {
                return null;
            }

            @Override
            public Result<MUser> searchByAccount(String filters, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MUser> get(Long id) {
                return null;
            }

            @Override
            public Result modifyPassword(Long id, String password) {
                return null;
            }

            @Override
            public Result<MUser> findByAccountAndParentId(String account, Long parentId) {
                return null;
            }

            @Override
            public Result<MGroupUser> findWaitUserGroup(Long parentId, String groupId) {
                return null;
            }

            @Override
            public Result<MGroupUser> findAlreadyUserGroup(Long groupId) {
                return null;
            }

            @Override
            public Result<MUser> findCanAddFriend(Long id, Long parentId) {
                return null;
            }

            @Override
            public Result<MUser> findAlreadyAddFriend(Long id) {
                return null;
            }

            @Override
            public Result delById(Long id) {
                return null;
            }

            @Override
            public Result<MUser> batchSave(MUser[] mUsers) throws Exception {
                return null;
            }

            @Override
            public Result<MUser> searchUserAndGroup(String filters, Long currGroupList, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Result<MUser> searchUserAndGroupAndDate(String filters, Long currGroupList, String GT_createDate, String LT_createDate, String sorts, int size, int page) {
                return null;
            }

            @Override
            public Boolean queryUserOnlineStatus(Long userId) {
                return null;
            }

            @Override
            public Result<MUser> searchUserIsOnline(Long currGroupList, String filters) {
                return null;
            }

            @Override
            public Result<MUser> iccIdUserBatchSave(MUser[] users) {
                return null;
            }
        };
    }
}
