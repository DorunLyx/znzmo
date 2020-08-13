package com.pactera.znzmo.database;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.DatabaseAddParam;
import com.pactera.znzmo.vo.DatabaseUpdateParam;
import com.pactera.znzmo.vo.ModelQueryParam;

/**
 * <p>
 * 资料库表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbDatabaseService extends IService<TbDatabase> {

	/**
	 * @Title: selectDatabasePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<TbDatabase>
	 * @author liyongxu
	 * @date 2020年8月13日 上午11:48:11 
	*/
	IPage<TbDatabase> selectDatabasePages(Page<TbDatabase> page, ModelQueryParam modelQueryParam);

	/**
	 * @Title: addDatabase 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param databaseAddParam void
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:13:16 
	*/
	void addDatabase(DatabaseAddParam databaseAddParam);

	/**
	 * @Title: updteDatabase 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param databaseUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:15:14 
	*/
	void updteDatabase(DatabaseUpdateParam databaseUpdateParam);

}
