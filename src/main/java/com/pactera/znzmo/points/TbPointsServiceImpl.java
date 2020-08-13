package com.pactera.znzmo.points;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.points.dao.TbPointsDetailsMapper;
import com.pactera.znzmo.points.dao.TbPointsMapper;
import com.pactera.znzmo.vo.points.PointsAddParam;
import com.pactera.znzmo.vo.points.PointsQueryParam;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbPointsServiceImpl extends ServiceImpl<TbPointsMapper, TbPoints> implements TbPointsService {

	@Autowired
	private TbPointsDetailsMapper tbPointsDetailsMapper;
	
	@Override
	public IPage<TbPoints> selectPointsManagePages(Page<TbPoints> page,
			PointsQueryParam pointsQueryParam) {
		QueryWrapper<TbPoints> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbPoints.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtil.isNotEmpty(pointsQueryParam.getUserName())) {
			queryWrapper.like(TbPoints.USER_NAME, pointsQueryParam.getUserName());
		}
		if(StringUtil.isNotEmpty(pointsQueryParam.getStartTime().toString())) {
			queryWrapper.ge(TbPoints.UPDATE_TIME, pointsQueryParam.getStartTime());
		}
		if(StringUtil.isNotEmpty(pointsQueryParam.getEndTime().toString())) {
			queryWrapper.le(TbPoints.UPDATE_TIME, pointsQueryParam.getEndTime());
		}
		queryWrapper.orderByDesc(TbPoints.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addPoints(PointsAddParam pointsAddParam) {
		QueryWrapper<TbPoints> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbPoints.IS_VALID, IsValidEnum.YES.getKey())
			.eq(TbPoints.USER_ID,pointsAddParam.getUserId());
		TbPoints tbPoints = baseMapper.selectOne(queryWrapper);
		if(tbPoints != null) {
			tbPoints.setTotalPoints(tbPoints.getTotalPoints().add(pointsAddParam.getPointsNum()));
			tbPoints.setCurrentPoints(tbPoints.getCurrentPoints().add(pointsAddParam.getPointsNum()));
			tbPoints.setUpdateTime(LocalDateTime.now());
			baseMapper.updateById(tbPoints);
			addPointsDetails(tbPoints, pointsAddParam);
		}else {
			TbPoints tbPointsNew = new TbPoints();
			tbPointsNew.setUserId(pointsAddParam.getUserId());
			tbPointsNew.setUserName(pointsAddParam.getUserName());
			tbPointsNew.setTotalPoints(pointsAddParam.getPointsNum());
			tbPointsNew.setCurrentPoints(pointsAddParam.getPointsNum());
			tbPointsNew.setConsumePoints(BigDecimal.ZERO);
			tbPointsNew.setIsValid(IsValidEnum.YES.getKey());
//			tbPointsNew.setCreateId(user.getUserId());
//			tbPointsNew.setCreateName(user.getUserName());
			tbPointsNew.setCreateTime(LocalDateTime.now());
//			tbPointsNew.setUpdateId(user.getUserId());
//			tbPointsNew.setUpdateName(user.getUserName());
			tbPointsNew.setUpdateTime(LocalDateTime.now());
			baseMapper.insert(tbPointsNew);
			addPointsDetails(tbPointsNew, pointsAddParam);
		}
	}

	/**
	 * @Title: addPointsDetails 
	 * @Description: 新增积分详情
	 * @param tbPoints
	 * @param pointsAddParam void
	 * @author liyongxu
	 * @date 2020年8月11日 下午3:04:23 
	*/
	private void addPointsDetails(TbPoints tbPoints, PointsAddParam pointsAddParam) {
		TbPointsDetails tbPointsDetails = new TbPointsDetails();
		tbPointsDetails.setTotalPoints(tbPoints.getTotalPoints());
		tbPointsDetails.setCurrentPoints(tbPoints.getCurrentPoints());
		tbPointsDetails.setConsumePoints(tbPoints.getConsumePoints());
		tbPointsDetails.setConsumePoints(BigDecimal.ZERO);
		tbPointsDetails.setIsValid(IsValidEnum.YES.getKey());
//		tbPointsDetails.setCreateId(user.getUserId());
//		tbPointsDetails.setCreateName(user.getUserName());
		tbPointsDetails.setCreateTime(LocalDateTime.now());
//		tbPointsDetails.setUpdateId(user.getUserId());
//		tbPointsDetails.setUpdateName(user.getUserName());
		tbPointsDetails.setUpdateTime(LocalDateTime.now());
		tbPointsDetailsMapper.insert(tbPointsDetails);
	}
}
