package com.pactera.znzmo.classify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.classify.ClassifyAddParam;
import com.pactera.znzmo.vo.classify.ClassifyQueryDetailsParam;
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

}
