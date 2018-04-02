package com.meta.dao.datatotal;

import com.meta.model.datatotal.QGeneralagentDataTotal;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

/**
 * create by lhq
 * create date on  18-2-27上午11:44
 *
 * @version 1.0
 **/
public interface QGeneralAgentDataTotalRepository  extends BaseRepository<QGeneralagentDataTotal, Long>  {

    QGeneralagentDataTotal findByYear(@Param("year") Integer year);

}
