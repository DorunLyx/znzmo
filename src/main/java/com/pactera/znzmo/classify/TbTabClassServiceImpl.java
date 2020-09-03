package com.pactera.znzmo.classify;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.classify.dao.TbTabClassMapper;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.classify.TabClassifyQueryParam;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-09-03
 */
@Service
public class TbTabClassServiceImpl extends ServiceImpl<TbTabClassMapper, TbTabClass> implements TbTabClassService {

	@Override
	public List<TbTabClass> selectTabClassifyList(TabClassifyQueryParam tabClassifyQueryParam) {
		QueryWrapper<TbTabClass> queryWrapper = new QueryWrapper<TbTabClass>();
		if(StringUtils.isNotEmpty(tabClassifyQueryParam.getName())) {
			queryWrapper.eq(TbTabClass.NAME, tabClassifyQueryParam.getName());
		}
		return baseMapper.selectList(queryWrapper);
	}

}
