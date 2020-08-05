package com.pactera.znzmo.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.ModelAddParam;
import com.pactera.znzmo.vo.ModelQueryParam;
import com.pactera.znzmo.vo.ModelUpdateParam;

/**
 * <p>
 * 3D模型表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface Tb3dModelService extends IService<Tb3dModel> {

	/**
	 * @Title: select3DModelPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<Tb3dModel>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:46:41 
	*/
	IPage<Tb3dModel> select3DModelPages(Page<Tb3dModel> page, ModelQueryParam modelQueryParam);

	/**
	 * @Title: add3DModel 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param modelAddParam void
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:40:35 
	*/
	void add3DModel(ModelAddParam modelAddParam);

	/**
	 * @Title: updte3DModel 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param modelUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:54:56 
	*/
	void updte3DModel(ModelUpdateParam modelUpdateParam);

}
