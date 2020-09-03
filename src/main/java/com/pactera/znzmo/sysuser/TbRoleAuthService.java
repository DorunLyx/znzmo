package com.pactera.znzmo.sysuser;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.role.RoleAuthAddParam;
import com.pactera.znzmo.vo.role.RoleAuthUpdateParam;

/**
 * <p>
 * 权限角色关联表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbRoleAuthService extends IService<TbRoleAuth> {

	/**
	 * @Title: addRoleAuth 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param roleAuthAddParam void
	 * @author liyongxu
	 * @date 2020年9月3日 下午4:00:02 
	*/
	void addRoleAuth(RoleAuthAddParam roleAuthAddParam);

	/**
	 * @Title: updteRoleAuth 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param roleAuthUpdateParam void
	 * @author liyongxu
	 * @date 2020年9月3日 下午4:07:45 
	*/
	void updteRoleAuth(RoleAuthUpdateParam roleAuthUpdateParam);

}
