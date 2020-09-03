package com.pactera.znzmo.sysuser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.role.RoleAddParam;
import com.pactera.znzmo.vo.role.RoleQueryParam;
import com.pactera.znzmo.vo.role.RoleUpdateParam;

/**
 * <p>
 * 角色管理表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbRoleService extends IService<TbRole> {

	/**
	 * @Title: selectRolePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param roleQueryParam
	 * @return IPage<TbRole>
	 * @author liyongxu
	 * @date 2020年9月3日 下午2:50:51 
	*/
	IPage<TbRole> selectRolePages(Page<TbRole> page, RoleQueryParam roleQueryParam);

	/**
	 * @Title: addRole 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param roleAddParam void
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:15:10 
	*/
	void addRole(RoleAddParam roleAddParam);

	/**
	 * @Title: updteRole 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param roleUpdateParam void
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:29:08 
	*/
	void updteRole(RoleUpdateParam roleUpdateParam);

	/**
	 * @Title: deleteRole 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param roleId void
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:32:17 
	*/
	void deleteRole(String roleId);

}
