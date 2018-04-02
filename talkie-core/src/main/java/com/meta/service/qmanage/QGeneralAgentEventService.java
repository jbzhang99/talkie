package com.meta.service.qmanage;

import com.meta.commonUtil.CommonUtils;
import com.meta.commonUtil.EnglishCommonUtils;
import com.meta.dao.qmanage.QGeneralAgentEventRepository;
import com.meta.model.qmanage.QGeneralAgentEvent;
import com.meta.model.user.User;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class QGeneralAgentEventService  extends BaseServiceImpl<QGeneralAgentEvent, Long> implements BaseService<QGeneralAgentEvent, Long> {

    @Autowired
    public void setBaseDao(QGeneralAgentEventRepository qGeneralAgentEventRepository) {
        super.setBaseDao(qGeneralAgentEventRepository);
    }

    @Autowired
    private  QGeneralAgentEventRepository qGeneralAgentEventRepository;


    /**
     *  分配记录
     * @param sourceUser
     * @param status
     * @param qbNum
     * @param targeUser
     * @return
     */
    public String getRemark(User sourceUser, String status, String qbNum, User targeUser){

        //代理转出：xx币，企业账号：xxx
        return CommonUtils.findMerchantLevel(sourceUser.getMerchantLevel())+"账号:"+sourceUser.getAccount()+status+":"+ qbNum +"币,"+CommonUtils.findMerchantLevel(targeUser.getMerchantLevel())+"账号:"+targeUser.getAccount() ;
    }


    public String getRemarkEnglish(User sourceUser, String status, String qbNum, User targeUser){

        //代理转出：xx币，企业账号：xxx
        return EnglishCommonUtils.findMerchantLevel(sourceUser.getMerchantLevel())+"  account:  "+sourceUser.getAccount()+"  "+status+"  :  "+ qbNum +"   money, "+EnglishCommonUtils.findMerchantLevel(targeUser.getMerchantLevel())+"  account:  "+targeUser.getAccount() ;
    }

}
