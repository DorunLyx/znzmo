package com.pactera.znzmo.points;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.points.dao.TbPointsDetailsMapper;
import com.pactera.znzmo.vo.PointsQueryParam;

/**
 * <p>
 * 积分明细表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbPointsDetailsServiceImpl extends ServiceImpl<TbPointsDetailsMapper, TbPointsDetails> implements TbPointsDetailsService {
	
	@Override
	public IPage<TbPointsDetails> selectPointsExchangePages(Page<TbPointsDetails> page,
			PointsQueryParam pointsQueryParam) {
		QueryWrapper<TbPointsDetails> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbPointsDetails.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(pointsQueryParam.getUserName())) {
			queryWrapper.like(TbPointsDetails.UPDATE_NAME, pointsQueryParam.getUserName());
		}
		if(StringUtil.isNotEmpty(pointsQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbPointsDetails.EXCHANGE_TIME, pointsQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(pointsQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbPointsDetails.EXCHANGE_TIME, pointsQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbPointsDetails.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}
}
