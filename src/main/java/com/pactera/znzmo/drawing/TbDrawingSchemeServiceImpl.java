package com.pactera.znzmo.drawing;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.ApproveStatusEnum;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.examineverify.TbExamineVerify;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
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
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbDrawingScheme> selectDrawingPages(Page<TbDrawingScheme> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbDrawingScheme> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbDrawingScheme.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbDrawingScheme.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId())) {
			queryWrapper.eq(TbDrawingScheme.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId())) {
			queryWrapper.eq(TbDrawingScheme.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId())) {
			queryWrapper.eq(TbDrawingScheme.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbDrawingScheme.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addDrawing(DrawingAddParam drawingAddParam) {
		TbDrawingScheme tbDrawing = new TbDrawingScheme();
		tbDrawing.setCode(NumGenerationUtil.getrandom());
		tbDrawing.setPrimaryClassId(Long.valueOf(drawingAddParam.getPrimaryClassId()));
		tbDrawing.setPrimaryClassName(drawingAddParam.getPrimaryClassName());
		tbDrawing.setSecondaryClassId(Long.valueOf(drawingAddParam.getSecondaryClassId()));
		tbDrawing.setSecondaryClassName(drawingAddParam.getSecondaryClassName());
		tbDrawing.setStyleId(Long.valueOf(drawingAddParam.getStyleId()));
		tbDrawing.setStyleName(drawingAddParam.getStyleName());
		tbDrawing.setTitle(drawingAddParam.getTitle());
		tbDrawing.setTag(drawingAddParam.getTag());
		tbDrawing.setType(drawingAddParam.getType());
		tbDrawing.setPrice(drawingAddParam.getPrice());
		tbDrawing.setVersion(drawingAddParam.getVersion());
		tbDrawing.setDesignTime(DateUtils.parseDate(drawingAddParam.getDesignTime() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbDrawing.setLocation(drawingAddParam.getLocation());
		tbDrawing.setSynopsis(drawingAddParam.getSynopsis());
		tbDrawing.setText(drawingAddParam.getText());
		tbDrawing.setRemarks(drawingAddParam.getRemarks());
		tbDrawing.setIsValid(IsValidEnum.YES.getKey());
		tbDrawing.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbDrawing.setCreateId(user.getId());
		tbDrawing.setCreateName(user.getName());
		tbDrawing.setCreateTime(LocalDateTime.now());
		tbDrawing.setUpdateId(user.getId());
		tbDrawing.setUpdateName(user.getName());
		tbDrawing.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbDrawing);
		
		//新增上传文件
		for (UploadInfo uploadInfo : drawingAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDrawing.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(uploadInfo.getType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getType() == 1) {
            	tbDrawing.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbDrawing);
            }
		}
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(user.getId());
        tbExamineVerify.setUserName(user.getName());
        tbExamineVerify.setReId(tbDrawing.getId());
        tbExamineVerify.setReType(ReTypeEnum.DRAWING.getKey());
        tbExamineVerify.setStatus(ApproveStatusEnum.WAIT.getKey());
        tbExamineVerify.setIsValid(IsValidEnum.YES.getKey());
		tbExamineVerify.setCreateId(user.getId());
		tbExamineVerify.setCreateName(user.getName());
        tbExamineVerify.setCreateTime(LocalDateTime.now());
		tbExamineVerify.setUpdateId(user.getId());
		tbExamineVerify.setUpdateName(user.getName());
        tbExamineVerify.setUpdateTime(LocalDateTime.now());
        tbExamineVerifyMapper.insert(tbExamineVerify);
	}

	@Override
	public void updteDrawing(DrawingUpdateParam drawingUpdateParam) {
		TbDrawingScheme tbDrawing = baseMapper.selectById(drawingUpdateParam.getDrawingId());
		tbDrawing.setPrimaryClassId(Long.valueOf(drawingUpdateParam.getPrimaryClassId()));
		tbDrawing.setPrimaryClassName(drawingUpdateParam.getPrimaryClassName());
		tbDrawing.setSecondaryClassId(Long.valueOf(drawingUpdateParam.getSecondaryClassId()));
		tbDrawing.setSecondaryClassName(drawingUpdateParam.getSecondaryClassName());
		tbDrawing.setStyleId(Long.valueOf(drawingUpdateParam.getStyleId()));
		tbDrawing.setStyleName(drawingUpdateParam.getStyleName());
		tbDrawing.setTitle(drawingUpdateParam.getTitle());
		tbDrawing.setTag(drawingUpdateParam.getTag());
		tbDrawing.setType(drawingUpdateParam.getType());
		tbDrawing.setPrice(drawingUpdateParam.getPrice());
		tbDrawing.setVersion(drawingUpdateParam.getVersion());
		tbDrawing.setDesignTime(DateUtils.parseDate(drawingUpdateParam.getDesignTime() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbDrawing.setLocation(drawingUpdateParam.getLocation());
		tbDrawing.setSynopsis(drawingUpdateParam.getSynopsis());
		tbDrawing.setText(drawingUpdateParam.getText());
		tbDrawing.setRemarks(drawingUpdateParam.getRemarks());
		tbDrawing.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbDrawing.setUpdateId(user.getId());
		tbDrawing.setUpdateName(user.getName());
		tbDrawing.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbDrawing);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, drawingUpdateParam.getDrawingId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
		for (UploadInfo uploadInfo : drawingUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDrawing.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(uploadInfo.getType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getType() == 1) {
            	tbDrawing.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbDrawing);
            }
		}
	}

}
