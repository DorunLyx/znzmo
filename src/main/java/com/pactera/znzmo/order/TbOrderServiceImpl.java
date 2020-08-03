package com.pactera.znzmo.order;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.order.dao.TbOrderMapper;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {

}
