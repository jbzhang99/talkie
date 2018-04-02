package com.meta.service.FileData;

import com.meta.dao.FileData.AudioRepository;

import com.meta.model.FileData.Audio;
import com.meta.query.BaseService;
import com.meta.query.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *  ybh
 *   17/12/11
 *   录音业务类
 */
@Service("AudioService")
@Transactional
public class AudioService extends BaseServiceImpl<Audio,Long> implements BaseService<Audio,Long> {

    @Autowired
    public void setBaseDao(AudioRepository AudioRepository) {
        super.setBaseDao(AudioRepository);
    }

    //日志
    protected static Logger logger = LoggerFactory.getLogger(AudioService.class);

    @Autowired
    private AudioRepository audioRepository;

    /**
     * 删除掉指定的日期前面的数据
     */
    public void deleteByCreateDateLessThan(String flagTime){

        audioRepository.deleteByCreateDateLessThan(flagTime);
        logger.info("删除指定时间前的数据:"+flagTime);
    }



}
