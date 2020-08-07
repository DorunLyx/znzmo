package com.pactera.znzmo.sumodel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.ModelQueryParam;
import com.pactera.znzmo.vo.SuModelAddParam;
import com.pactera.znzmo.vo.SuModelUpdateParam;

/**
 * <p>
 * su模型表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbSuModelService extends IService<TbSuModel> {

	/**
	 * @Title: selectSuModelPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<TbSuModel>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:42:48 
	*/
	IPage<TbSuModel> selectSuModelPages(Page<TbSuModel> page, ModelQueryParam modelQueryParam);

	/**
	 * @Title: addSuModel 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param suModelAddParam void
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:45:43 
	*/
	void addSuModel(SuModelAddParam suModelAddParam);

	/**
	 * @Title: updteSuModel 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param suModelUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:46:12 
	*/
	void updteSuModel(SuModelUpdateParam suModelUpdateParam);

}
