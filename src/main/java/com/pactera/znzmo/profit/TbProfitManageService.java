package com.pactera.znzmo.profit;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.profit.ProfitQueryParam;

/**
 * <p>
 * 收益管理表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbProfitManageService extends IService<TbProfitManage> {

	/**
	 * @Title: selectProfitManagePages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param profitQueryParam
	 * @return IPage<TbProfitDetails>
	 * @author liyongxu
	 * @date 2020年8月11日 下午4:51:43 
	*/
	IPage<TbProfitManage> selectProfitManagePages(Page<TbProfitManage> page, ProfitQueryParam profitQueryParam);

}
