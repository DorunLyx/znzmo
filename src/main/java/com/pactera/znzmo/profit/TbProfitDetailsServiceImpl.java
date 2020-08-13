package com.pactera.znzmo.profit;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.profit.dao.TbProfitDetailsMapper;
import com.pactera.znzmo.vo.profit.ProfitQueryParam;

/**
 * <p>
 * 收益明细表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbProfitDetailsServiceImpl extends ServiceImpl<TbProfitDetailsMapper, TbProfitDetails> implements TbProfitDetailsService {

	@Override
	public IPage<TbProfitDetails> selectProfitDetailsPages(Page<TbProfitDetails> page,
			ProfitQueryParam profitQueryParam) {
		QueryWrapper<TbProfitDetails> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbProfitDetails.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(profitQueryParam.getType().toString())) {
			queryWrapper.ge(TbProfitDetails.PROFIT_TYPE, profitQueryParam.getType());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getKeyWord())) {
			queryWrapper.ge(TbProfitDetails.USER_ID, profitQueryParam.getKeyWord())
				.or().ge(TbProfitDetails.USER_NAME, profitQueryParam.getKeyWord());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbProfitDetails.UPDATE_TIME, profitQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbProfitDetails.UPDATE_TIME, profitQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbProfitDetails.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<TbProfitDetails> selectWithdrawalExcel(ProfitQueryParam profitQueryParam) {
		QueryWrapper<TbProfitDetails> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbProfitDetails.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(profitQueryParam.getType().toString())) {
			queryWrapper.ge(TbProfitDetails.PROFIT_TYPE, profitQueryParam.getType());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getKeyWord())) {
			queryWrapper.ge(TbProfitDetails.USER_ID, profitQueryParam.getKeyWord())
				.or().ge(TbProfitDetails.USER_NAME, profitQueryParam.getKeyWord());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbProfitDetails.UPDATE_TIME, profitQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbProfitDetails.UPDATE_TIME, profitQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbProfitDetails.UPDATE_TIME);
		return baseMapper.selectList(queryWrapper);
	}

}
