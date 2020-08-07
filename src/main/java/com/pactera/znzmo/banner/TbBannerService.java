package com.pactera.znzmo.banner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.BannerAddParam;
import com.pactera.znzmo.vo.BannerQueryParam;
import com.pactera.znzmo.vo.BannerUpdateParam;

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
	 * @Title: selectBannerPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param homePageQueryParam
	 * @return IPage<TbBanner>
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:05:59 
	*/
	IPage<TbBanner> selectBannerPages(Page<TbBanner> page, BannerQueryParam bannerQueryParam);

	/**
	 * @Title: addBanner 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param bannerAddParam void
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:19:37 
	*/
	void addBanner(BannerAddParam bannerAddParam);

	/**
	 * @Title: updteBanner 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param bannerUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:29:40 
	*/
	void updteBanner(BannerUpdateParam bannerUpdateParam);

}
