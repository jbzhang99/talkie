package com.meta.dao.map;

import com.meta.model.map.MetaBaseStation;
import com.meta.query.BaseRepository;
import org.springframework.data.repository.query.Param;

/**
 * create by lhq
 * create date on  18-1-30上午11:41
 *
 * @version 1.0
 **/
public interface MetaStationRepository extends BaseRepository<MetaBaseStation, Long> {

    MetaBaseStation findByCellIdAndTerminal_Id(@Param("cellId") String cellId,@Param("terminalId")Long terminalId);
}
