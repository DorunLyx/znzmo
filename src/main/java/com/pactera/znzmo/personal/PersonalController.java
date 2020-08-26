/**
 * 
 */
package com.pactera.znzmo.personal;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.sysuser.TbUserAssets;
import com.pactera.znzmo.sysuser.TbUserAssetsService;
import com.pactera.znzmo.vo.personal.DownloadRecordsAddParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsQueryParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsVO;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：PersonalController
 * @Description：个人中心API
 * @author liyongxu 
 * @date 2020年8月21日 上午11:30:39 
 * @version 1.0.0 
 */
@Api(tags = "个人中心API", value = "个人中心API")
@RestController
@RequestMapping(value = "/personal")
public class PersonalController extends BaseController{
	
	@Autowired
	private TbUserAssetsService tbUserAssetsService;
	
	@Autowired
	private TbCollectionService tbCollectionService;
	
	@Autowired
	private TbDownloadRecordsService tbDownloadRecordsService;
	
	public static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

	/**
	 * @Title: getUserAssets 
	 * @Description: 我的资产查询
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月21日 下午2:02:37 
	*/
	@ApiOperation(value = "我的资产查询", httpMethod = "POST", notes = "我的资产查询")
    @RequestMapping(value = "/getUserAssets", method = {RequestMethod.POST})
    public JsonResp getUserAssets(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam Long userId) {
		Supplier<TbUserAssets> businessHandler = () ->{
			try {
				QueryWrapper<TbUserAssets> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbUserAssets.USER_ID, userId);
				TbUserAssets tbUserAssets = tbUserAssetsService.getOne(queryWrapper);
				return tbUserAssets;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getCollection 
	 * @Description: 我的收藏
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月25日 下午2:05:30 
	*/
	@ApiOperation(value = "我的收藏", httpMethod = "POST", notes = "我的收藏")
    @RequestMapping(value = "/getCollection", method = {RequestMethod.POST})
    public JsonResp getCollection(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam Long userId) {
		Supplier<List<TbCollection>> businessHandler = () ->{
			try {
				QueryWrapper<TbCollection> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbCollection.USER_ID, userId);
				List<TbCollection> tbCollectionList = tbCollectionService.list(queryWrapper);
				return tbCollectionList;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getDownloadRecords 
	 * @Description: 我的下载
	 * @param downloadRecordsQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月26日 上午10:59:05 
	*/
	@ApiOperation(value = "我的下载", httpMethod = "POST", notes = "我的下载")
    @RequestMapping(value = "/getDownloadRecords", method = {RequestMethod.POST})
    public JsonResp getDownloadRecords(
    		@ApiParam(name="downloadRecordsQueryParam", value="我的下载列表筛选参数", required=false)@RequestBody DownloadRecordsQueryParam downloadRecordsQueryParam) {
		Supplier<IPage<DownloadRecordsVO>> businessHandler = () ->{
			try {
				Page<TbDownloadRecords> page = new Page<TbDownloadRecords>(downloadRecordsQueryParam.getPageNo(), downloadRecordsQueryParam.getPageSize());
				IPage<DownloadRecordsVO> iPage = tbDownloadRecordsService.selectDownloadRecordsPages(page, downloadRecordsQueryParam);
				return iPage;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addDownloadRecords 
	 * @Description: 下载记录新增
	 * @param downloadRecordsAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月26日 上午11:54:00 
	*/
	@ApiOperation(value = "下载记录新增", httpMethod = "POST", notes = "下载记录新增")
    @RequestMapping(value = "/addDownloadRecords", method = {RequestMethod.POST})
	public JsonResp addDownloadRecords(
			@ApiParam(name="downloadRecordsAddParam", value="3d模型新增参数", required=false)@RequestBody DownloadRecordsAddParam downloadRecordsAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbDownloadRecordsService.addDownloadRecords(downloadRecordsAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
}
