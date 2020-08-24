package com.pactera.znzmo.profit;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.profit.dao.TbProfitManageMapper;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.profit.ProfitQueryParam;

/**
 * <p>
 * 收益管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbProfitManageServiceImpl extends ServiceImpl<TbProfitManageMapper, TbProfitManage> implements TbProfitManageService {

	@Override
	public IPage<TbProfitManage> selectProfitManagePages(Page<TbProfitManage> page,
			ProfitQueryParam profitQueryParam) {
		QueryWrapper<TbProfitManage> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbProfitManage.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(profitQueryParam.getKeyWord())) {
			queryWrapper.eq(TbProfitManage.USER_ID, profitQueryParam.getKeyWord())
				.or().eq(TbProfitManage.USER_NAME, profitQueryParam.getKeyWord());
		}
		if(StringUtils.isNotEmpty(profitQueryParam.getStartTime())) {
			queryWrapper.ge(TbProfitManage.UPDATE_TIME, profitQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(profitQueryParam.getEndTime())) {
			queryWrapper.le(TbProfitManage.UPDATE_TIME, profitQueryParam.getEndTime() + " 23:59:59");
		}
		if(StringUtils.isNotEmpty(profitQueryParam.getUserId())) {
			queryWrapper.eq(TbProfitManage.USER_ID, profitQueryParam.getUserId());
		}
		queryWrapper.orderByDesc(TbProfitManage.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

}
