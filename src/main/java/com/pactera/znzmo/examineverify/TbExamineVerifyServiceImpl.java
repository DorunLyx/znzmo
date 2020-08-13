package com.pactera.znzmo.examineverify;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.banner.TbBanner;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.model.Tb3dModel;
import com.pactera.znzmo.model.dao.Tb3dModelMapper;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.vo.ExamineListVO;
import com.pactera.znzmo.vo.ExamineQueryParam;
import com.pactera.znzmo.vo.ExamineStatusParam;

/**
 * <p>
 * 审批表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbExamineVerifyServiceImpl extends ServiceImpl<TbExamineVerifyMapper, TbExamineVerify> implements TbExamineVerifyService {

	@Autowired
	private Tb3dModelMapper tb3dModelMapper;
	
	@Autowired
	private TbSuModelMapper tbSuModelMapper;
	
	@Autowired
	private TbDrawingSchemeMapper tbDrawingSchemeMapper;
	
	@Autowired
	private TbHdMappingMapper tbHdMappingMapper;
	
	@Autowired
	private TbDatabaseMapper tbDatabaseMapper;
	
	@Override
	public IPage<ExamineListVO> selectExaminePages(Page<TbExamineVerify> page, ExamineQueryParam examineQueryParam) {
		QueryWrapper<TbExamineVerify> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbExamineVerify.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(examineQueryParam.getType().toString())) {
			queryWrapper.eq(TbExamineVerify.RE_TYPE, examineQueryParam.getType());
		}
		if(StringUtil.isNotEmpty(examineQueryParam.getStatus().toString())) {
			queryWrapper.eq(TbBanner.STATUS, examineQueryParam.getStatus());
		}
		queryWrapper.orderByDesc(TbBanner.UPDATE_TIME);
		IPage<TbExamineVerify> selectPage = baseMapper.selectPage(page,queryWrapper);
		return this.selectReInfo(selectPage, examineQueryParam);
	}

	/**
	 * @Title: selectReInfo 
	 * @Description: 获取审核业务数据
	 * @param iPage
	 * @return IPage<ExamineListVO>
	 * @author liyongxu
	 * @date 2020年8月10日 上午11:02:08 
	*/
	private IPage<ExamineListVO> selectReInfo(IPage<TbExamineVerify> iPage ,ExamineQueryParam examineQueryParam) {
		List<ExamineListVO> examineList = new ArrayList<ExamineListVO>();
		IPage<ExamineListVO> exmaineListPage =  new Page<ExamineListVO>(examineQueryParam.getPageNo(), examineQueryParam.getPageSize());
		for (TbExamineVerify tbExamineVerify : iPage.getRecords()) {
			ExamineListVO examineListVO = new ExamineListVO();
			examineListVO.setExamineId(tbExamineVerify.getId());
			examineListVO.setStatus(tbExamineVerify.getStatus());
			examineListVO = this.selectReInfoByType(examineListVO, tbExamineVerify.getReId(), tbExamineVerify.getReType());
			examineList.add(examineListVO);
		}
		exmaineListPage.setRecords(examineList);
		exmaineListPage.setCurrent(iPage.getCurrent());
		exmaineListPage.setPages(iPage.getPages());
		exmaineListPage.setSize(iPage.getSize());
		exmaineListPage.setTotal(iPage.getTotal());		
		return exmaineListPage;
	}

	/**
	 * @Title: selectReInfoByType 
	 * @Description: 根据业务id跟类型查询业务信息
	 * @author liyongxu
	 * @param examineListVO 
	 * @param reType 
	 * @param reId 
	 * @date 2020年8月10日 上午11:09:29 
	*/
	private ExamineListVO selectReInfoByType(ExamineListVO examineListVO, Long reId, Integer reType) {
		if(reType == 0) {
			Tb3dModel tb3dModel = tb3dModelMapper.selectById(reId);
			examineListVO.setMainGraph(tb3dModel.getMainGraph());
			examineListVO.setCode(tb3dModel.getCode());
			examineListVO.setPrimaryClassName(tb3dModel.getPrimaryClassName());
			examineListVO.setClassName(tb3dModel.getSecondaryClassName());
			examineListVO.setStyleName(tb3dModel.getStyleName());
			examineListVO.setTitle(tb3dModel.getTitle());
			examineListVO.setType(tb3dModel.getType());
			examineListVO.setPrice(tb3dModel.getPrice());
			examineListVO.setStatus(tb3dModel.getStatus());
			examineListVO.setUploadUser(tb3dModel.getCreateName());
			examineListVO.setUploadTime(tb3dModel.getCreateTime());
		}else if (reType == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(reId);
			examineListVO.setMainGraph(tbSuModel.getMainGraph());
			examineListVO.setCode(tbSuModel.getCode());
			examineListVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
			examineListVO.setClassName(tbSuModel.getSecondaryClassName());
			examineListVO.setStyleName(tbSuModel.getStyleName());
			examineListVO.setTitle(tbSuModel.getTitle());
			examineListVO.setType(tbSuModel.getType());
			examineListVO.setPrice(tbSuModel.getPrice());
			examineListVO.setStatus(tbSuModel.getStatus());
			examineListVO.setUploadUser(tbSuModel.getCreateName());
			examineListVO.setUploadTime(tbSuModel.getCreateTime());
		}else if (reType == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(reId);
			examineListVO.setMainGraph(tbDrawing.getMainGraph());
			examineListVO.setCode(tbDrawing.getCode());
			examineListVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
			examineListVO.setClassName(tbDrawing.getSecondaryClassName());
			examineListVO.setStyleName(tbDrawing.getStyleName());
			examineListVO.setTitle(tbDrawing.getTitle());
			examineListVO.setType(tbDrawing.getType());
			examineListVO.setPrice(tbDrawing.getPrice());
			examineListVO.setStatus(tbDrawing.getStatus());
			examineListVO.setUploadUser(tbDrawing.getCreateName());
			examineListVO.setUploadTime(tbDrawing.getCreateTime());
		}else if (reType == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(reId);
			examineListVO.setMainGraph(tbHdMapping.getMainGraph());
			examineListVO.setCode(tbHdMapping.getCode());
			examineListVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
			examineListVO.setClassName(tbHdMapping.getSecondaryClassName());
			examineListVO.setStyleName(tbHdMapping.getStyleName());
			examineListVO.setTitle(tbHdMapping.getTitle());
			examineListVO.setType(tbHdMapping.getType());
			examineListVO.setPrice(tbHdMapping.getPrice());
			examineListVO.setStatus(tbHdMapping.getStatus());
			examineListVO.setUploadUser(tbHdMapping.getCreateName());
			examineListVO.setUploadTime(tbHdMapping.getCreateTime());
		}else if (reType == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(reId);
			examineListVO.setMainGraph(tbDatabase.getMainGraph());
			examineListVO.setCode(tbDatabase.getCode());
			examineListVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
			examineListVO.setClassName(tbDatabase.getSecondaryClassName());
			examineListVO.setStyleName(tbDatabase.getStyleName());
			examineListVO.setTitle(tbDatabase.getTitle());
			examineListVO.setType(tbDatabase.getType());
			examineListVO.setPrice(tbDatabase.getPrice());
			examineListVO.setStatus(tbDatabase.getStatus());
			examineListVO.setUploadUser(tbDatabase.getCreateName());
			examineListVO.setUploadTime(tbDatabase.getCreateTime());
		}
		return examineListVO;
	}

	@Override
	public void updateExamineStatus(ExamineStatusParam examineStatusParam) {
		TbExamineVerify tbExamineVerify = baseMapper.selectById(examineStatusParam.getExamineId());
		tbExamineVerify.setStatus(StatusEnum.START_USE.getKey());
		if(tbExamineVerify.getReType() == 0) {
			Tb3dModel tb3dModel = tb3dModelMapper.selectById(tbExamineVerify.getReId());
			tb3dModel.setStatus(StatusEnum.START_USE.getKey());
			tb3dModelMapper.updateById(tb3dModel);
		}else if (tbExamineVerify.getReType() == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(tbExamineVerify.getReId());
			tbSuModel.setStatus(StatusEnum.START_USE.getKey());
			tbSuModelMapper.updateById(tbSuModel);
		}else if (tbExamineVerify.getReType() == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(tbExamineVerify.getReId());
			tbDrawing.setStatus(StatusEnum.START_USE.getKey());
			tbDrawingSchemeMapper.updateById(tbDrawing);
		}else if (tbExamineVerify.getReType() == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(tbExamineVerify.getReId());
			tbHdMapping.setStatus(StatusEnum.START_USE.getKey());
			tbHdMappingMapper.updateById(tbHdMapping);
		}else if (tbExamineVerify.getReType() == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(tbExamineVerify.getReId());
			tbDatabase.setStatus(StatusEnum.START_USE.getKey());
			tbDatabaseMapper.updateById(tbDatabase);
		}
		baseMapper.updateById(tbExamineVerify);
	}
	
}
