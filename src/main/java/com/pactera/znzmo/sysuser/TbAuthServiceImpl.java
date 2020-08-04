package com.pactera.znzmo.sysuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.sysuser.dao.TbAuthMapper;

/**
 * <p>
 * 权限资源管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbAuthServiceImpl extends ServiceImpl<TbAuthMapper, TbAuth> implements TbAuthService {
	@Resource
    private TbAuthMapper authMapper;
	@Override
	public TbAuth findByAuthId(Long authId) {
		// TODO Auto-generated method stub
		return authMapper.selectById(authId);
	}

	@Override
	public List<TbAuth> findAll() {
		// TODO Auto-generated method stub
		return authMapper.selectAll();
	}

}
