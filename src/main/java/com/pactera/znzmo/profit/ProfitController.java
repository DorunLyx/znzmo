package com.pactera.znzmo.profit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.ExcelExportBean;
import com.pactera.znzmo.util.ExcelUtils;
import com.pactera.znzmo.vo.profit.ProfitDetailsListVO;
import com.pactera.znzmo.vo.profit.ProfitManageListVO;
import com.pactera.znzmo.vo.profit.ProfitQueryParam;
import com.pactera.znzmo.vo.profit.WithdrawalExportExcelBean;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ProfitController
 * @Description：收益管理API 
 * @author liyongxu 
 * @date 2020年8月11日 下午3:21:56 
 * @version 1.0.0 
 */
@Api(tags = "收益管理API", value = "收益管理API")
@RestController
@RequestMapping(value = "/profit")
public class ProfitController extends BaseController{
	
	@Autowired
	private TbProfitManageService tbProfitManageService;
	
	@Autowired
	private TbProfitDetailsService tbProfitDetailsService;
	
	public static final Logger logger = LoggerFactory.getLogger(ProfitController.class);

	/**
	 * @Title: getProfitManageList 
	 * @Description: 收益统计列表查询
	 * @param profitQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月11日 下午3:44:57 
	*/
	@ApiOperation(value = "收益统计列表查询", httpMethod = "POST", notes = "收益统计列表查询")
    @RequestMapping(value = "/getProfitManageList", method = {RequestMethod.POST})
    public JsonResp getProfitManageList(
    		@ApiParam(name="profitQueryParam", value="收益统计列表筛选参数", required=false)@RequestBody ProfitQueryParam profitQueryParam) {
		Supplier<IPage<ProfitManageListVO>> businessHandler = () -> {
			try {
				List<ProfitManageListVO> pointsExchangeList = new ArrayList<ProfitManageListVO>();
				Page<TbProfitManage> page = new Page<TbProfitManage>(profitQueryParam.getPageNo(), profitQueryParam.getPageSize());
		        IPage<ProfitManageListVO> pointsExchangeListPage =  new Page<ProfitManageListVO>(profitQueryParam.getPageNo(), profitQueryParam.getPageSize());
		        IPage<TbProfitManage> iPage = tbProfitManageService.selectProfitManagePages(page, profitQueryParam);
				for (TbProfitManage tbProfitManage : iPage.getRecords()) {
					ProfitManageListVO profitManageListVO = new ProfitManageListVO();
					profitManageListVO.setProfitId(tbProfitManage.getId());
					profitManageListVO.setUserId(tbProfitManage.getUserId());
					profitManageListVO.setUserName(tbProfitManage.getUserName());
					profitManageListVO.setMobile(tbProfitManage.getMobile());
					profitManageListVO.setTotalRevenue(tbProfitManage.getTotalRevenue());
					profitManageListVO.setAccountBalance(tbProfitManage.getAccountBalance());
					profitManageListVO.setWithdrawalAmount(tbProfitManage.getWithdrawalAmount());
					pointsExchangeList.add(profitManageListVO);
	    		}
				pointsExchangeListPage.setRecords(pointsExchangeList);
				pointsExchangeListPage.setCurrent(iPage.getCurrent());
				pointsExchangeListPage.setPages(iPage.getPages());
				pointsExchangeListPage.setSize(iPage.getSize());
				pointsExchangeListPage.setTotal(iPage.getTotal());	
		        return pointsExchangeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: getProfitDetailsList 
	 * @Description: 收益详情列表查询
	 * @param profitQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月11日 下午5:13:59 
	*/
	@ApiOperation(value = "收益详情列表查询", httpMethod = "POST", notes = "收益详情列表查询")
    @RequestMapping(value = "/getProfitDetailsList", method = {RequestMethod.POST})
    public JsonResp getProfitDetailsList(
    		@ApiParam(name="pointsQueryParam", value="积分兑换列表筛选参数", required=false)@RequestBody ProfitQueryParam profitQueryParam) {
		Supplier<IPage<ProfitDetailsListVO>> businessHandler = () -> {
			try {
				List<ProfitDetailsListVO> pointsExchangeList = new ArrayList<ProfitDetailsListVO>();
				Page<TbProfitDetails> page = new Page<TbProfitDetails>(profitQueryParam.getPageNo(), profitQueryParam.getPageSize());
		        IPage<ProfitDetailsListVO> pointsExchangeListPage =  new Page<ProfitDetailsListVO>(profitQueryParam.getPageNo(), profitQueryParam.getPageSize());
		        IPage<TbProfitDetails> iPage = tbProfitDetailsService.selectProfitDetailsPages(page, profitQueryParam);
				for (TbProfitDetails tbProfitDetails : iPage.getRecords()) {
					ProfitDetailsListVO profitDetailsListVO = new ProfitDetailsListVO();
					profitDetailsListVO.setProfitDetailsId(tbProfitDetails.getId());
					profitDetailsListVO.setUserId(tbProfitDetails.getUserId());
					profitDetailsListVO.setUserName(tbProfitDetails.getUserName());
					profitDetailsListVO.setMobile(tbProfitDetails.getMobile());
					profitDetailsListVO.setAmount(tbProfitDetails.getAmount());
					profitDetailsListVO.setOperationTime(tbProfitDetails.getOperationTime());
					profitDetailsListVO.setWithdrawalCode(tbProfitDetails.getOrderCode());
					profitDetailsListVO.setRemarks(tbProfitDetails.getRemarks());
					pointsExchangeList.add(profitDetailsListVO);
	    		}
				pointsExchangeListPage.setRecords(pointsExchangeList);
				pointsExchangeListPage.setCurrent(iPage.getCurrent());
				pointsExchangeListPage.setPages(iPage.getPages());
				pointsExchangeListPage.setSize(iPage.getSize());
				pointsExchangeListPage.setTotal(iPage.getTotal());	
		        return pointsExchangeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: exportWithdrawalExcel 
	 * @Description: 提现记录导出
	 * @param request
	 * @param profitQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月11日 下午5:53:40 
	*/
	@ApiOperation(value = "提现记录导出", httpMethod = "POST", notes = "提现记录导出")
    @RequestMapping(value = "/exportWithdrawalExcel", method = {RequestMethod.POST})
    public JsonResp exportWithdrawalExcel(HttpServletRequest request,
    		@ApiParam(name="profitQueryParam", value="提现记录列表筛选参数", required=false)@RequestBody ProfitQueryParam profitQueryParam) {
		Supplier<ExcelExportBean> businessHandler = () -> {
	        try {
	        	 new ExcelExportBean();
	        	List<TbProfitDetails> assetList = tbProfitDetailsService.selectWithdrawalExcel(profitQueryParam);
	        	List<WithdrawalExportExcelBean> assetExportExcel = this.assembleWithdrawalList(assetList);
	        	String fileName = "提现记录" + DateUtils.DateToString(new Date(), DateUtils.LONG_DATE_COMPACT_FORMAT) + ".xls";
	        	ExcelExportBean excelExportBean = ExcelUtils.getExcel(request, assetExportExcel, "提现记录", "提现记录", WithdrawalExportExcelBean.class, fileName);
	        	return excelExportBean;
	        } catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: assembleWithdrawalList 
	 * @Description: 重组提现导出数据
	 * @param assetList
	 * @return List<WithdrawalExportExcelBean>
	 * @author liyongxu
	 * @date 2020年8月11日 下午5:49:52 
	*/
	private List<WithdrawalExportExcelBean> assembleWithdrawalList(List<TbProfitDetails> assetList) {
		List<WithdrawalExportExcelBean> withdrawalExportList = new ArrayList<>();
		for (TbProfitDetails tbProfitDetails : assetList) {
			WithdrawalExportExcelBean withdrawalExportExcelBean = new WithdrawalExportExcelBean();
			withdrawalExportExcelBean.setWithdrawalCode(tbProfitDetails.getOrderCode());
			withdrawalExportExcelBean.setAmount(tbProfitDetails.getAmount());
			withdrawalExportExcelBean.setUserName(tbProfitDetails.getUserName());
			withdrawalExportExcelBean.setMobile(tbProfitDetails.getMobile());
			withdrawalExportExcelBean.setOperationTime(tbProfitDetails.getOperationTime());
			withdrawalExportExcelBean.setRemarks(tbProfitDetails.getRemarks());
			withdrawalExportList.add(withdrawalExportExcelBean);
		}
		return withdrawalExportList;
	}
	
}
