package com.pactera.znzmo.points;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.points.PointsAddParam;
import com.pactera.znzmo.vo.points.PointsQueryParam;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbPointsService extends IService<TbPoints> {

	/**
	 * @Title: selectPointsManagePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param pointsQueryParam
	 * @return IPage<TbPointsDetails>
	 * @author liyongxu
	 * @date 2020年8月11日 上午11:07:17 
	*/
	IPage<TbPoints> selectPointsManagePages(Page<TbPoints> page, PointsQueryParam pointsQueryParam);

	/**
	 * @Title: addPoints 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param pointsAddParam void
	 * @author liyongxu
	 * @date 2020年8月11日 下午2:10:54 
	*/
	void addPoints(PointsAddParam pointsAddParam);

}
