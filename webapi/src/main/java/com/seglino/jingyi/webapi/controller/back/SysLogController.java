package com.seglino.jingyi.webapi.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.log.dto.SysLogDetailDto;
import com.seglino.jingyi.common.log.dto.SysLogEntryData;
import com.seglino.jingyi.common.log.dto.SysLogListDto;
import com.seglino.jingyi.common.log.service.SysLogService;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.webapi.vo.back.log.SysLogDetailVo;
import com.seglino.jingyi.webapi.vo.back.log.SysLogListVo;

@RestController
@RequestMapping("back/syslog")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;

	@GetMapping("list")
	public ApiPageResult list(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			Page<SysLogListDto> page = sysLogService.pageByIndex(params);
			aResult.setTotal(page.getTotal());
			aResult.setData(AutoMapper.mapperList(page.getResult(), SysLogListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("detail")
	public ApiResult detail(String id) {
		ApiResult aResult = new ApiResult();
		try {
			SysLogDetailDto dto = sysLogService.detail(id);
			aResult.setData(AutoMapper.mapper(dto, SysLogDetailVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("module/list")
	public ApiResult moduleList() {
		ApiResult aResult = new ApiResult();
		try {
			aResult.setData(sysLogService.moduleList());
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("type/list")
	public ApiResult typeList() {
		ApiResult aResult = new ApiResult();
		try {
			aResult.setData(SysLogEntryData.getTypeList());
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
