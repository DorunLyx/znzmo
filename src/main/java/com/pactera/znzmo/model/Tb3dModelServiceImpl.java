package com.pactera.znzmo.model;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.model.dao.Tb3dModelMapper;
import com.pactera.znzmo.vo.ModelQueryParam;

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

	@Override
	public IPage<Tb3dModel> select3DModelPages(Page<Tb3dModel> page, ModelQueryParam modelQueryParam) {
		QueryWrapper<Tb3dModel> assetQueryWrapper = new QueryWrapper<>();
		assetQueryWrapper.eq(Tb3dModel.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(modelQueryParam.getKeyword())) {
			assetQueryWrapper.like(Tb3dModel.TITLE, modelQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getStyleId().toString())) {
			assetQueryWrapper.eq(Tb3dModel.STYLE_ID, modelQueryParam.getStyleId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getPrimaryClassId().toString())) {
			assetQueryWrapper.eq(Tb3dModel.PRIMARY_CLASS_ID, modelQueryParam.getPrimaryClassId());
		}
		if(StringUtil.isNotEmpty(modelQueryParam.getSecondaryClassId().toString())) {
			assetQueryWrapper.eq(Tb3dModel.SECONDARY_CLASS_ID, modelQueryParam.getSecondaryClassId());
		}
		assetQueryWrapper.orderByDesc(Tb3dModel.UPDATE_TIME);
		return baseMapper.selectPage(page,assetQueryWrapper);
	}

}
