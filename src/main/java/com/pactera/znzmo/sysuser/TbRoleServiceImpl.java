package com.pactera.znzmo.sysuser;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.sysuser.dao.TbRoleAuthMapper;
import com.pactera.znzmo.sysuser.dao.TbRoleMapper;
import com.pactera.znzmo.sysuser.dao.TbRoleUserMapper;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.role.RoleAddParam;
import com.pactera.znzmo.vo.role.RoleQueryParam;
import com.pactera.znzmo.vo.role.RoleUpdateParam;

/**
 * <p>
 * 角色管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements TbRoleService {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TbRoleAuthMapper tbRoleAuthMapper;
	
	@Autowired
	private TbRoleUserMapper tbRoleUserMapper;
	
	@Override
	public IPage<TbRole> selectRolePages(Page<TbRole> page, RoleQueryParam roleQueryParam) {
		QueryWrapper<TbRole> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbRole.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(roleQueryParam.getName())) {
			queryWrapper.like(TbRole.NAME, roleQueryParam.getName());
		}
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addRole(RoleAddParam roleAddParam) {
		TbRole tbRole = new TbRole();
		tbRole.setName(roleAddParam.getName());
		tbRole.setDescription(roleAddParam.getDescription());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbRole.setCreateId(user.getId());
		tbRole.setCreateName(user.getName());
		tbRole.setCreateTime(LocalDateTime.now());
		tbRole.setUpdateId(user.getId());
		tbRole.setUpdateName(user.getName());
		tbRole.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbRole);
	}

	@Override
	public void updteRole(RoleUpdateParam roleUpdateParam) {
		TbRole tbRole = baseMapper.selectById(roleUpdateParam.getRoleId());
		tbRole.setName(roleUpdateParam.getName());
		tbRole.setDescription(roleUpdateParam.getDescription());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbRole.setUpdateId(user.getId());
		tbRole.setUpdateName(user.getName());
		tbRole.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbRole);
	}

	@Override
	public void deleteRole(String roleId) {
		baseMapper.deleteById(roleId);
		QueryWrapper<TbRoleAuth> roleAuthQueryWrapper = new QueryWrapper<TbRoleAuth>();
		roleAuthQueryWrapper.eq(TbRoleAuth.ROLE_ID, roleId);
		tbRoleAuthMapper.delete(roleAuthQueryWrapper);
		QueryWrapper<TbRoleUser> roleUserQueryWrapper = new QueryWrapper<TbRoleUser>();
		roleUserQueryWrapper.eq(TbRoleAuth.ROLE_ID, roleId);
		tbRoleUserMapper.delete(roleUserQueryWrapper);
	}

}
