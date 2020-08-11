package com.pactera.znzmo.points;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.PointsQueryParam;

/**
 * <p>
 * 积分明细表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbPointsDetailsService extends IService<TbPointsDetails> {

	/**
	 * @Title: selectPointsExchangePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param pointsQueryParam
	 * @return IPage<TbPointsDetails>
	 * @author liyongxu
	 * @date 2020年8月11日 上午11:00:27 
	*/
	IPage<TbPointsDetails> selectPointsExchangePages(Page<TbPointsDetails> page, PointsQueryParam pointsQueryParam);

}
