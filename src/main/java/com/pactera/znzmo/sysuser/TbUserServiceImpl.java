package com.pactera.znzmo.sysuser;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.sysuser.dao.TbRoleUserMapper;
import com.pactera.znzmo.sysuser.dao.TbUserMapper;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.user.UserAddParam;
import com.pactera.znzmo.vo.user.UserQueryParam;
import com.pactera.znzmo.vo.user.UserUpdateParam;

/**
 * <p>
 * 用户管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TbRoleUserMapper tbRoleUserMapper;
	
	@Override
	public IPage<TbUser> selectUserPages(Page<TbUser> page, UserQueryParam userQueryParam) {
		QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbUser.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(userQueryParam.getName())) {
			queryWrapper.like(TbUser.USER_NAME, userQueryParam.getName());
		}
		if(StringUtils.isNotEmpty(userQueryParam.getType())) {
			queryWrapper.like(TbUser.TYPE, userQueryParam.getType());
		}
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addUser(UserAddParam userAddParam) {
		TbUser tbUser = new TbUser();
		tbUser.setUserName(userAddParam.getUserName());
		tbUser.setRealName(userAddParam.getRealName());
		tbUser.setSex(userAddParam.getSex());
		tbUser.setBirthday(DateUtils.parseDate(userAddParam.getBirthday() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbUser.setMobile(userAddParam.getMobile());
		tbUser.setEmail(userAddParam.getEmail());
		tbUser.setType(userAddParam.getType());
		tbUser.setPassword(userAddParam.getPassword());
		tbUser.setIsValid(IsValidEnum.YES.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbUser.setCreateId(user.getId());
		tbUser.setCreateName(user.getName());
		tbUser.setCreateTime(LocalDateTime.now());
		tbUser.setUpdateId(user.getId());
		tbUser.setUpdateName(user.getName());
		tbUser.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbUser);
	}

	@Override
	public void updteUser(UserUpdateParam userUpdateParam) {
		TbUser tbUser = baseMapper.selectById(userUpdateParam.getUserId());
		tbUser.setUserName(userUpdateParam.getUserName());
		tbUser.setRealName(userUpdateParam.getRealName());
		tbUser.setSex(userUpdateParam.getSex());
		tbUser.setBirthday(DateUtils.parseDate(userUpdateParam.getBirthday() + " 00:00:00", DateUtils.FORMAT_ONE));
		tbUser.setMobile(userUpdateParam.getMobile());
		tbUser.setEmail(userUpdateParam.getEmail());
		tbUser.setType(userUpdateParam.getType());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbUser.setUpdateId(user.getId());
		tbUser.setUpdateName(user.getName());
		tbUser.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbUser);
	}

	@Override
	public void deleteUser(String userId) {
		TbUser tbUser = baseMapper.selectById(userId);
		tbUser.setIsValid(IsValidEnum.NO.getKey());
		QueryWrapper<TbRoleUser> roleUserQueryWrapper = new QueryWrapper<TbRoleUser>();
		roleUserQueryWrapper.eq(TbRoleUser.USER_ID, userId);
		tbRoleUserMapper.delete(roleUserQueryWrapper);
	}

}
