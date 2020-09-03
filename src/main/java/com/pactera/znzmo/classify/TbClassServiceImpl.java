package com.pactera.znzmo.classify;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.classify.dao.TbClassMapper;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.classify.ClassListQueryParam;
import com.pactera.znzmo.vo.classify.ClassifyAddParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryDetailsParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryParam;
import com.pactera.znzmo.vo.classify.ClassifyUpdateParam;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Service
public class TbClassServiceImpl extends ServiceImpl<TbClassMapper, TbClass> implements TbClassService {

	@Override
	public void addClassify(ClassifyAddParam classifyAddParam) {
		TbClass tbClass = new TbClass();
		tbClass.setName(classifyAddParam.getClassifyName());
		tbClass.setPId(Long.valueOf(classifyAddParam.getPId()));
		TbClass tbClassPInfo = baseMapper.selectById(classifyAddParam.getPId());
		if(tbClassPInfo != null) {
			tbClass.setPName(tbClassPInfo.getName());
			tbClass.setType(tbClassPInfo.getType());
		}
		tbClass.setLevel(classifyAddParam.getLevel());
		tbClass.setSort(classifyAddParam.getSort());
		tbClass.setCreateTime(LocalDateTime.now());
		tbClass.setUpdateTime(LocalDateTime.now());
		baseMapper.insert(tbClass);
	}

	@Override
	public void updteClassify(ClassifyUpdateParam classifyUpdateParam) {
		TbClass tbClass = baseMapper.selectById(classifyUpdateParam.getClassifyId());
		tbClass.setName(classifyUpdateParam.getClassifyName());
		tbClass.setPId(Long.valueOf(classifyUpdateParam.getPId()));
		TbClass tbClassPInfo = baseMapper.selectById(classifyUpdateParam.getPId());
		if(tbClassPInfo != null) {
			tbClass.setPName(tbClassPInfo.getName());
			tbClass.setType(tbClassPInfo.getType());
		}
		tbClass.setLevel(classifyUpdateParam.getLevel());
		tbClass.setSort(classifyUpdateParam.getSort());
		tbClass.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbClass);
	}

	@Override
	public void delectClassify(ClassifyQueryDetailsParam classifyQueryDetailsParam) {
		baseMapper.deleteById(classifyQueryDetailsParam.getClassifyId());
	}

	@Override
	public List<TbClass> selectByPid(long pid) {
		QueryWrapper<TbClass> queryWrapper = new QueryWrapper<TbClass>();
		queryWrapper.eq(TbClass.P_ID, pid);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public IPage<TbClass> selectClassifyPage(Page<TbClass> page, ClassifyQueryParam classifyQueryParam) {
		QueryWrapper<TbClass> queryWrapper = new QueryWrapper<TbClass>();
		if(StringUtils.isNotEmpty(classifyQueryParam.getKeyWord())) {
			queryWrapper.like(TbClass.NAME, classifyQueryParam.getKeyWord());
		}
		if(StringUtils.isNotEmpty(classifyQueryParam.getType())) {
			queryWrapper.eq(TbClass.TYPE, classifyQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(classifyQueryParam.getLevel())) {
			queryWrapper.eq(TbClass.LEVEL, classifyQueryParam.getLevel());
		}
		queryWrapper.orderByDesc(TbClass.UPDATE_TIME);
		return baseMapper.selectPage(page,queryWrapper);
	}

	@Override
	public List<TbClass> selectClassifyList(ClassListQueryParam classListQueryParam) {
		QueryWrapper<TbClass> queryWrapper = new QueryWrapper<TbClass>();
		if(StringUtils.isNotEmpty(classListQueryParam.getId())) {
			queryWrapper.like(TbClass.ID, classListQueryParam.getId());
		}
		if(StringUtils.isNotEmpty(classListQueryParam.getPId())) {
			queryWrapper.like(TbClass.P_ID, classListQueryParam.getPId());
		}
		if(StringUtils.isNotEmpty(classListQueryParam.getType())) {
			queryWrapper.eq(TbClass.TYPE, classListQueryParam.getType());
		}
		if(StringUtils.isNotEmpty(classListQueryParam.getLevel())) {
			queryWrapper.eq(TbClass.LEVEL, classListQueryParam.getLevel());
		}
		queryWrapper.orderByDesc(TbClass.UPDATE_TIME);
		return baseMapper.selectList(queryWrapper);
	}

}
