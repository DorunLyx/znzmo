/**
 * 
 */
package com.pactera.znzmo.admin.drawing;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pactera.znzmo.sysuser.dao.SysUserDao;
import com.pactera.znzmo.sysuser.dao.SysUserMapper;
import com.pactera.znzmo.util.PageRequest;
import com.pactera.znzmo.util.PageResult;
import com.pactera.znzmo.util.PageUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public SysUser findByUserId(Long userId) {
		return sysUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<SysUser> findAll() {
		return sysUserMapper.selectAll();
	}

	/**
	 * 调用分页插件完成分页
	 * 
	 * @param pageQuery
	 * @return
	 */
	private PageInfo<SysUser> getPageInfo(PageRequest pageRequest) {
		int pageNum = pageRequest.getPageNum();
		int pageSize = pageRequest.getPageSize();
		PageHelper.startPage(pageNum, pageSize);
		List<SysUser> sysMenus = sysUserMapper.selectPage();
		return new PageInfo<SysUser>(sysMenus);
	}

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
	}

	@Override
	public void save(SysUser user) {
		sysUserDao.save(user);
	}

	@Override
	public void delete(String id) {
		sysUserDao.delete(id);
	}

	@Override
	public List<SysUser> findAllDao() {
		return sysUserDao.findAll();
	}

	@Override
	public SysUser findByUsername(String username) {
		SysUser user = new SysUser();
		user.setId(1L);
		user.setName(username);
		String password = new BCryptPasswordEncoder().encode("123");
		user.setPassword(password);
		return user;
	}

	@Override
	public Set<String> findPermissions(String username) {
		Set<String> permissions = new HashSet<>();
		permissions.add("sys:user:view");
		permissions.add("sys:user:add");
		permissions.add("sys:user:edit");
		permissions.add("sys:user:delete");
		return permissions;
	}
}
