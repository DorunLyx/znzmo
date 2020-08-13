package com.pactera.znzmo.examineverify;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.examine.ExamineListVO;
import com.pactera.znzmo.vo.examine.ExamineQueryParam;
import com.pactera.znzmo.vo.examine.ExamineStatusParam;

/**
 * <p>
 * 审批表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbExamineVerifyService extends IService<TbExamineVerify> {

	/**
	 * @Title: selectExaminePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param examineQueryParam
	 * @return IPage<ExamineListVO>
	 * @author liyongxu
	 * @date 2020年8月10日 上午10:57:20 
	*/
	IPage<ExamineListVO> selectExaminePages(Page<TbExamineVerify> page, ExamineQueryParam examineQueryParam);

	/**
	 * @Title: updateExamineStatus 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param examineStatusParam void
	 * @author liyongxu
	 * @date 2020年8月10日 上午11:43:42 
	*/
	void updateExamineStatus(ExamineStatusParam examineStatusParam);

}
