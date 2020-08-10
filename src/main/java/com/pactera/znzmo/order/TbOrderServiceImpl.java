package com.pactera.znzmo.order;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.dao.TbDatabaseMapper;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.dao.TbDrawingSchemeMapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.dao.TbHdMappingMapper;
import com.pactera.znzmo.model.Tb3dModel;
import com.pactera.znzmo.model.dao.Tb3dModelMapper;
import com.pactera.znzmo.order.dao.TbOrderMapper;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.dao.TbSuModelMapper;
import com.pactera.znzmo.util.NumGenerationUtil;
import com.pactera.znzmo.vo.OrderAddParam;
import com.pactera.znzmo.vo.OrderDetailsParam;
import com.pactera.znzmo.vo.OrderDetailsVO;
import com.pactera.znzmo.vo.OrderQueryParam;

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
	private Tb3dModelMapper tb3dModelMapper;
	
	@Autowired
	private TbSuModelMapper tbSuModelMapper;
	
	@Autowired
	private TbDrawingSchemeMapper tbDrawingSchemeMapper;
	
	@Autowired
	private TbHdMappingMapper tbHdMappingMapper;
	
	@Autowired
	private TbDatabaseMapper tbDatabaseMapper;
	
	@Override
	public IPage<TbOrder> selectOrderPages(Page<TbOrder> page, OrderQueryParam orderQueryParam) {
		QueryWrapper<TbOrder> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbOrder.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(orderQueryParam.getKeyword())) {
			queryWrapper.like(TbOrder.TITLE, orderQueryParam.getKeyword());
		}
		if(StringUtil.isNotEmpty(orderQueryParam.getType().toString())) {
			queryWrapper.eq(TbOrder.TYPE, orderQueryParam.getType());
		}
		if(StringUtil.isNotEmpty(orderQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbOrder.ORDER_TIME, orderQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(orderQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbOrder.ORDER_TIME, orderQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbOrder.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addOrder(OrderAddParam orderAddParam) {
		TbOrder tbOrder = new TbOrder();
		tbOrder.setOrderCode(NumGenerationUtil.getrandom());
		if(orderAddParam.getType() == 0) {
			Tb3dModel tb3dModel = tb3dModelMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tb3dModel.getTitle());
			tbOrder.setPrice(tb3dModel.getModelPrice());
		}else if (orderAddParam.getType() == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbSuModel.getTitle());
			tbOrder.setPrice(tbSuModel.getModelPrice());
		}else if (orderAddParam.getType() == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbDrawing.getTitle());
			tbOrder.setPrice(tbDrawing.getModelPrice());
		}else if (orderAddParam.getType() == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbHdMapping.getTitle());
			tbOrder.setPrice(tbHdMapping.getMappingPrice());
		}else if (orderAddParam.getType() == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(orderAddParam.getReId());
			tbOrder.setTitle(tbDatabase.getTitle());
			tbOrder.setPrice(tbDatabase.getMappingPrice());
		}
		tbOrder.setType(orderAddParam.getType());
		tbOrder.setUserId(orderAddParam.getUserId());
		tbOrder.setUserName(orderAddParam.getUserName());
		tbOrder.setOrderTime(orderAddParam.getOrderTime());
		tbOrder.setIsValid(IsValidEnum.YES.getKey());
//		tbOrder.setCreateId(user.getUserId());
//		tbOrder.setCreateName(user.getUserName());
		tbOrder.setCreateTime(LocalDateTime.now());
//		tbOrder.setUpdateId(user.getUserId());
//		tbOrder.setUpdateName(user.getUserName());
		tbOrder.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbOrder);
	}

	@Override
	public OrderDetailsVO getOrderInfo(OrderDetailsParam orderDetailsParam) {
		OrderDetailsVO orderDetailsVO = new OrderDetailsVO();
		TbOrder tbOrder = baseMapper.selectById(orderDetailsParam.getOrderId());
		orderDetailsVO.setOrderId(tbOrder.getId());
		orderDetailsVO.setTitle(tbOrder.getTitle());
		orderDetailsVO.setPrice(tbOrder.getPrice());
		orderDetailsVO.setType(tbOrder.getType());
		orderDetailsVO.setUserId(tbOrder.getUserId());
		orderDetailsVO.setUserName(tbOrder.getUserName());
		orderDetailsVO.setOrderTime(tbOrder.getOrderTime());
		if(tbOrder.getType() == 0) {
			Tb3dModel tb3dModel = tb3dModelMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tb3dModel.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tb3dModel.getPrimaryClassId());
			orderDetailsVO.setPrimaryClassName(tb3dModel.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tb3dModel.getSecondaryClassId());
			orderDetailsVO.setSecondaryClassName(tb3dModel.getSecondaryClassName());
			orderDetailsVO.setStyleId(tb3dModel.getStyleId());
			orderDetailsVO.setStyleName(tb3dModel.getStyleName());
			orderDetailsVO.setTextureMapping(tb3dModel.getTextureMapping());
			orderDetailsVO.setLightingEffects(tb3dModel.getLightingEffects());
			orderDetailsVO.setRemarks(tb3dModel.getRemarks());
		}else if (tbOrder.getType() == 1) {
			TbSuModel tbSuModel = tbSuModelMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbSuModel.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbSuModel.getPrimaryClassId());
			orderDetailsVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbSuModel.getSecondaryClassId());
			orderDetailsVO.setSecondaryClassName(tbSuModel.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbSuModel.getStyleId());
			orderDetailsVO.setStyleName(tbSuModel.getStyleName());
			orderDetailsVO.setTextureMapping(tbSuModel.getTextureMapping());
			orderDetailsVO.setRemarks(tbSuModel.getRemarks());
		}else if (tbOrder.getType() == 2) {
			TbDrawingScheme tbDrawing = tbDrawingSchemeMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbDrawing.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbDrawing.getPrimaryClassId());
			orderDetailsVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbDrawing.getSecondaryClassId());
			orderDetailsVO.setSecondaryClassName(tbDrawing.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbDrawing.getStyleId());
			orderDetailsVO.setStyleName(tbDrawing.getStyleName());
			orderDetailsVO.setTextureMapping(tbDrawing.getTextureMapping());
			orderDetailsVO.setRemarks(tbDrawing.getRemarks());
		}else if (tbOrder.getType() == 3) {
			TbHdMapping tbHdMapping = tbHdMappingMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbHdMapping.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbHdMapping.getPrimaryClassId());
			orderDetailsVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbHdMapping.getSecondaryClassId());
			orderDetailsVO.setSecondaryClassName(tbHdMapping.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbHdMapping.getStyleId());
			orderDetailsVO.setStyleName(tbHdMapping.getStyleName());
			orderDetailsVO.setRemarks(tbHdMapping.getRemarks());
		}else if (tbOrder.getType() == 4) {
			TbDatabase tbDatabase = tbDatabaseMapper.selectById(tbOrder.getReId());
			orderDetailsVO.setMainGraph(tbDatabase.getMainGraph());
			orderDetailsVO.setPrimaryClassId(tbDatabase.getPrimaryClassId());
			orderDetailsVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
			orderDetailsVO.setSecondaryClassId(tbDatabase.getSecondaryClassId());
			orderDetailsVO.setSecondaryClassName(tbDatabase.getSecondaryClassName());
			orderDetailsVO.setStyleId(tbDatabase.getStyleId());
			orderDetailsVO.setStyleName(tbDatabase.getStyleName());
			orderDetailsVO.setRemarks(tbDatabase.getRemarks());
		}
		return orderDetailsVO;
	}
	
}
