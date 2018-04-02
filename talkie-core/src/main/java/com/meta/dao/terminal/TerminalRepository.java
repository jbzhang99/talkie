package com.meta.dao.terminal;

import com.meta.model.terminal.Terminal;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by llin on 2017/9/29.
 */
public interface TerminalRepository extends BaseRepository<Terminal, Long> {


    /**
     * 根据UserId删除对应终端
     * @param id
     */
    public void deleteByUser_Id(@Param("id")Long id);

    /**
     * 根据User account 对应终端
     * @param account
     */
    public Terminal findByUser_Account(@Param("account")String account);




}
