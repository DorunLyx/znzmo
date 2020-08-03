package com.pactera.znzmo.sysuser;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.sysuser.dao.TbUserMapper;

/**
 * <p>
 * 用户管理表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

}
