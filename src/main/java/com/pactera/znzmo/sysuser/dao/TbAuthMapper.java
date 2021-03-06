package com.pactera.znzmo.sysuser.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pactera.znzmo.sysuser.TbAuth;

/**
 * <p>
 * 权限资源管理表 Mapper 接口
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbAuthMapper extends BaseMapper<TbAuth> {
	/**
	 * 查询全部权限
	 * 
	 * @return
	 */
	List<TbAuth> selectAll();
}
