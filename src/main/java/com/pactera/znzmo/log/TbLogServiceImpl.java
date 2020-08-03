package com.pactera.znzmo.log;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.log.dao.TbLogMapper;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbLogServiceImpl extends ServiceImpl<TbLogMapper, TbLog> implements TbLogService {

}
