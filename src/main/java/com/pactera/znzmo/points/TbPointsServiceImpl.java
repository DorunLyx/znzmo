package com.pactera.znzmo.points;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.points.dao.TbPointsDetailsMapper;
import com.pactera.znzmo.points.dao.TbPointsMapper;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.sysuser.SysUserService;
import com.pactera.znzmo.util.SecurityUtils;
import com.pactera.znzmo.util.StringUtils;
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
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IPage<TbPoints> selectPointsManagePages(Page<TbPoints> page,
			PointsQueryParam pointsQueryParam) {
		QueryWrapper<TbPoints> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(TbPoints.IS_VALID, IsValidEnum.YES.getKey());
		if(StringUtils.isNotEmpty(pointsQueryParam.getUserName())) {
			queryWrapper.like(TbPoints.USER_NAME, pointsQueryParam.getUserName());
		}
		if(StringUtils.isNotEmpty(pointsQueryParam.getStartTime())) {
			queryWrapper.ge(TbPoints.UPDATE_TIME, pointsQueryParam.getStartTime() + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(pointsQueryParam.getEndTime())) {
			queryWrapper.le(TbPoints.UPDATE_TIME, pointsQueryParam.getEndTime() + " 23:59:59");
		}
		queryWrapper.orderByDesc(TbPoints.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public void addPoints(PointsAddParam pointsAddParam ) {
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
			tbPointsNew.setUserId(Long.valueOf(pointsAddParam.getUserId()));
			tbPointsNew.setUserName(pointsAddParam.getUserName());
			tbPointsNew.setTotalPoints(pointsAddParam.getPointsNum());
			tbPointsNew.setCurrentPoints(pointsAddParam.getPointsNum());
			tbPointsNew.setConsumePoints(BigDecimal.ZERO);
			tbPointsNew.setIsValid(IsValidEnum.YES.getKey());
			SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
			tbPointsNew.setCreateId(user.getId());
			tbPointsNew.setCreateName(user.getName());
			tbPointsNew.setCreateTime(LocalDateTime.now());
			tbPointsNew.setUpdateId(user.getId());
			tbPointsNew.setUpdateName(user.getName());
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
		SysUser user = sysUserService.findByUsername(SecurityUtils.getUsername());
		tbPointsDetails.setCreateId(user.getId());
		tbPointsDetails.setCreateName(user.getName());
		tbPointsDetails.setCreateTime(LocalDateTime.now());
		tbPointsDetails.setUpdateId(user.getId());
		tbPointsDetails.setUpdateName(user.getName());
		tbPointsDetails.setUpdateTime(LocalDateTime.now());
		tbPointsDetailsMapper.insert(tbPointsDetails);
	}
}
