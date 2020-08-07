package com.pactera.znzmo.sumodel;

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
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.vo.ModelQueryParam;
import com.pactera.znzmo.vo.SuModelAddParam;
import com.pactera.znzmo.vo.SuModelUpdateParam;
import com.pactera.znzmo.vo.UploadInfo;

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
	
	@Override
	public IPage<TbSuModel> selectSuModelPages(Page<TbSuModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(modelQueryParam.getKeyword())) {
			queryWrapper.like(TbSuModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			queryWrapper.eq(TbSuModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			queryWrapper.eq(TbSuModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
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
		tbSuModel.setModelType(suModelAddParam.getModelType());
		tbSuModel.setModelPrice(suModelAddParam.getModelPrice());
		tbSuModel.setTextureMapping(suModelAddParam.getTextureMapping());
		tbSuModel.setRemarks(suModelAddParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
		tbSuModel.setStatus(StatusEnum.START_USE.getKey());
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
		tbSuModel.setModelType(suModelUpdateParam.getModelType());
		tbSuModel.setModelPrice(suModelUpdateParam.getModelPrice());
		tbSuModel.setTextureMapping(suModelUpdateParam.getTextureMapping());
		tbSuModel.setRemarks(suModelUpdateParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
//		tbSuModel.setCreateId(user.getUserId());
//		tbSuModel.setCreateName(user.getUserName());
		tbSuModel.setCreateTime(LocalDateTime.now());
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
