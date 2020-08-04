package com.pactera.znzmo.model;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
		// TODO Auto-generated method stub
		return null;
	}

}
