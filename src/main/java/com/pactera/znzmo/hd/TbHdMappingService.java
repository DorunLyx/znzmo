package com.pactera.znzmo.hd;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.hd.HDMappingAddParam;
import com.pactera.znzmo.vo.hd.HDMappingUpdateParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;

/**
 * <p>
 * 高清贴图表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbHdMappingService extends IService<TbHdMapping> {

	/**
	 * @Title: selectHdMappingPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<TbHdMapping>
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:36:01 
	*/
	IPage<TbHdMapping> selectHdMappingPages(Page<TbHdMapping> page, ModelQueryParam modelQueryParam);

	/**
	 * @Title: addHdMapping 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param hdMappingAddParam void
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:51:35 
	*/
	void addHdMapping(HDMappingAddParam hdMappingAddParam);

	/**
	 * @Title: updteHdMapping 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param hDMappingUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:53:40 
	*/
	void updteHdMapping(HDMappingUpdateParam hDMappingUpdateParam);

}
