package com.pactera.znzmo.sysuser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.user.UserAddParam;
import com.pactera.znzmo.vo.user.UserQueryParam;
import com.pactera.znzmo.vo.user.UserUpdateParam;

/**
 * <p>
 * 用户管理表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbUserService extends IService<TbUser> {

	/**
	 * @Title: selectUserPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param userQueryParam
	 * @return IPage<TbUser>
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:09:14 
	*/
	IPage<TbUser> selectUserPages(Page<TbUser> page, UserQueryParam userQueryParam);

	/**
	 * @Title: addUser 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param userAddParam void
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:25:23 
	*/
	void addUser(UserAddParam userAddParam);

	/**
	 * @Title: updteUser 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param userUpdateParam void
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:48:35 
	*/
	void updteUser(UserUpdateParam userUpdateParam);

	/**
	 * @Title: deleteUser 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param userId void
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:53:03 
	*/
	void deleteUser(String userId);

}
