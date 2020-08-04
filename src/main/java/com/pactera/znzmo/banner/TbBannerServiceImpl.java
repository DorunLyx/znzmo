package com.pactera.znzmo.banner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.banner.dao.TbBannerMapper;
import com.pactera.znzmo.vo.HomePageListVO;
import com.pactera.znzmo.vo.HomePageQueryParam;

/**
 * <p>
 * 轮播管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbBannerServiceImpl extends ServiceImpl<TbBannerMapper, TbBanner> implements TbBannerService {

	@Override
	@Transactional(readOnly=true)
	public IPage<HomePageListVO> selectHomePages(HomePageQueryParam homePageQueryParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
