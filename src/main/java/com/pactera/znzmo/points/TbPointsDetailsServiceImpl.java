package com.pactera.znzmo.points;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.points.dao.TbPointsDetailsMapper;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.points.PointsQueryParam;

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
		if(StringUtils.isNotEmpty(pointsQueryParam.getUserName())) {
			queryWrapper.like(TbPointsDetails.USER_NAME, pointsQueryParam.getUserName());
		}
		if(StringUtils.isNotEmpty(pointsQueryParam.getStartTime())) {
			queryWrapper.ge(TbPointsDetails.EXCHANGE_TIME, pointsQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(pointsQueryParam.getEndTime())) {
			queryWrapper.le(TbPointsDetails.EXCHANGE_TIME, pointsQueryParam.getEndTime() + " 23:59:59");
		}
		if(StringUtils.isNotEmpty(pointsQueryParam.getUserId())) {
			queryWrapper.eq(TbPointsDetails.USER_ID, pointsQueryParam.getUserId());
		}
		queryWrapper.orderByDesc(TbPointsDetails.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}
}
