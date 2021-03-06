/**
 * 
 */
package com.pactera.znzmo.sysuser.dao;

import java.util.List;

import com.pactera.znzmo.sysuser.SysUser;

/**
 * @author Administrator
 *
 */
public interface SysUserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(SysUser record);

	int insertSelective(SysUser record);

	SysUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(SysUser record);

	int updateByPrimaryKey(SysUser record);

	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	List<SysUser> selectAll();

	/**
	 * 分页查询用户
	 * 
	 * @return
	 */
	List<SysUser> selectPage();
}
