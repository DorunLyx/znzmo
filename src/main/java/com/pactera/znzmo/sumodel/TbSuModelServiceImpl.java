package com.pactera.znzmo.sumodel;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.enums.ApproveStatusEnum;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.examineverify.TbExamineVerify;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.vo.su.SuModelAddParam;
import com.pactera.znzmo.vo.su.SuModelUpdateParam;

/**
 * <p>
 * su模型表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbSuModelServiceImpl extends ServiceImpl<TbSuModelMapper, TbSuModel> implements TbSuModelService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbSuModel> selectSuModelPages(Page<TbSuModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbSuModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId())) {
			queryWrapper.eq(TbSuModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId())) {
			queryWrapper.eq(TbSuModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId())) {
			queryWrapper.eq(TbSuModel.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbSuModel.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addSuModel(SuModelAddParam suModelAddParam) {
		TbSuModel tbSuModel = new TbSuModel();
		tbSuModel.setCode(NumGenerationUtil.getrandom());
		tbSuModel.setPrimaryClassId(Long.valueOf(suModelAddParam.getPrimaryClassId()));
		tbSuModel.setPrimaryClassName(suModelAddParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(Long.valueOf(suModelAddParam.getSecondaryClassId()));
		tbSuModel.setSecondaryClassName(suModelAddParam.getSecondaryClassName());
		tbSuModel.setThreeClassId(Long.valueOf(suModelAddParam.getThreeClassId()));
		tbSuModel.setThreeClassName(suModelAddParam.getThreeClassName());
		tbSuModel.setStyleId(Long.valueOf(suModelAddParam.getStyleId()));
		tbSuModel.setStyleName(suModelAddParam.getStyleName());
		tbSuModel.setTitle(suModelAddParam.getTitle());
		tbSuModel.setType(suModelAddParam.getType());
		tbSuModel.setPrice(suModelAddParam.getPrice());
		tbSuModel.setTextureMapping(suModelAddParam.getTextureMapping());
		tbSuModel.setRemarks(suModelAddParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
		tbSuModel.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbSuModel.setCreateId(user.getId());
		tbSuModel.setCreateName(user.getName());
		tbSuModel.setCreateTime(LocalDateTime.now());
		tbSuModel.setUpdateId(user.getId());
		tbSuModel.setUpdateName(user.getName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbSuModel);
		
		//新增上传文件
		for (UploadInfo uploadInfo : suModelAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbSuModel.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(ReTypeEnum.SUMODEL.getKey());
            tbAttachment.setUploadVersion(uploadInfo.getUploadVersion());
            tbAttachment.setImgType(uploadInfo.getImgType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getImgType() == 1) {
            	tbSuModel.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbSuModel);
            }
		}
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(user.getId());
        tbExamineVerify.setUserName(user.getName());
        tbExamineVerify.setReId(tbSuModel.getId());
        tbExamineVerify.setReType(ReTypeEnum.SUMODEL.getKey());
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
	public void updteSuModel(SuModelUpdateParam suModelUpdateParam) {
		TbSuModel tbSuModel = baseMapper.selectById(suModelUpdateParam.getSuModelId());
		tbSuModel.setPrimaryClassId(Long.valueOf(suModelUpdateParam.getPrimaryClassId()));
		tbSuModel.setPrimaryClassName(suModelUpdateParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(Long.valueOf(suModelUpdateParam.getSecondaryClassId()));
		tbSuModel.setSecondaryClassName(suModelUpdateParam.getSecondaryClassName());
		tbSuModel.setThreeClassId(Long.valueOf(suModelUpdateParam.getThreeClassId()));
		tbSuModel.setThreeClassName(suModelUpdateParam.getThreeClassName());
		tbSuModel.setStyleId(Long.valueOf(suModelUpdateParam.getStyleId()));
		tbSuModel.setStyleName(suModelUpdateParam.getStyleName());
		tbSuModel.setTitle(suModelUpdateParam.getTitle());
		tbSuModel.setType(suModelUpdateParam.getType());
		tbSuModel.setPrice(suModelUpdateParam.getPrice());
		tbSuModel.setTextureMapping(suModelUpdateParam.getTextureMapping());
		tbSuModel.setRemarks(suModelUpdateParam.getRemarks());
		tbSuModel.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbSuModel.setUpdateId(user.getId());
		tbSuModel.setUpdateName(user.getName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbSuModel);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, suModelUpdateParam.getSuModelId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
		for (UploadInfo uploadInfo : suModelUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbSuModel.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(ReTypeEnum.SUMODEL.getKey());
            tbAttachment.setUploadVersion(uploadInfo.getUploadVersion());
            tbAttachment.setImgType(uploadInfo.getImgType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
            tbAttachment.setCreateId(user.getId());
            tbAttachment.setCreateName(user.getName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getImgType() == 1) {
            	tbSuModel.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbSuModel);
            }
		}
	}

}
