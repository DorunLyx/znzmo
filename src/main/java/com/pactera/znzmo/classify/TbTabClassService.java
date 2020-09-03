package com.pactera.znzmo.classify;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.classify.TabClassifyQueryParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-09-03
 */
public interface TbTabClassService extends IService<TbTabClass> {

	/**
	 * @Title: selectTabClassifyList 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param tabClassifyQueryParam
	 * @return List<TbTabClass>
	 * @author liyongxu
	 * @date 2020年9月3日 上午10:57:19 
	*/
	List<TbTabClass> selectTabClassifyList(TabClassifyQueryParam tabClassifyQueryParam);

}
