package com.pactera.znzmo.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.model.ModelAddParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.vo.model.ModelUpdateParam;

/**
 * <p>
 * 3D模型表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbThreedModelService extends IService<TbThreedModel> {

	/**
	 * @Title: select3DModelPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<TbThreedModel>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:46:41 
	*/
	IPage<TbThreedModel> select3DModelPages(Page<TbThreedModel> page, ModelQueryParam modelQueryParam);

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
