package com.pactera.znzmo.examineverify;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.model.TbThreedModel;
import com.pactera.znzmo.model.dao.TbThreedModelMapper;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.examine.ExamineListVO;
import com.pactera.znzmo.vo.examine.ExamineQueryParam;
import com.pactera.znzmo.vo.examine.ExamineStatusParam;

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
	private TbThreedModelMapper tbThreedModelMapper;
	
	@Autowired
	private TbSuModelMapper tbSuModelMapper;
	
	@Autowired
	private TbDrawingSchemeMapper tbDrawingSchemeMapper;
	
	@Autowired
	private TbHdMappingMapper tbHdMappingMapper;
	
	@Autowired
	private TbDatabaseMapper tbDatabaseMapper;
	
	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Override
	public IPage<ExamineListVO> selectExaminePages(Page<TbExamineVerify> page, ExamineQueryParam examineQueryParam) {
		QueryWrapper<TbExamineVerify> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbExamineVerify.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(examineQueryParam.getUserId())) {
			queryWrapper.eq(TbExamineVerify.CREATE_ID, examineQueryParam.getUserId());
		}
		if(StringUtils.isNotEmpty(examineQueryParam.getType())) {
			queryWrapper.eq(TbExamineVerify.RE_TYPE, examineQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(examineQueryParam.getStatus().toString())) {
			queryWrapper.eq(TbExamineVerify.STATUS, examineQueryParam.getStatus());
		}
		if(StringUtils.isNotEmpty(examineQueryParam.getStartTime())) {
			queryWrapper.ge(TbExamineVerify.UPDATE_TIME, examineQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(examineQueryParam.getEndTime())) {
			queryWrapper.le(TbExamineVerify.UPDATE_TIME, examineQueryParam.getEndTime() + " 23:59:59");
		}
		queryWrapper.orderByDesc(TbExamineVerify.UPDATE_TIME);
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
			examineListVO.setExamineId(tbExamineVerify.getId().toString());
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
		if(reType == ReTypeEnum.MODEL.getKey()) {
			TbThreedModel tbThreedModel = tbThreedModelMapper.selectById(reId);
	        TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbThreedModel.getMainGraph());
	        if(tbAttachment != null) {
	        	examineListVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			examineListVO.setCode(tbThreedModel.getCode());
			examineListVO.setPrimaryClassName(tbThreedModel.getPrimaryClassName());
			examineListVO.setClassName(tbThreedModel.getSecondaryClassName());
			examineListVO.setStyleName(tbThreedModel.getStyleName());
			examineListVO.setTitle(tbThreedModel.getTitle());
			examineListVO.setType(tbThreedModel.getType());
			examineListVO.setPrice(tbThreedModel.getPrice());
			examineListVO.setStatus(tbThreedModel.getStatus());
			examineListVO.setUploadUser(tbThreedModel.getCreateName());
			examineListVO.setUploadTime(DateUtils.localDateTimeToString(tbThreedModel.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.SUMODEL.getKey()) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbSuModel.getMainGraph());
	        if(tbAttachment != null) {
	        	examineListVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			examineListVO.setCode(tbSuModel.getCode());
			examineListVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
			examineListVO.setClassName(tbSuModel.getSecondaryClassName());
			examineListVO.setStyleName(tbSuModel.getStyleName());
			examineListVO.setTitle(tbSuModel.getTitle());
			examineListVO.setType(tbSuModel.getType());
			examineListVO.setPrice(tbSuModel.getPrice());
			examineListVO.setStatus(tbSuModel.getStatus());
			examineListVO.setUploadUser(tbSuModel.getCreateName());
			examineListVO.setUploadTime(DateUtils.localDateTimeToString(tbSuModel.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.DRAWING.getKey()) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbDrawing.getMainGraph());
	        if(tbAttachment != null) {
	        	examineListVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			examineListVO.setCode(tbDrawing.getCode());
			examineListVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
			examineListVO.setClassName(tbDrawing.getSecondaryClassName());
			examineListVO.setStyleName(tbDrawing.getStyleName());
			examineListVO.setTitle(tbDrawing.getTitle());
			examineListVO.setType(tbDrawing.getType());
			examineListVO.setPrice(tbDrawing.getPrice());
			examineListVO.setStatus(tbDrawing.getStatus());
			examineListVO.setUploadUser(tbDrawing.getCreateName());
			examineListVO.setUploadTime(DateUtils.localDateTimeToString(tbDrawing.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.HD.getKey()) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbHdMapping.getMainGraph());
	        if(tbAttachment != null) {
	        	examineListVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			examineListVO.setCode(tbHdMapping.getCode());
			examineListVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
			examineListVO.setClassName(tbHdMapping.getSecondaryClassName());
			examineListVO.setStyleName(tbHdMapping.getStyleName());
			examineListVO.setTitle(tbHdMapping.getTitle());
			examineListVO.setType(tbHdMapping.getType());
			examineListVO.setPrice(tbHdMapping.getPrice());
			examineListVO.setStatus(tbHdMapping.getStatus());
			examineListVO.setUploadUser(tbHdMapping.getCreateName());
			examineListVO.setUploadTime(DateUtils.localDateTimeToString(tbHdMapping.getCreateTime(), DateUtils.DATE_FORMAT));
		}else if (reType == ReTypeEnum.DATABASE.getKey()) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(reId);
			TbAttachment tbAttachment = tbAttachmentMapper.selectById(tbDatabase.getMainGraph());
	        if(tbAttachment != null) {
	        	examineListVO.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			examineListVO.setCode(tbDatabase.getCode());
			examineListVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
			examineListVO.setClassName(tbDatabase.getSecondaryClassName());
			examineListVO.setStyleName(tbDatabase.getStyleName());
			examineListVO.setTitle(tbDatabase.getTitle());
			examineListVO.setType(tbDatabase.getType());
			examineListVO.setPrice(tbDatabase.getPrice());
			examineListVO.setStatus(tbDatabase.getStatus());
			examineListVO.setUploadUser(tbDatabase.getCreateName());
			examineListVO.setUploadTime(DateUtils.localDateTimeToString(tbDatabase.getCreateTime(), DateUtils.DATE_FORMAT));
		}
		return examineListVO;
	}

	@Override
	public void updateExamineStatus(ExamineStatusParam examineStatusParam) {
		TbExamineVerify tbExamineVerify = baseMapper.selectById(examineStatusParam.getExamineId());
		if(tbExamineVerify != null) {
			tbExamineVerify.setStatus(StatusEnum.START_USE.getKey());
			if(tbExamineVerify.getReType().equals(ReTypeEnum.MODEL.getKey())) {
				TbThreedModel tbThreedModel = tbThreedModelMapper.selectById(tbExamineVerify.getReId());
				tbThreedModel.setStatus(StatusEnum.START_USE.getKey());
				tbThreedModelMapper.updateById(tbThreedModel);
			}else if (tbExamineVerify.getReType().equals(ReTypeEnum.SUMODEL.getKey())) {
				TbSuModel tbSuModel = tbSuModelMapper.selectById(tbExamineVerify.getReId());
				tbSuModel.setStatus(StatusEnum.START_USE.getKey());
				tbSuModelMapper.updateById(tbSuModel);
			}else if (tbExamineVerify.getReType().equals(ReTypeEnum.DRAWING.getKey())) {
				TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(tbExamineVerify.getReId());
				tbDrawing.setStatus(StatusEnum.START_USE.getKey());
				tbDrawingSchemeMapper.updateById(tbDrawing);
			}else if (tbExamineVerify.getReType().equals(ReTypeEnum.HD.getKey())) {
				TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(tbExamineVerify.getReId());
				tbHdMapping.setStatus(StatusEnum.START_USE.getKey());
				tbHdMappingMapper.updateById(tbHdMapping);
			}else if (tbExamineVerify.getReType().equals(ReTypeEnum.DATABASE.getKey())) {
				TbDatabase tbDatabase = tbDatabaseMapper.selectById(tbExamineVerify.getReId());
				tbDatabase.setStatus(StatusEnum.START_USE.getKey());
				tbDatabaseMapper.updateById(tbDatabase);
			}
			baseMapper.updateById(tbExamineVerify);
		}
	}
	
}
