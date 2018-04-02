package com.meta.dao.map;

import com.meta.model.map.BaseStation;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

/**
 * create by lhq
 * create date on  18-1-30上午11:41
 *
 * @version 1.0
 **/
public interface BaseStationRepository extends BaseRepository<BaseStation, Long> {

    BaseStation   findByCellId(@Param("cellId") String cellid);
}
