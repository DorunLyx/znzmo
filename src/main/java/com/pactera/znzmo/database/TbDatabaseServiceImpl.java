package com.pactera.znzmo.database;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.dao.TbAttachmentMapper;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.enums.ApproveStatusEnum;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.examineverify.TbExamineVerify;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.database.DatabaseAddParam;
import com.pactera.znzmo.vo.database.DatabaseUpdateParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;

/**
 * <p>
 * 资料库表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbDatabaseServiceImpl extends ServiceImpl<TbDatabaseMapper, TbDatabase> implements TbDatabaseService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbDatabase> selectDatabasePages(Page<TbDatabase> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbDatabase> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbDatabase.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbDatabase.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId())) {
			queryWrapper.eq(TbDatabase.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId())) {
			queryWrapper.eq(TbDatabase.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId())) {
			queryWrapper.eq(TbDatabase.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbDatabase.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addDatabase(DatabaseAddParam databaseAddParam) {
		TbDatabase tbDatabase = new TbDatabase();
		tbDatabase.setCode(NumGenerationUtil.getrandom());
		tbDatabase.setPrimaryClassId(Long.valueOf(databaseAddParam.getPrimaryClassId()));
		tbDatabase.setPrimaryClassName(databaseAddParam.getPrimaryClassName());
		tbDatabase.setSecondaryClassId(Long.valueOf(databaseAddParam.getSecondaryClassId()));
		tbDatabase.setSecondaryClassName(databaseAddParam.getSecondaryClassName());
		tbDatabase.setStyleId(Long.valueOf(databaseAddParam.getStyleId()));
		tbDatabase.setStyleName(databaseAddParam.getStyleName());
		tbDatabase.setTitle(databaseAddParam.getTitle());
		tbDatabase.setType(databaseAddParam.getType());
		tbDatabase.setPrice(databaseAddParam.getPrice());
		tbDatabase.setRemarks(databaseAddParam.getRemarks());
		tbDatabase.setIsValid(IsValidEnum.YES.getKey());
		tbDatabase.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbDatabase.setCreateId(user.getId());
		tbDatabase.setCreateName(user.getName());
		tbDatabase.setCreateTime(LocalDateTime.now());
		tbDatabase.setUpdateId(user.getId());
		tbDatabase.setUpdateName(user.getName());
		tbDatabase.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbDatabase);
		
		//新增上传文件
		for (UploadInfo uploadInfo : databaseAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDatabase.getId());
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
            	tbDatabase.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbDatabase);
            }
		}
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(user.getId());
        tbExamineVerify.setUserName(user.getName());
        tbExamineVerify.setReId(tbDatabase.getId());
        tbExamineVerify.setReType(ReTypeEnum.DATABASE.getKey());
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
	public void updteDatabase(DatabaseUpdateParam databaseUpdateParam) {
		TbDatabase tbDatabase = baseMapper.selectById(databaseUpdateParam.getDatabaseId());
		tbDatabase.setPrimaryClassId(Long.valueOf(databaseUpdateParam.getPrimaryClassId()));
		tbDatabase.setPrimaryClassName(databaseUpdateParam.getPrimaryClassName());
		tbDatabase.setSecondaryClassId(Long.valueOf(databaseUpdateParam.getSecondaryClassId()));
		tbDatabase.setSecondaryClassName(databaseUpdateParam.getSecondaryClassName());
		tbDatabase.setStyleId(Long.valueOf(databaseUpdateParam.getStyleId()));
		tbDatabase.setStyleName(databaseUpdateParam.getStyleName());
		tbDatabase.setTitle(databaseUpdateParam.getTitle());
		tbDatabase.setType(databaseUpdateParam.getType());
		tbDatabase.setPrice(databaseUpdateParam.getPrice());
		tbDatabase.setRemarks(databaseUpdateParam.getRemarks());
		tbDatabase.setStatus(ApproveStatusEnum.WAIT.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbDatabase.setUpdateId(user.getId());
		tbDatabase.setUpdateName(user.getName());
		tbDatabase.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbDatabase);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, databaseUpdateParam.getDatabaseId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
		for (UploadInfo uploadInfo : databaseUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbDatabase.getId());
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
            	tbDatabase.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbDatabase);
            }
		}
	}

}
