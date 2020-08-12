package com.pactera.znzmo.sensitivewords;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.msg.TbSysMsg;
import com.pactera.znzmo.sensitivewords.dao.TbSensitiveWordsMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;

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

}
