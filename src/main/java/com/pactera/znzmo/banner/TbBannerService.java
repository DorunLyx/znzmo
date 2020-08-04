package com.pactera.znzmo.banner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.HomePageListVO;
import com.pactera.znzmo.vo.HomePageQueryParam;

/**
 * <p>
 * 轮播管理表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbBannerService extends IService<TbBanner> {

	/**
	 * @Title: selectHomePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param homePageQueryParam
	 * @return IPage<HomePageListVO>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:30:48 
	*/
	IPage<HomePageListVO> selectHomePages(HomePageQueryParam homePageQueryParam);

}
