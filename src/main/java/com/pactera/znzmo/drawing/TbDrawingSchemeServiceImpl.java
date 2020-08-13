package com.pactera.znzmo.drawing;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.ApproveStatusEnum;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.examineverify.TbExamineVerify;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.drawing.DrawingAddParam;
import com.pactera.znzmo.vo.drawing.DrawingUpdateParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;

/**
 * <p>
 * 图纸方案表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbDrawingSchemeServiceImpl extends ServiceImpl<TbDrawingSchemeMapper, TbDrawingScheme> implements TbDrawingSchemeService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Override
	public IPage<TbDrawingScheme> selectDrawingPages(Page<TbDrawingScheme> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbDrawingScheme> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbDrawingScheme.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbDrawingScheme.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			queryWrapper.eq(TbDrawingScheme.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			queryWrapper.eq(TbDrawingScheme.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
			queryWrapper.eq(TbDrawingScheme.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbDrawingScheme.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addDrawing(DrawingAddParam drawingAddParam) {
		TbDrawingScheme tbDrawing = new TbDrawingScheme();
		tbDrawing.setMainGraph(drawingAddParam.getMainGraph());
		tbDrawing.setCode(NumGenerationUtil.getrandom());
		tbDrawing.setPrimaryClassId(drawingAddParam.getPrimaryClassId());
		tbDrawing.setPrimaryClassName(drawingAddParam.getPrimaryClassName());
		tbDrawing.setSecondaryClassId(drawingAddParam.getSecondaryClassId());
		tbDrawing.setSecondaryClassName(drawingAddParam.getSecondaryClassName());
		tbDrawing.setStyleId(drawingAddParam.getStyleId());
		tbDrawing.setStyleName(drawingAddParam.getStyleName());
		tbDrawing.setTitle(drawingAddParam.getTitle());
		tbDrawing.setTag(drawingAddParam.getTag());
		tbDrawing.setType(drawingAddParam.getType());
		tbDrawing.setPrice(drawingAddParam.getPrice());
		tbDrawing.setVersion(drawingAddParam.getVersion());
		tbDrawing.setDesignTime(drawingAddParam.getDesignTime());
		tbDrawing.setLocation(drawingAddParam.getLocation());
		tbDrawing.setSynopsis(drawingAddParam.getSynopsis());
		tbDrawing.setText(drawingAddParam.getText());
		tbDrawing.setRemarks(drawingAddParam.getRemarks());
		tbDrawing.setIsValid(IsValidEnum.YES.getKey());
		tbDrawing.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tbDrawing.setCreateId(user.getUserId());
//		tbDrawing.setCreateName(user.getUserName());
		tbDrawing.setCreateTime(LocalDateTime.now());
//		tbDrawing.setUpdateId(user.getUserId());
//		tbDrawing.setUpdateName(user.getUserName());
		tbDrawing.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbDrawing);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : drawingAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDrawing.getId());
            tbAttachment.setAttachmentName(keyAndUrl.getFileName());
            tbAttachment.setAttachmentPath(keyAndUrl.getFile());
            tbAttachment.setPhysicalPath(keyAndUrl.getFile());
            tbAttachment.setAliasName(keyAndUrl.getRealName());
            tbAttachment.setReType(IsValidEnum.YES.getValue());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//            tbAttachment.setCreateId(user.getUserId());
//            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
        }
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(1L);
        tbExamineVerify.setUserName("admin");
        tbExamineVerify.setReId(tbDrawing.getId());
        tbExamineVerify.setReType(ReTypeEnum.MODEL.getKey());
        tbExamineVerify.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
        tbExamineVerify.setIsValid(IsValidEnum.YES.getKey());
//		tbExamineVerify.setCreateId(user.getUserId());
//		tbExamineVerify.setCreateName(user.getUserName());
        tbExamineVerify.setCreateTime(LocalDateTime.now());
//		tbExamineVerify.setUpdateId(user.getUserId());
//		tbExamineVerify.setUpdateName(user.getUserName());
        tbExamineVerify.setUpdateTime(LocalDateTime.now());
        tbExamineVerifyMapper.insert(tbExamineVerify);
	}

	@Override
	public void updteDrawing(DrawingUpdateParam drawingUpdateParam) {
		TbDrawingScheme tbDrawing = baseMapper.selectById(drawingUpdateParam.getDrawingId());
		tbDrawing.setMainGraph(drawingUpdateParam.getMainGraph());
		tbDrawing.setPrimaryClassId(drawingUpdateParam.getPrimaryClassId());
		tbDrawing.setPrimaryClassName(drawingUpdateParam.getPrimaryClassName());
		tbDrawing.setSecondaryClassId(drawingUpdateParam.getSecondaryClassId());
		tbDrawing.setSecondaryClassName(drawingUpdateParam.getSecondaryClassName());
		tbDrawing.setStyleId(drawingUpdateParam.getStyleId());
		tbDrawing.setStyleName(drawingUpdateParam.getStyleName());
		tbDrawing.setTitle(drawingUpdateParam.getTitle());
		tbDrawing.setTag(drawingUpdateParam.getTag());
		tbDrawing.setType(drawingUpdateParam.getType());
		tbDrawing.setPrice(drawingUpdateParam.getPrice());
		tbDrawing.setVersion(drawingUpdateParam.getVersion());
		tbDrawing.setDesignTime(drawingUpdateParam.getDesignTime());
		tbDrawing.setLocation(drawingUpdateParam.getLocation());
		tbDrawing.setSynopsis(drawingUpdateParam.getSynopsis());
		tbDrawing.setText(drawingUpdateParam.getText());
		tbDrawing.setRemarks(drawingUpdateParam.getRemarks());
		tbDrawing.setStatus(StatusEnum.START_USE.getKey());
//		tbDrawing.setUpdateId(user.getUserId());
//		tbDrawing.setUpdateName(user.getUserName());
		tbDrawing.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbDrawing);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, drawingUpdateParam.getDrawingId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : drawingUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDrawing.getId());
            tbAttachment.setAttachmentName(keyAndUrl.getFileName());
            tbAttachment.setAttachmentPath(keyAndUrl.getFile());
            tbAttachment.setPhysicalPath(keyAndUrl.getFile());
            tbAttachment.setAliasName(keyAndUrl.getRealName());
            tbAttachment.setReType(IsValidEnum.YES.getValue());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//            tbAttachment.setCreateId(user.getUserId());
//            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
        }
	}

}
