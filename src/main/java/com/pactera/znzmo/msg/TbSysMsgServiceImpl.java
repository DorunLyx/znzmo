package com.pactera.znzmo.msg;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.msg.dao.TbSysMsgMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.vo.MsgAddParam;
import com.pactera.znzmo.vo.MsgQueryParam;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbSysMsgServiceImpl extends ServiceImpl<TbSysMsgMapper, TbSysMsg> implements TbSysMsgService {
	@Autowired
	private TbSysMsgMapper tbSysMsgMapper;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public void addSysMsg(MsgAddParam msgAddParam) {
		TbSysMsg tbSysMsg = new TbSysMsg();
		tbSysMsg.setContent(msgAddParam.getContent());
		tbSysMsg.setTitle(msgAddParam.getTitle());
		tbSysMsg.setCreateAccount(SecurityUtils.getUsername());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbSysMsg.setCreateId(user.getId());
		tbSysMsg.setCreateName(user.getName());
		tbSysMsg.setCreateTime(LocalDateTime.now());
		tbSysMsgMapper.insert(tbSysMsg);

	}

	@Override
	public IPage<TbSysMsg> selectSysMsgPages(Page<TbSysMsg> page, MsgQueryParam msgQueryParam) {
		QueryWrapper<TbSysMsg> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbSysMsg.IS_VALID, IsValidEnum.YES.getKey());
		if (StringUtil.isNotEmpty(msgQueryParam.getTitle())) {
			queryWrapper.like(TbSysMsg.TITLE, msgQueryParam.getTitle());
		}
		queryWrapper.orderByDesc(TbSysMsg.UPDATE_TIME);
		return baseMapper.selectPage(page, queryWrapper);
	}

	@Override
	public void updateSysMsg(MsgAddParam msgAddParam) {
		TbSysMsg tbSysMsg = new TbSysMsg();
		tbSysMsg.setContent(msgAddParam.getContent());
		tbSysMsg.setTitle(msgAddParam.getTitle());
		tbSysMsg.setCreateAccount(SecurityUtils.getUsername());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbSysMsg.setCreateId(user.getId());
		tbSysMsg.setCreateName(user.getName());
		tbSysMsg.setCreateTime(LocalDateTime.now());
		tbSysMsg.setId(Long.parseLong(msgAddParam.getMsgId()));
		tbSysMsgMapper.updateById(tbSysMsg);
	}

}
