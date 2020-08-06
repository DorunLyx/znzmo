package com.pactera.znzmo.sumodel;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.StatusEnum;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.vo.ModelAddParam;
import com.pactera.znzmo.vo.ModelQueryParam;
import com.pactera.znzmo.vo.ModelUpdateParam;

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
	public void addSuModel(ModelAddParam modelAddParam) {
		TbSuModel tbSuModel = new TbSuModel();
		tbSuModel.setMainGraph(modelAddParam.getMainGraph());
		tbSuModel.setCode(NumGenerationUtil.getrandom());
		tbSuModel.setPrimaryClassId(modelAddParam.getPrimaryClassId());
		tbSuModel.setPrimaryClassName(modelAddParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(modelAddParam.getSecondaryClassId());
		tbSuModel.setSecondaryClassName(modelAddParam.getSecondaryClassName());
		tbSuModel.setStyleId(modelAddParam.getStyleId());
		tbSuModel.setStyleName(modelAddParam.getStyleName());
		tbSuModel.setTitle(modelAddParam.getTitle());
		tbSuModel.setModelType(modelAddParam.getModelType());
		tbSuModel.setModelPrice(modelAddParam.getModelPrice());
		tbSuModel.setTextureMapping(modelAddParam.getTextureMapping());
		tbSuModel.setRemarks(modelAddParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
		tbSuModel.setStatus(StatusEnum.START_USE.getKey());
//		tbSuModel.setCreateId(user.getUserId());
//		tbSuModel.setCreateName(user.getUserName());
		tbSuModel.setCreateTime(LocalDateTime.now());
//		tbSuModel.setUpdateId(user.getUserId());
//		tbSuModel.setUpdateName(user.getUserName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbSuModel);
	}

	@Override
	public void updteSuModel(ModelUpdateParam modelUpdateParam) {
		TbSuModel tbSuModel = new TbSuModel();
		tbSuModel.setId(modelUpdateParam.getModelId());
		tbSuModel.setMainGraph(modelUpdateParam.getMainGraph());
		tbSuModel.setPrimaryClassId(modelUpdateParam.getPrimaryClassId());
		tbSuModel.setPrimaryClassName(modelUpdateParam.getPrimaryClassName());
		tbSuModel.setSecondaryClassId(modelUpdateParam.getSecondaryClassId());
		tbSuModel.setSecondaryClassName(modelUpdateParam.getSecondaryClassName());
		tbSuModel.setStyleId(modelUpdateParam.getStyleId());
		tbSuModel.setStyleName(modelUpdateParam.getStyleName());
		tbSuModel.setTitle(modelUpdateParam.getTitle());
		tbSuModel.setModelType(modelUpdateParam.getModelType());
		tbSuModel.setModelPrice(modelUpdateParam.getModelPrice());
		tbSuModel.setTextureMapping(modelUpdateParam.getTextureMapping());
		tbSuModel.setRemarks(modelUpdateParam.getRemarks());
		tbSuModel.setIsValid(IsValidEnum.YES.getKey());
//		tb3dModel.setCreateId(user.getUserId());
//		tb3dModel.setCreateName(user.getUserName());
		tbSuModel.setCreateTime(LocalDateTime.now());
//		tb3dModel.setUpdateId(user.getUserId());
//		tb3dModel.setUpdateName(user.getUserName());
		tbSuModel.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbSuModel);
	}

}
