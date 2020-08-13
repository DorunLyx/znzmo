package com.pactera.znzmo.hd;

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
import com.pactera.znzmo.enums.ApproveStatusEnum;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.examineverify.TbExamineVerify;
import com.pactera.znzmo.examineverify.dao.TbExamineVerifyMapper;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
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
	
	@Override
	public IPage<TbHdMapping> selectHdMappingPages(Page<TbHdMapping> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbHdMapping> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbHdMapping.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbHdMapping.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			queryWrapper.eq(TbHdMapping.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			queryWrapper.eq(TbHdMapping.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
			queryWrapper.eq(TbHdMapping.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbHdMapping.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addHdMapping(HDMappingAddParam hdMappingAddParam) {
		TbHdMapping tbHdMapping = new TbHdMapping();
		tbHdMapping.setMainGraph(hdMappingAddParam.getMainGraph());
		tbHdMapping.setCode(NumGenerationUtil.getrandom());
		tbHdMapping.setPrimaryClassId(hdMappingAddParam.getPrimaryClassId());
		tbHdMapping.setPrimaryClassName(hdMappingAddParam.getPrimaryClassName());
		tbHdMapping.setSecondaryClassId(hdMappingAddParam.getSecondaryClassId());
		tbHdMapping.setSecondaryClassName(hdMappingAddParam.getSecondaryClassName());
		tbHdMapping.setStyleId(hdMappingAddParam.getStyleId());
		tbHdMapping.setStyleName(hdMappingAddParam.getStyleName());
		tbHdMapping.setTitle(hdMappingAddParam.getTitle());
		tbHdMapping.setType(hdMappingAddParam.getType());
		tbHdMapping.setPrice(hdMappingAddParam.getPrice());
		tbHdMapping.setRemarks(hdMappingAddParam.getRemarks());
		tbHdMapping.setIsValid(IsValidEnum.YES.getKey());
		tbHdMapping.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tbHdMapping.setCreateId(user.getUserId());
//		tbHdMapping.setCreateName(user.getUserName());
		tbHdMapping.setCreateTime(LocalDateTime.now());
//		tbHdMapping.setUpdateId(user.getUserId());
//		tbHdMapping.setUpdateName(user.getUserName());
		tbHdMapping.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbHdMapping);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : hdMappingAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbHdMapping.getId());
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
        tbExamineVerify.setReId(tbHdMapping.getId());
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
	public void updteHdMapping(HDMappingUpdateParam hDMappingUpdateParam) {
		TbHdMapping tbHdMapping = baseMapper.selectById(hDMappingUpdateParam.getHdId());
		tbHdMapping.setMainGraph(hDMappingUpdateParam.getMainGraph());
		tbHdMapping.setPrimaryClassId(hDMappingUpdateParam.getPrimaryClassId());
		tbHdMapping.setPrimaryClassName(hDMappingUpdateParam.getPrimaryClassName());
		tbHdMapping.setSecondaryClassId(hDMappingUpdateParam.getSecondaryClassId());
		tbHdMapping.setSecondaryClassName(hDMappingUpdateParam.getSecondaryClassName());
		tbHdMapping.setStyleId(hDMappingUpdateParam.getStyleId());
		tbHdMapping.setStyleName(hDMappingUpdateParam.getStyleName());
		tbHdMapping.setTitle(hDMappingUpdateParam.getTitle());
		tbHdMapping.setType(hDMappingUpdateParam.getType());
		tbHdMapping.setPrice(hDMappingUpdateParam.getPrice());
		tbHdMapping.setRemarks(hDMappingUpdateParam.getRemarks());
//		tbHdMapping.setUpdateId(user.getUserId());
//		tbHdMapping.setUpdateName(user.getUserName());
		tbHdMapping.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbHdMapping);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, hDMappingUpdateParam.getHdId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : hDMappingUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbHdMapping.getId());
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
