package com.pactera.znzmo.hd;

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
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.hd.HDMappingAddParam;
import com.pactera.znzmo.vo.hd.HDMappingUpdateParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;

/**
 * <p>
 * 高清贴图表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbHdMappingServiceImpl extends ServiceImpl<TbHdMappingMapper, TbHdMapping> implements TbHdMappingService {
	
	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbHdMapping> selectHdMappingPages(Page<TbHdMapping> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbHdMapping> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbHdMapping.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbHdMapping.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId())) {
			queryWrapper.eq(TbHdMapping.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId())) {
			queryWrapper.eq(TbHdMapping.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId())) {
			queryWrapper.eq(TbHdMapping.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbHdMapping.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addHdMapping(HDMappingAddParam hdMappingAddParam) {
		TbHdMapping tbHdMapping = new TbHdMapping();
		tbHdMapping.setCode(NumGenerationUtil.getrandom());
		tbHdMapping.setPrimaryClassId(Long.valueOf(hdMappingAddParam.getPrimaryClassId()));
		tbHdMapping.setPrimaryClassName(hdMappingAddParam.getPrimaryClassName());
		tbHdMapping.setSecondaryClassId(Long.valueOf(hdMappingAddParam.getSecondaryClassId()));
		tbHdMapping.setSecondaryClassName(hdMappingAddParam.getSecondaryClassName());
		tbHdMapping.setStyleId(Long.valueOf(hdMappingAddParam.getStyleId()));
		tbHdMapping.setStyleName(hdMappingAddParam.getStyleName());
		tbHdMapping.setTitle(hdMappingAddParam.getTitle());
		tbHdMapping.setType(hdMappingAddParam.getType());
		tbHdMapping.setPrice(hdMappingAddParam.getPrice());
		tbHdMapping.setRemarks(hdMappingAddParam.getRemarks());
		tbHdMapping.setIsValid(IsValidEnum.YES.getKey());
		tbHdMapping.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbHdMapping.setCreateId(user.getId());
		tbHdMapping.setCreateName(user.getName());
		tbHdMapping.setCreateTime(LocalDateTime.now());
		tbHdMapping.setUpdateId(user.getId());
		tbHdMapping.setUpdateName(user.getName());
		tbHdMapping.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbHdMapping);
		
		//新增上传文件
		for (UploadInfo uploadInfo : hdMappingAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbHdMapping.getId());
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
            	tbHdMapping.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbHdMapping);
            }
		}
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(user.getId());
        tbExamineVerify.setUserName(user.getName());
        tbExamineVerify.setReId(tbHdMapping.getId());
        tbExamineVerify.setReType(ReTypeEnum.HD.getKey());
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
	public void updteHdMapping(HDMappingUpdateParam hDMappingUpdateParam) {
		TbHdMapping tbHdMapping = baseMapper.selectById(hDMappingUpdateParam.getHdId());
		tbHdMapping.setPrimaryClassId(Long.valueOf(hDMappingUpdateParam.getPrimaryClassId()));
		tbHdMapping.setPrimaryClassName(hDMappingUpdateParam.getPrimaryClassName());
		tbHdMapping.setSecondaryClassId(Long.valueOf(hDMappingUpdateParam.getSecondaryClassId()));
		tbHdMapping.setSecondaryClassName(hDMappingUpdateParam.getSecondaryClassName());
		tbHdMapping.setStyleId(Long.valueOf(hDMappingUpdateParam.getStyleId()));
		tbHdMapping.setStyleName(hDMappingUpdateParam.getStyleName());
		tbHdMapping.setTitle(hDMappingUpdateParam.getTitle());
		tbHdMapping.setType(hDMappingUpdateParam.getType());
		tbHdMapping.setPrice(hDMappingUpdateParam.getPrice());
		tbHdMapping.setRemarks(hDMappingUpdateParam.getRemarks());
		tbHdMapping.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbHdMapping.setUpdateId(user.getId());
		tbHdMapping.setUpdateName(user.getName());
		tbHdMapping.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbHdMapping);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, hDMappingUpdateParam.getHdId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
		for (UploadInfo uploadInfo : hDMappingUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbHdMapping.getId());
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
            	tbHdMapping.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbHdMapping);
            }
		}
	}

}
