package com.pactera.znzmo.sysuser;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 权限资源管理表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbAuthService extends IService<TbAuth> {
	/**
	 * 根据用户ID查找权限
	 * 
	 * @param userId
	 * @return
	 */
	TbAuth findByAuthId(Long authId);

	/**
	 * 查找所有权限
	 * 
	 * @return
	 */
	List<TbAuth> findAll();
}
