package com.gen.service.module.generate.controller;

import com.gen.service.common.controller.BaseController;
import com.gen.service.common.exception.ApiRest;
import com.gen.service.model.vo.BaseStringVO;
import com.gen.service.module.db.dto.DbStructureDTO;
import com.gen.service.module.db.dto.GenerateDTO;
import com.gen.service.module.db.service.DBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api(tags={"Basic Dict"})
@RestController
@RequestMapping("/api/generate")
public class GenerateApiController extends BaseController {

    /**
     * DB Connection Service
     */
    @Resource
    DBService dbService;

    /**
     * DB HTML
     */
    @ApiOperation(value = "DB HTML")
    @RequestMapping(value = "/structure", method = { RequestMethod.POST})
    public ApiRest<BaseStringVO> getDbHTML(@RequestBody DbStructureDTO dto) {
        return dbService.dbDocument(dto);
    }

    /**
     * Code Source
     */
    @ApiOperation(value = "Code Source")
    @RequestMapping(value = "/source", method = { RequestMethod.POST})
    public ApiRest<BaseStringVO> getCodeSource(@RequestBody GenerateDTO dto) {
        return dbService.codeSource(dto);
    }
}
