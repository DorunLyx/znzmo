package com.pactera.znzmo.model;

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
import com.pactera.znzmo.model.dao.TbThreedModelMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.model.ModelAddParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.vo.model.ModelUpdateParam;

/**
 * <p>
 * 3D模型表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbThreedModelServiceImpl extends ServiceImpl<TbThreedModelMapper, TbThreedModel> implements TbThreedModelService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Override
	public IPage<TbThreedModel> select3DModelPages(Page<TbThreedModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbThreedModel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbThreedModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbThreedModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId())) {
			queryWrapper.eq(TbThreedModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId())) {
			queryWrapper.eq(TbThreedModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId())) {
			queryWrapper.eq(TbThreedModel.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbThreedModel.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void add3DModel(ModelAddParam modelAddParam) {
		TbThreedModel tbThreedModel = new TbThreedModel();
		tbThreedModel.setCode(NumGenerationUtil.getrandom());
		tbThreedModel.setPrimaryClassId(Long.valueOf(modelAddParam.getPrimaryClassId()));
		tbThreedModel.setPrimaryClassName(modelAddParam.getPrimaryClassName());
		tbThreedModel.setSecondaryClassId(Long.valueOf(modelAddParam.getSecondaryClassId()));
		tbThreedModel.setSecondaryClassName(modelAddParam.getSecondaryClassName());
		tbThreedModel.setStyleId(Long.valueOf(modelAddParam.getStyleId()));
		tbThreedModel.setStyleName(modelAddParam.getStyleName());
		tbThreedModel.setTitle(modelAddParam.getTitle());
		tbThreedModel.setType(modelAddParam.getType());
		tbThreedModel.setPrice(modelAddParam.getPrice());
		tbThreedModel.setTextureMapping(modelAddParam.getTextureMapping());
		tbThreedModel.setLightingEffects(modelAddParam.getLightingEffects());
		tbThreedModel.setRemarks(modelAddParam.getRemarks());
		tbThreedModel.setIsValid(IsValidEnum.YES.getKey());
		tbThreedModel.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tbThreedModel.setCreateId(user.getUserId());
//		tbThreedModel.setCreateName(user.getUserName());
		tbThreedModel.setCreateTime(LocalDateTime.now());
//		tbThreedModel.setUpdateId(user.getUserId());
//		tbThreedModel.setUpdateName(user.getUserName());
		tbThreedModel.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbThreedModel);
		
		//新增上传文件
        for (UploadInfo uploadInfo : modelAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbThreedModel.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(uploadInfo.getType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//            tbAttachment.setCreateId(user.getUserId());
//            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getType() == 1) {
            	tbThreedModel.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbThreedModel);
            }
        }
        
        TbExamineVerify tbExamineVerify = new TbExamineVerify();
        tbExamineVerify.setUserId(1L);
        tbExamineVerify.setUserName("admin");
        tbExamineVerify.setReId(tbThreedModel.getId());
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
	public void updte3DModel(ModelUpdateParam modelUpdateParam) {
		TbThreedModel tbThreedModel = baseMapper.selectById(modelUpdateParam.getModelId());
		tbThreedModel.setPrimaryClassId(Long.valueOf(modelUpdateParam.getPrimaryClassId()));
		tbThreedModel.setPrimaryClassName(modelUpdateParam.getPrimaryClassName());
		tbThreedModel.setSecondaryClassId(Long.valueOf(modelUpdateParam.getSecondaryClassId()));
		tbThreedModel.setSecondaryClassName(modelUpdateParam.getSecondaryClassName());
		tbThreedModel.setStyleId(Long.valueOf(modelUpdateParam.getStyleId()));
		tbThreedModel.setStyleName(modelUpdateParam.getStyleName());
		tbThreedModel.setTitle(modelUpdateParam.getTitle());
		tbThreedModel.setType(modelUpdateParam.getType());
		tbThreedModel.setPrice(modelUpdateParam.getPrice());
		tbThreedModel.setTextureMapping(modelUpdateParam.getTextureMapping());
		tbThreedModel.setLightingEffects(modelUpdateParam.getLightingEffects());
		tbThreedModel.setRemarks(modelUpdateParam.getRemarks());
		tbThreedModel.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tbThreedModel.setUpdateId(user.getUserId());
//		tbThreedModel.setUpdateName(user.getUserName());
		tbThreedModel.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbThreedModel);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, modelUpdateParam.getModelId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
		for (UploadInfo uploadInfo : modelUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbThreedModel.getId());
            tbAttachment.setAttachmentName(uploadInfo.getFileName());
            tbAttachment.setAttachmentPath(uploadInfo.getFile());
            tbAttachment.setPhysicalPath(uploadInfo.getFile());
            tbAttachment.setAliasName(uploadInfo.getRealName());
            tbAttachment.setReType(uploadInfo.getType());
            tbAttachment.setSuffix(uploadInfo.getFileSuffix());
            tbAttachment.setAttachmentSize(uploadInfo.getSizes());
            tbAttachment.setPictureSize(uploadInfo.getPictureSize());
            tbAttachment.setIsValid(IsValidEnum.YES.getKey());
//	            tbAttachment.setCreateId(user.getUserId());
//	            tbAttachment.setCreateName(user.getUserName());
            tbAttachment.setCreateTime(LocalDateTime.now());
            tbAttachment.setUpdateTime(LocalDateTime.now());
            tbAttachmentMapper.insert(tbAttachment);
            if(uploadInfo.getType() == 1) {
            	tbThreedModel.setMainGraph(tbAttachment.getId().toString());
            	baseMapper.updateById(tbThreedModel);
            }
		}
	}

}
