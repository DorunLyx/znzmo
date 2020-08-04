/**
 * 
 */
package com.pactera.znzmo.sysuser.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pactera.znzmo.sysuser.SysUser;

/**
 * @author Administrator
 *
 */
@Repository
public class SysUserDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void save(SysUser user) {
		String sql = "insert into sys_user(id, name, nick_name, password, email) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, user.getId(), user.getName(), user.getNickName(), user.getPassword(), user.getEmail());
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void delete(String id) {
		String sql = "delete from sys_user where id=?";
		jdbcTemplate.update(sql, id);
	}

	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	public List<SysUser> findAll() {
		String sql = "select * from sys_user";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysUser.class));
	}
	
	public Set<String> findPermissions(String username){
		String sql="SELECT CONCAT( tb_user.user_name, \":\", tb_role.`name`, \":\", tb_auth.`name` ) " + 
				"FROM tb_role_user " + 
				"	INNER JOIN tb_user ON tb_role_user.user_id = tb_user.id " + 
				"	INNER JOIN tb_role ON tb_role_user.role_id = tb_role.id " + 
				"	INNER JOIN tb_role_auth ON tb_role.id = tb_role_auth.role_id " + 
				"	INNER JOIN tb_auth ON tb_role_auth.auth_id = tb_auth.id"+
				"   where tb_user.username ='"+username+"'";
		return (Set<String>) jdbcTemplate.query(sql, new BeanPropertyRowMapper(String.class));
		
	}
}
