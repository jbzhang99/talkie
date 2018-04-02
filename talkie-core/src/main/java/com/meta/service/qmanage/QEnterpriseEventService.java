package com.meta.service.qmanage;

import com.meta.dao.qmanage.QEnterpriseEventRepository;
import com.meta.model.qmanage.QEnterpriseEvent;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import com.meta.remark.remarkDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by lhq on 2017/11/14.
 */
@Service
@Transactional
public class QEnterpriseEventService extends BaseServiceImpl<QEnterpriseEvent, Long> implements BaseService<QEnterpriseEvent, Long> {
    @Autowired
    public void setBaseDao(QEnterpriseEventRepository qEnterpriseEventRepository) {
        super.setBaseDao(qEnterpriseEventRepository);
    }

    @Autowired
    private  QEnterpriseEventRepository qEnterpriseEventRepository;


    /**
     * 划分QB操作备注
     * @return
     */
    public  String getRemark_PLACE(String operatingNamre,String qbNum,String userNmae){
        return operatingNamre+ remarkDict.PLACE + remarkDict.GIVE + ":"+  qbNum + remarkDict.MONEY+ ","+remarkDict.USER +"账号："+ userNmae;
    }

    /**
     * 划分QB操作备注
     * @return
     */
    public  String getRemark_RECOVER(String operatingNamre,String qbNum,String userNmae){
        //3.代理转出：xx币，企业账号：xxx
        return   operatingNamre+ remarkDict.RECOVER + ":"+   qbNum + remarkDict.MONEY+ ","+remarkDict.USER +"账号："+userNmae;
    }

}
