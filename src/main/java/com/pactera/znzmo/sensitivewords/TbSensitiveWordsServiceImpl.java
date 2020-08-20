package com.pactera.znzmo.sensitivewords;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.sensitivewords.dao.TbSensitiveWordsMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;
import com.pactera.znzmo.vo.sensitivewords.SensiWordQueryParam;

/**
 * <p>
 * 敏感词表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbSensitiveWordsServiceImpl extends ServiceImpl<TbSensitiveWordsMapper, TbSensitiveWords>
		implements TbSensitiveWordsService {
	@Autowired
	private TbSensitiveWordsMapper TbSensitiveWordsMapper;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public void addSensitiveWord(SensiWordAddParam sensiWordAddParam) {
		TbSensitiveWords sensiWord = new TbSensitiveWords();
		sensiWord.setName(sensiWordAddParam.getName());
		sensiWord.setShowContents(sensiWordAddParam.getShowContent());
		sensiWord.setCreateAccount(SecurityUtils.getUsername());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		sensiWord.setCreateId(user.getId());
		sensiWord.setCreateName(user.getName());
		sensiWord.setCreateTime(LocalDateTime.now());
		TbSensitiveWordsMapper.insert(sensiWord);
	}

	@Override
	public IPage<TbSensitiveWords> selectSensiWordPages(Page<TbSensitiveWords> page,
			SensiWordQueryParam sensiWordQueryParam) {
		QueryWrapper<TbSensitiveWords> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbSensitiveWords.IS_VALID, IsValidEnum.YES.getKey());
		if (StringUtils.isNotEmpty(sensiWordQueryParam.getTitle())) {
			queryWrapper.like(TbSensitiveWords.NAME, sensiWordQueryParam.getTitle());
		}
		queryWrapper.orderByDesc(TbSensitiveWords.UPDATE_TIME);
		return baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public void updteSensiWord(SensiWordAddParam sensiWordAddParam) {
		TbSensitiveWords sensiWord = new TbSensitiveWords();
		sensiWord.setId(Long.parseLong(sensiWordAddParam.getSenId()));
		sensiWord.setName(sensiWordAddParam.getName());
		sensiWord.setShowContents(sensiWordAddParam.getShowContent());
		sensiWord.setUpdateAccount(SecurityUtils.getUsername());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		sensiWord.setUpdateId(user.getId());
		sensiWord.setUpdateName(user.getName());
		sensiWord.setUpdateTime(LocalDateTime.now());
		TbSensitiveWordsMapper.insert(sensiWord);
	}

}
