/**
 * 
 */
package com.pactera.znzmo.msg;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.model.Tb3dModel;
import com.pactera.znzmo.util.DataUtils;
import com.pactera.znzmo.vo.ModelDetailsVO;
import com.pactera.znzmo.vo.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.ModelUpdateParam;
import com.pactera.znzmo.vo.MsgAddParam;
import com.pactera.znzmo.vo.MsgListVO;
import com.pactera.znzmo.vo.MsgQueryDetailsParam;
import com.pactera.znzmo.vo.MsgQueryParam;
import com.pactera.znzmo.vo.UploadInfo;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author heliang
 *
 */
@Api(tags = "系统消息API", value = "系统消息API")
@RestController
@RequestMapping(value = "/SysMsg")
public class MsgController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(MsgController.class);

	@Autowired
	TbSysMsgService msgService;

	@ApiOperation(value = "系统消息新增", httpMethod = "POST", notes = "系统消息新增")
	@RequestMapping(value = "/addSysMsg", method = { RequestMethod.POST })
	public JsonResp addSysMsg(
			@ApiParam(name = "MsgAddParam", value = "系统消息新增参数", required = false) @RequestBody MsgAddParam msgAddParam) {
		Supplier<String> businessHandler = () -> {
			try {
				msgService.addSysMsg(msgAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}

	@ApiOperation(value = "系统消息列表查询", httpMethod = "POST", notes = "系统消息列表查询")
	@RequestMapping(value = "/getSysMsgList", method = { RequestMethod.POST })
	public JsonResp getSysMsgList(
			@ApiParam(name = "msgQueryParam", value = "系统消息列表筛选参数", required = false) @RequestBody MsgQueryParam msgQueryParam) {
		Supplier<IPage<MsgListVO>> businessHandler = () -> {
			try {
				List<MsgListVO> modelList = new ArrayList<MsgListVO>();
				Page<TbSysMsg> page = new Page<TbSysMsg>(msgQueryParam.getPageNo(), msgQueryParam.getPageSize());
				IPage<MsgListVO> modeListPage = new Page<MsgListVO>(msgQueryParam.getPageNo(),
						msgQueryParam.getPageSize());
				IPage<TbSysMsg> iPage = msgService.selectSysMsgPages(page, msgQueryParam);
				for (TbSysMsg tbSysMsg : iPage.getRecords()) {
					MsgListVO msgListVO = new MsgListVO();
					msgListVO.setTitle(tbSysMsg.getTitle());
					msgListVO.setContent(tbSysMsg.getContent());
					msgListVO.setStatus(tbSysMsg.getStatus());
					msgListVO.setCreateAccount(tbSysMsg.getCreateAccount());
					msgListVO.setMsgId(tbSysMsg.getId());
					msgListVO.setCreateFTime(tbSysMsg.getCreateTime());
					modelList.add(msgListVO);
				}
				modeListPage.setRecords(modelList);
				modeListPage.setCurrent(iPage.getCurrent());
				modeListPage.setPages(iPage.getPages());
				modeListPage.setSize(iPage.getSize());
				modeListPage.setTotal(iPage.getTotal());
				return modeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);

	}

	@ApiOperation(value = "系統消息详情", httpMethod = "POST", notes = "系統消息详情")
	@RequestMapping(value = "/getSysMsglInfo", method = { RequestMethod.POST })
	public JsonResp getSysMsgInfo(
			@ApiParam(name = "msgQueryDetailsParam", value = "系統详情参数", required = false) @RequestBody MsgQueryDetailsParam msgQueryDetailsParam) {
		Supplier<TbSysMsg> businessHandler = () -> {
			try {
				QueryWrapper<TbSysMsg> modelQueryWrapper = new QueryWrapper<>();
				modelQueryWrapper.eq(TbSysMsg.IS_VALID, IsValidEnum.YES.getKey()).eq(Tb3dModel.ID,
						msgQueryDetailsParam.getMsgId());
				TbSysMsg tbSysMsg = msgService.getOne(modelQueryWrapper);
				return tbSysMsg;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
	}

	/**
	 * @Title: updateSysMsgStatus
	 * @Description: 变更状态-3d模型
	 * @param modelQueryDetailsParam
	 * @return JsonResult<ModelDetailsVO>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:12:18
	 */
	@ApiOperation(value = "变更状态-系统消息", httpMethod = "POST", notes = "变更状态-系统消息")
	@RequestMapping(value = "/updateSysMsgStatus", method = { RequestMethod.POST })
	public JsonResp updateSysMsglStatus(
			@ApiParam(name = "msgQueryDetailsParam", value = "系统消息详情参数", required = false) @RequestBody MsgQueryDetailsParam msgQueryDetailsParam) {
		Supplier<String> businessHandler = () -> {
			try {
				TbSysMsg tbSysMsg = msgService.getById(msgQueryDetailsParam.getMsgId());
				tbSysMsg.setStatus(msgQueryDetailsParam.getStatus());
				msgService.updateById(tbSysMsg);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
				logger.error(e.getMessage(), e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	

	/**
	 * @Title: updteSysMsg 
	 * @Description: 编辑
	 * @param modelUpdateParam
	 * @return JsonResult
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:54:44 
	*/
	@ApiOperation(value = "系统消息编辑", httpMethod = "POST", notes = "系统消息编辑")
	@RequestMapping(value = "/updteSysMsg", method = {RequestMethod.POST})
	public JsonResp updteSysMsg(
		@ApiParam(name="msgAddParam", value="编辑参数", required=false)@RequestBody MsgAddParam msgAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				msgService.updateSysMsg(msgAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
}
