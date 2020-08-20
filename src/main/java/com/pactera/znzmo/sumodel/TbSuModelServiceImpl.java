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
import com.pactera.znzmo.util.NumGenerationUtil;
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
	
	@Override
	public IPage<TbSuModel> selectSuModelPages(Page<TbSuModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbSuModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			queryWrapper.eq(TbSuModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			queryWrapper.eq(TbSuModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtils.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
			queryWrapper.eq(TbSuModel.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		queryWrapper.orderByDesc(TbSuModel.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addSuModel(SuModelAddParam suModelAddParam) {
		TbSuModel tbSuModel = new TbSuModel();
		tbSuModel.setMainGraph(suModelAddParam.getMainGraph());
		tbSuModel.setCode(NumGenerationUtil.getrandom());
		tbSuModel.setPrimaryClassId(suModelAddParam.getPrimaryClassId());
		tbSuModel.setPrimaryClassName(suModelAddParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(suModelAddParam.getSecondaryClassId());
		tbSuModel.setSecondaryClassName(suModelAddParam.getSecondaryClassName());
		tbSuModel.setThreeClassId(suModelAddParam.getThreeClassId());
		tbSuModel.setThreeClassName(suModelAddParam.getThreeClassName());
		tbSuModel.setStyleId(suModelAddParam.getStyleId());
		tbSuModel.setStyleName(suModelAddParam.getStyleName());
		tbSuModel.setTitle(suModelAddParam.getTitle());
		tbSuModel.setType(suModelAddParam.getType());
		tbSuModel.setPrice(suModelAddParam.getPrice());
		tbSuModel.setTextureMapping(suModelAddParam.getTextureMapping());
		tbSuModel.setRemarks(suModelAddParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
		tbSuModel.setStatus(ApproveStatusEnum.WAITAPPROVAL.getKey());
//		tbSuModel.setCreateId(user.getUserId());
//		tbSuModel.setCreateName(user.getUserName());
		tbSuModel.setCreateTime(LocalDateTime.now());
//		tbSuModel.setUpdateId(user.getUserId());
//		tbSuModel.setUpdateName(user.getUserName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbSuModel);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : suModelAddParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbSuModel.getId());
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
        tbExamineVerify.setReId(tbSuModel.getId());
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
	public void updteSuModel(SuModelUpdateParam suModelUpdateParam) {
		TbSuModel tbSuModel = baseMapper.selectById(suModelUpdateParam.getSuModelId());
		tbSuModel.setMainGraph(suModelUpdateParam.getMainGraph());
		tbSuModel.setPrimaryClassId(suModelUpdateParam.getPrimaryClassId());
		tbSuModel.setPrimaryClassName(suModelUpdateParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(suModelUpdateParam.getSecondaryClassId());
		tbSuModel.setSecondaryClassName(suModelUpdateParam.getSecondaryClassName());
		tbSuModel.setThreeClassId(suModelUpdateParam.getThreeClassId());
		tbSuModel.setThreeClassName(suModelUpdateParam.getThreeClassName());
		tbSuModel.setStyleId(suModelUpdateParam.getStyleId());
		tbSuModel.setStyleName(suModelUpdateParam.getStyleName());
		tbSuModel.setTitle(suModelUpdateParam.getTitle());
		tbSuModel.setType(suModelUpdateParam.getType());
		tbSuModel.setPrice(suModelUpdateParam.getPrice());
		tbSuModel.setTextureMapping(suModelUpdateParam.getTextureMapping());
		tbSuModel.setRemarks(suModelUpdateParam.getRemarks());
//		tbSuModel.setUpdateId(user.getUserId());
//		tbSuModel.setUpdateName(user.getUserName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbSuModel);
		
		QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbAttachment.RELATION_ID, suModelUpdateParam.getSuModelId());
		tbAttachmentMapper.delete(queryWrapper);
		
		//新增上传文件
        for (UploadInfo keyAndUrl : suModelUpdateParam.getUploadImg()) {
            TbAttachment tbAttachment = new TbAttachment();
            tbAttachment.setRelationId(tbSuModel.getId());
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
