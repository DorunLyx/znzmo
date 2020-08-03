package com.pactera.znzmo.database;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;

/**
 * <p>
 * 资料库表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbDatabaseServiceImpl extends ServiceImpl<TbDatabaseMapper, TbDatabase> implements TbDatabaseService {

}
