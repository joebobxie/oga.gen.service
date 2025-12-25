package com.gen.service.module.db.controller;

import com.alibaba.fastjson.JSON;
import com.gen.service.common.controller.BaseController;
import com.gen.service.common.exception.ApiRest;
import com.gen.service.module.db.dto.DbConfigDTO;
import com.gen.service.module.db.dto.DbStructureDTO;
import com.gen.service.module.db.service.DBService;
import com.gen.service.module.db.vo.DbStructureVO;
import com.gen.service.module.db.vo.DbVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DB Connection
 */
@Api(tags={"DB Connection"})
@RestController
@RequestMapping("/api/db")
public class DbApiController extends BaseController {

    /**
     * DB Connection Service
     */
    @Resource
    DBService dbService;

    /**
     * Get DB List
     */
    @ApiOperation(value = "DB List")
    @RequestMapping(value = "/list", method = { RequestMethod.POST})
    public ApiRest<List<DbVO>> getDbList(@RequestBody DbConfigDTO dto) {
        return super.success(dbService.dbList(dto));
    }

    /**
     * DB Structure
     */
    @ApiOperation(value = "DB Structure")
    @RequestMapping(value = "/structure", method = { RequestMethod.POST})
    public ApiRest<DbStructureVO> getDbStructure(@RequestBody DbStructureDTO dto) {
        return super.success(dbService.dbStructure(dto));
    }

}
