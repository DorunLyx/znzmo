package com.pactera.znzmo.profit;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.profit.dao.TbProfitManageMapper;
import com.pactera.znzmo.vo.ProfitQueryParam;

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
		if(StringUtil.isNotEmpty(profitQueryParam.getKeyWord())) {
			queryWrapper.ge(TbProfitManage.USER_ID, profitQueryParam.getKeyWord())
				.or().ge(TbProfitManage.USER_NAME, profitQueryParam.getKeyWord());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbProfitManage.UPDATE_TIME, profitQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(profitQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbProfitManage.UPDATE_TIME, profitQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbProfitManage.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

}
