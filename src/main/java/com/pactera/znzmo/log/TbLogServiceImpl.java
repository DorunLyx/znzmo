package com.pactera.znzmo.log;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.log.dao.TbLogMapper;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.LogQueryParam;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbLogServiceImpl extends ServiceImpl<TbLogMapper, TbLog> implements TbLogService {

	@Override
	public IPage<TbLog> selectTbLogPages(Page<TbLog> page, LogQueryParam logQueryParam) {
		QueryWrapper<TbLog> queryWrapper = new QueryWrapper<>();

		if(StringUtils.isNotEmpty(logQueryParam.getTitle().toString())) {
			queryWrapper.like(TbLog.TITLE, logQueryParam.getTitle());
		}
		if(StringUtils.isNotEmpty(logQueryParam.getStartTime().toString())) {
			queryWrapper.eq(TbLog.CREATE_TIME, logQueryParam.getStartTime());
		}
		queryWrapper.orderByDesc(TbLog.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}
	
}
