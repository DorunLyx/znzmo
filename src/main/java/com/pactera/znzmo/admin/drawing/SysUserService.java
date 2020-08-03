/**
 * 
 */
package com.pactera.znzmo.admin.drawing;

import java.util.List;
import java.util.Set;

import com.pactera.znzmo.util.PageRequest;
import com.pactera.znzmo.util.PageResult;

/**
 * @author Administrator
 *
 */
public interface SysUserService {
	/**
	 * 根据用户ID查找用户
	 * 
	 * @param userId
	 * @return
	 */
	SysUser findByUserId(Long userId);

	/**
	 * 查找所有用户
	 * 
	 * @return
	 */
	List<SysUser> findAll();

	/**
	 * 分页查询接口 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
	 * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会 影响服务层以上的分页接口，起到了解耦的作用
	 * 
	 * @param pageRequest 自定义，统一分页查询请求
	 * @return PageResult 自定义，统一分页查询结果
	 */
	PageResult findPage(PageRequest pageRequest);
	
	
    /**
     * 保存用户
     * @param user
     */
    public void save(SysUser user);
    
    /**
     * 删除用户
     * @param id
     */
    public void delete(String id);
    
    /**
     * 查询全部用户
     * @return
     */
    public List<SysUser> findAllDao();
    

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
    SysUser findByUsername(String username);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param userName
	 * @return
	 */
	Set<String> findPermissions(String username);

}
