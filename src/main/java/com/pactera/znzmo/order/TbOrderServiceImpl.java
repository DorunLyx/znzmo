package com.pactera.znzmo.order;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.model.TbThreedModel;
import com.pactera.znzmo.model.dao.TbThreedModelMapper;
import com.pactera.znzmo.order.dao.TbOrderMapper;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.order.OrderAddParam;
import com.pactera.znzmo.vo.order.OrderDetailsParam;
import com.pactera.znzmo.vo.order.OrderDetailsVO;
import com.pactera.znzmo.vo.order.OrderQueryParam;

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

	@Autowired
	private TbThreedModelMapper tbThreedModelMapper;
	
	@Autowired
	private TbSuModelMapper tbSuModelMapper;

	@Autowired
	private TbDrawingSchemeMapper tbDrawingSchemeMapper;
	
	@Autowired
	private TbHdMappingMapper tbHdMappingMapper;
	
	@Autowired
	private TbDatabaseMapper tbDatabaseMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbOrder> selectOrderPages(Page<TbOrder> page, OrderQueryParam orderQueryParam) {
		QueryWrapper<TbOrder> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbOrder.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(orderQueryParam.getKeyword())) {
			queryWrapper.like(TbOrder.TITLE, orderQueryParam.getKeyword());
		}
		if(StringUtils.isNotEmpty(orderQueryParam.getType().toString())) {
			queryWrapper.eq(TbOrder.TYPE, orderQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(orderQueryParam.getStartTime())) {
			queryWrapper.ge(TbOrder.ORDER_TIME, orderQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(orderQueryParam.getEndTime())) {
			queryWrapper.le(TbOrder.ORDER_TIME, orderQueryParam.getEndTime() + " 23:59:59");
		}
		queryWrapper.orderByDesc(TbOrder.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addOrder(OrderAddParam orderAddParam) {
		TbOrder tbOrder = new TbOrder();
		tbOrder.setOrderCode(NumGenerationUtil.getrandom());
		if(orderAddParam.getType() == 0) {
			TbThreedModel tbThreedModel = tbThreedModelMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbThreedModel.getTitle());
			tbOrder.setPrice(tbThreedModel.getPrice());
		}else if (orderAddParam.getType() == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbSuModel.getTitle());
			tbOrder.setPrice(tbSuModel.getPrice());
		}else if (orderAddParam.getType() == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbDrawing.getTitle());
			tbOrder.setPrice(tbDrawing.getPrice());
		}else if (orderAddParam.getType() == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbHdMapping.getTitle());
			tbOrder.setPrice(tbHdMapping.getPrice());
		}else if (orderAddParam.getType() == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbDatabase.getTitle());
			tbOrder.setPrice(tbDatabase.getPrice());
		}
		tbOrder.setType(orderAddParam.getType());
		tbOrder.setUserId(Long.valueOf(orderAddParam.getUserId()));
		tbOrder.setUserName(orderAddParam.getUserName());
		tbOrder.setOrderTime(orderAddParam.getOrderTime());
		tbOrder.setIsValid(IsValidEnum.YES.getKey());
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbOrder.setCreateId(user.getId());
		tbOrder.setCreateName(user.getName());
		tbOrder.setCreateTime(LocalDateTime.now());
		tbOrder.setUpdateId(user.getId());
		tbOrder.setUpdateName(user.getName());
		tbOrder.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbOrder);
	}

	@Override
	public OrderDetailsVO getOrderInfo(OrderDetailsParam orderDetailsParam) {
		OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
		TbOrder tbOrder = baseMapper.selectById(orderDetailsParam.getOrderId());
		orderDetailsVO.setOrderId(tbOrder.getId().toString());
		orderDetailsVO.setTitle(tbOrder.getTitle());
		orderDetailsVO.setPrice(tbOrder.getPrice());
		orderDetailsVO.setType(tbOrder.getType());
		orderDetailsVO.setUserId(tbOrder.getUserId().toString());
		orderDetailsVO.setUserName(tbOrder.getUserName());
		orderDetailsVO.setOrderTime(DateUtils.localDateTimeToString(tbOrder.getOrderTime(), DateUtils.DATE_FORMAT));
		if(tbOrder.getType() == 0) {
			TbThreedModel tbThreedModel = tbThreedModelMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbThreedModel.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbThreedModel.getPrimaryClassId().toString());
			orderDetailsVO.setPrimaryClassName(tbThreedModel.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbThreedModel.getSecondaryClassId().toString());
			orderDetailsVO.setSecondaryClassName(tbThreedModel.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbThreedModel.getStyleId().toString());
			orderDetailsVO.setStyleName(tbThreedModel.getStyleName());
			orderDetailsVO.setTextureMapping(tbThreedModel.getTextureMapping());
			orderDetailsVO.setLightingEffects(tbThreedModel.getLightingEffects());
			orderDetailsVO.setRemarks(tbThreedModel.getRemarks());
		}else if (tbOrder.getType() == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbSuModel.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbSuModel.getPrimaryClassId().toString());
			orderDetailsVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbSuModel.getSecondaryClassId().toString());
			orderDetailsVO.setSecondaryClassName(tbSuModel.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbSuModel.getStyleId().toString());
			orderDetailsVO.setStyleName(tbSuModel.getStyleName());
			orderDetailsVO.setTextureMapping(tbSuModel.getTextureMapping());
			orderDetailsVO.setRemarks(tbSuModel.getRemarks());
		}else if (tbOrder.getType() == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbDrawing.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbDrawing.getPrimaryClassId().toString());
			orderDetailsVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbDrawing.getSecondaryClassId().toString());
			orderDetailsVO.setSecondaryClassName(tbDrawing.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbDrawing.getStyleId().toString());
			orderDetailsVO.setStyleName(tbDrawing.getStyleName());
			orderDetailsVO.setTextureMapping(tbDrawing.getTextureMapping());
			orderDetailsVO.setRemarks(tbDrawing.getRemarks());
		}else if (tbOrder.getType() == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbHdMapping.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbHdMapping.getPrimaryClassId().toString());
			orderDetailsVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbHdMapping.getSecondaryClassId().toString());
			orderDetailsVO.setSecondaryClassName(tbHdMapping.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbHdMapping.getStyleId().toString());
			orderDetailsVO.setStyleName(tbHdMapping.getStyleName());
			orderDetailsVO.setRemarks(tbHdMapping.getRemarks());
		}else if (tbOrder.getType() == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbDatabase.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbDatabase.getPrimaryClassId().toString());
			orderDetailsVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbDatabase.getSecondaryClassId().toString());
			orderDetailsVO.setSecondaryClassName(tbDatabase.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbDatabase.getStyleId().toString());
			orderDetailsVO.setStyleName(tbDatabase.getStyleName());
			orderDetailsVO.setRemarks(tbDatabase.getRemarks());
		}
		return orderDetailsVO;
	}
	
}
