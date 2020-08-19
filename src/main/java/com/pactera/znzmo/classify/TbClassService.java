package com.pactera.znzmo.classify;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.classify.ClassifyAddParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryDetailsParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryParam;
import com.pactera.znzmo.vo.classify.ClassifyUpdateParam;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbClassService extends IService<TbClass> {

	/**
	 * @Title: addClassify 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param classifyAddParam void
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:14:39 
	*/
	void addClassify(ClassifyAddParam classifyAddParam);

	/**
	 * @Title: updteClassify 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param classifyUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:25:56 
	*/
	void updteClassify(ClassifyUpdateParam classifyUpdateParam);

	/**
	 * @Title: delectClassify 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param classifyQueryDetailsParam void
	 * @author liyongxu
	 * @date 2020年8月17日 上午10:32:25 
	*/
	void delectClassify(ClassifyQueryDetailsParam classifyQueryDetailsParam);

	/**
	 * @Title: selectByPid 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param l
	 * @return List<TbClass>
	 * @author liyongxu
	 * @date 2020年8月19日 下午4:15:03 
	*/
	List<TbClass> selectByPid(long pid);

	/**
	 * @Title: selectClassifyList 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param classifyQueryParam
	 * @return List<TbClass>
	 * @author liyongxu
	 * @param page 
	 * @date 2020年8月19日 下午4:38:51 
	*/
	IPage<TbClass> selectClassifyList(Page<TbClass> page, ClassifyQueryParam classifyQueryParam);

}
