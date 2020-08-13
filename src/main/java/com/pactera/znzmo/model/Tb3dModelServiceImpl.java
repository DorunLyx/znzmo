package com.pactera.znzmo.model;

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
import com.pactera.znzmo.model.dao.Tb3dModelMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
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
public class Tb3dModelServiceImpl extends ServiceImpl<Tb3dModelMapper, Tb3dModel> implements Tb3dModelService {

	@Autowired
	private TbAttachmentMapper tbAttachmentMapper;
	
	@Autowired
	private TbExamineVerifyMapper tbExamineVerifyMapper;
	
	@Override
	public IPage<Tb3dModel> select3DModelPages(Page<Tb3dModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<Tb3dModel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(Tb3dModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(Tb3dModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			queryWrapper.eq(Tb3dModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			queryWrapper.eq(Tb3dModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
			queryWrapper.eq(Tb3dModel.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(Tb3dModel.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void add3DModel(ModelAddParam modelAddParam) {
		Tb3dModel tb3dModel = new Tb3dModel();
		tb3dModel.setMainGraph(modelAddParam.getMainGraph());
		tb3dModel.setCode(NumGenerationUtil.getrandom());
		tb3dModel.setPrimaryClassId(modelAddParam.getPrimaryClassId());
		tb3dModel.setPrimaryClassName(modelAddParam.getPrimaryClassName());
		tb3dModel.setSecondaryClassId(modelAddParam.getSecondaryClassId());
		tb3dModel.setSecondaryClassName(modelAddParam.getSecondaryClassName());
		tb3dModel.setStyleId(modelAddParam.getStyleId());
		tb3dModel.setStyleName(modelAddParam.getStyleName());
		tb3dModel.setTitle(modelAddParam.getTitle());
		tb3dModel.setType(modelAddParam.getType());
		tb3dModel.setPrice(modelAddParam.getPrice());
		tb3dModel.setTextureMapping(modelAddParam.getTextureMapping());
		tb3dModel.setLightingEffects(modelAddParam.getLightingEffects());
		tb3dModel.setRemarks(modelAddParam.getRemarks());
		tb3dModel.setIsValid(IsValidEnum.YES.getKey());
		tb3dModel.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tb3dModel.setCreateId(user.getUserId());
//		tb3dModel.setCreateName(user.getUserName());
		tb3dModel.setCreateTime(LocalDateTime.now());
//		tb3dModel.setUpdateId(user.getUserId());
//		tb3dModel.setUpdateName(user.getUserName());
		tb3dModel.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tb3dModel);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : modelAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tb3dModel.getId());
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
        tbExamineVerify.setReId(tb3dModel.getId());
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
		Tb3dModel tb3dModel = baseMapper.selectById(modelUpdateParam.getModelId());
		tb3dModel.setMainGraph(modelUpdateParam.getMainGraph());
		tb3dModel.setPrimaryClassId(modelUpdateParam.getPrimaryClassId());
		tb3dModel.setPrimaryClassName(modelUpdateParam.getPrimaryClassName());
		tb3dModel.setSecondaryClassId(modelUpdateParam.getSecondaryClassId());
		tb3dModel.setSecondaryClassName(modelUpdateParam.getSecondaryClassName());
		tb3dModel.setStyleId(modelUpdateParam.getStyleId());
		tb3dModel.setStyleName(modelUpdateParam.getStyleName());
		tb3dModel.setTitle(modelUpdateParam.getTitle());
		tb3dModel.setType(modelUpdateParam.getType());
		tb3dModel.setPrice(modelUpdateParam.getPrice());
		tb3dModel.setTextureMapping(modelUpdateParam.getTextureMapping());
		tb3dModel.setLightingEffects(modelUpdateParam.getLightingEffects());
		tb3dModel.setRemarks(modelUpdateParam.getRemarks());
//		tb3dModel.setUpdateId(user.getUserId());
//		tb3dModel.setUpdateName(user.getUserName());
		tb3dModel.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tb3dModel);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, modelUpdateParam.getModelId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : modelUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tb3dModel.getId());
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
