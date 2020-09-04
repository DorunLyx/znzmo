/**
 * 
 */
package com.pactera.znzmo.sysuser;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.vo.HttpResult;
import com.pactera.znzmo.vo.user.UserAddParam;
import com.pactera.znzmo.vo.user.UserQueryParam;
import com.pactera.znzmo.vo.user.UserUpdateParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Administrator
 *
 */
@Api(value = "用户接口")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private TbUserService tbUserService;
	
	@GetMapping(value = "/findByUserId")
	public Object findByUserId(@RequestParam Long userId) {
		return sysUserService.findByUserId(userId);
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value = "/findAll")
	public Object findAll() {
		return sysUserService.findAll();
	}

//	@PostMapping(value = "/findPage")
//	public Object findPage(@RequestBody PageRequest pageQuery) {
//		return sysUserService.findPage(pageQuery);
//	}

	@PostMapping(value = "/save")
	public Object save(@RequestBody SysUser user) {
		sysUserService.save(user);
		return 1;
	}

	@PreAuthorize("hasAuthority('sys:user:delete')")
	@GetMapping(value = "/delete")
	public Object delete(@RequestParam("id") String id) {
		sysUserService.delete(id);
		return 1;
	}

	@GetMapping(value = "/findAllDao")
	public Object findAllDao() {
		return sysUserService.findAllDao();
	}

	@PreAuthorize("hasAuthority('sys:user:edit')")
	@GetMapping(value = "/edit")
	public HttpResult edit() {
		return HttpResult.ok("the edit service is called success.");
	}

	/**
	 * @Title: getUserList 
	 * @Description: 用户列表查询
	 * @param userQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:07:33 
	*/
	@ApiOperation(value = "用户列表查询", httpMethod = "POST", notes = "用户列表查询")
    @RequestMapping(value = "/getUserList", method = {RequestMethod.POST})
    public JsonResp getUserList(
    		@ApiParam(name="userQueryParam", value="用户列表筛选参数", required=false)@RequestBody UserQueryParam userQueryParam) {
		Supplier<IPage<TbUser>> businessHandler = () -> {
			try {
				Page<TbUser> page = new Page<TbUser>(userQueryParam.getPageNo(), userQueryParam.getPageSize());
		        IPage<TbUser> iPage = tbUserService.selectUserPages(page, userQueryParam);
		        return iPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addUser 
	 * @Description: 用户新增
	 * @param userAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:19:06 
	*/
	@ApiOperation(value = "用户新增", httpMethod = "POST", notes = "用户新增")
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST})
	public JsonResp addUser(
			@ApiParam(name="userAddParam", value="用户新增参数", required=false)@RequestBody UserAddParam userAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbUserService.addUser(userAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getUserInfo 
	 * @Description: 用户详情
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:46:27 
	*/
	@ApiOperation(value = "用户详情", httpMethod = "POST", notes = "用户详情")
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST})
    public JsonResp getUserInfo(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam String userId) {
		Supplier<TbUser> businessHandler = () ->{
			try {
				return tbUserService.getById(userId);
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteUser 
	 * @Description: 用户编辑
	 * @param roleUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:46:54 
	*/
	@ApiOperation(value = "用户编辑", httpMethod = "POST", notes = "用户编辑")
	@RequestMapping(value = "/updteUser", method = {RequestMethod.POST})
	public JsonResp updteUser(
			@ApiParam(name="userUpdateParam", value="用户编辑参数", required=false)@RequestBody UserUpdateParam userUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbUserService.updteUser(userUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: deleteUser 
	 * @Description: 删除用户
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午10:52:53 
	*/
	@ApiOperation(value = "删除用户", httpMethod = "POST", notes = "删除用户")
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.POST})
    public JsonResp deleteUser(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam String userId) {
		Supplier<String> businessHandler = () ->{
			try {
				tbUserService.deleteUser(userId);
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updatePassword 
	 * @Description: 重置密码
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月4日 上午11:01:50 
	*/
	@ApiOperation(value = "重置密码", httpMethod = "POST", notes = "重置密码")
    @RequestMapping(value = "/updatePassword", method = {RequestMethod.POST})
    public JsonResp updatePassword(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam String userId
    		,@ApiParam(name="password", value="密码", required=false)@RequestParam String password) {
		Supplier<String> businessHandler = () ->{
			try {
				TbUser tbUser = tbUserService.getById(userId);
				tbUser.setPassword(password);
				tbUserService.updateById(tbUser);
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
}
