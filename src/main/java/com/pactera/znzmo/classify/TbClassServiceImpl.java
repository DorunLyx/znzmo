package com.pactera.znzmo.classify;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pactera.znzmo.classify.dao.TbClassMapper;
import com.pactera.znzmo.vo.classify.ClassifyAddParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryDetailsParam;
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
		tbClass.setPId(classifyAddParam.getPId());
		tbClass.setPName(classifyAddParam.getPName());
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
		tbClass.setPId(classifyUpdateParam.getPId());
		tbClass.setPName(classifyUpdateParam.getPName());
		tbClass.setLevel(classifyUpdateParam.getLevel());
		tbClass.setSort(classifyUpdateParam.getSort());
		tbClass.setUpdateTime(LocalDateTime.now());
		baseMapper.updateById(tbClass);
	}

	@Override
	public void delectClassify(ClassifyQueryDetailsParam classifyQueryDetailsParam) {
		baseMapper.deleteById(classifyQueryDetailsParam.getClassifyId());
	}

}
