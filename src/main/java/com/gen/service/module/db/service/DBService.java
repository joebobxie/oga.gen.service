package com.gen.service.module.db.service;

import com.gen.service.common.exception.ApiRest;
import com.gen.service.model.vo.BaseStringVO;
import com.gen.service.module.db.dto.DbConfigDTO;
import com.gen.service.module.db.dto.DbStructureDTO;
import com.gen.service.module.db.dto.GenerateDTO;
import com.gen.service.module.db.vo.DbStructureVO;
import com.gen.service.module.db.vo.DbVO;

import java.util.List;

/**
 * DB Connection
 */
public interface DBService {

    /**
     * Database List
     */
    List<DbVO> dbList(DbConfigDTO dto);

    /**
     * Database Structure
     */
    DbStructureVO dbStructure(DbStructureDTO dto);

    /**
     * Database HTML
     */
    ApiRest<BaseStringVO> dbDocument(DbStructureDTO dto);

    /**
     * Code Source
     */
    ApiRest<BaseStringVO> codeSource(GenerateDTO dto);
}
