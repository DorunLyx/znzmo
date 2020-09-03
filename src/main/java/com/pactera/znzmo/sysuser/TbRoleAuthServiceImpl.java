package com.pactera.znzmo.sysuser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.sysuser.dao.TbRoleAuthMapper;
import com.pactera.znzmo.vo.role.RoleAuthAddParam;
import com.pactera.znzmo.vo.role.RoleAuthUpdateParam;

/**
 * <p>
 * 权限角色关联表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbRoleAuthServiceImpl extends ServiceImpl<TbRoleAuthMapper, TbRoleAuth> implements TbRoleAuthService {

	@Override
	public void addRoleAuth(RoleAuthAddParam roleAuthAddParam) {
		List<TbRoleAuth> tbRoleAuthList = new ArrayList<TbRoleAuth>();
		for (String authId : roleAuthAddParam.getAuthIdList()) {
			TbRoleAuth tbRoleAuth = new TbRoleAuth();
			tbRoleAuth.setRoleId(Long.valueOf(roleAuthAddParam.getRoleId()));
			tbRoleAuth.setRoleId(Long.valueOf(authId));
			tbRoleAuthList.add(tbRoleAuth);
		}
		saveBatch(tbRoleAuthList);
	}

	@Override
	public void updteRoleAuth(RoleAuthUpdateParam roleAuthUpdateParam) {
		QueryWrapper<TbRoleAuth> queryWrapper = new QueryWrapper<TbRoleAuth>();
		queryWrapper.eq(TbRoleAuth.ROLE_ID, roleAuthUpdateParam.getRoleId());
		baseMapper.delete(queryWrapper);
		List<TbRoleAuth> tbRoleAuthList = new ArrayList<TbRoleAuth>();
		for (String authId : roleAuthUpdateParam.getAuthIdList()) {
			TbRoleAuth tbRoleAuth = new TbRoleAuth();
			tbRoleAuth.setRoleId(Long.valueOf(roleAuthUpdateParam.getRoleId()));
			tbRoleAuth.setRoleId(Long.valueOf(authId));
			tbRoleAuthList.add(tbRoleAuth);
		}
		saveBatch(tbRoleAuthList);
	}

}
