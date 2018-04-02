package com.meta.dao.FileData;

import com.meta.model.FileData.Audio;
import com.meta.query.BaseRepository;

/**
 * ybh
 */
public interface AudioRepository extends BaseRepository<Audio,Long> {


    /**
     * 删除掉指定的日期前面的数据
     */
    public void deleteByCreateDateLessThan(String flagTime);

}
