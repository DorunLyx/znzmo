package com.pactera.znzmo.profit;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.profit.ProfitQueryParam;

/**
 * <p>
 * 收益明细表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbProfitDetailsService extends IService<TbProfitDetails> {

	/**
	 * @Title: selectProfitDetailsPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param profitQueryParam
	 * @return IPage<TbProfitDetails>
	 * @author liyongxu
	 * @date 2020年8月11日 下午5:14:16 
	*/
	IPage<TbProfitDetails> selectProfitDetailsPages(Page<TbProfitDetails> page, ProfitQueryParam profitQueryParam);

	/**
	 * @Title: selectWithdrawalExcel 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param profitQueryParam
	 * @return List<TbProfitDetails>
	 * @author liyongxu
	 * @date 2020年8月11日 下午5:26:13 
	*/
	List<TbProfitDetails> selectWithdrawalExcel(ProfitQueryParam profitQueryParam);

}
