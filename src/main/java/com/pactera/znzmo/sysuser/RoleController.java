/**
 * 
 */
package com.pactera.znzmo.sysuser;

import java.util.List;
import java.util.function.Supplier;

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
import com.pactera.znzmo.vo.role.RoleAddParam;
import com.pactera.znzmo.vo.role.RoleAuthAddParam;
import com.pactera.znzmo.vo.role.RoleAuthUpdateParam;
import com.pactera.znzmo.vo.role.RoleQueryParam;
import com.pactera.znzmo.vo.role.RoleUpdateParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Administrator
 *
 */
/**
 * @ClassName：RoleController
 * @Description：角色接口
 * @author liyongxu 
 * @date 2020年9月3日 下午2:27:21 
 * @version 1.0.0 
 */
@Api(value = "角色接口")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@Autowired
	private TbRoleService tbRoleService;
	
	@Autowired
	private TbRoleAuthService tbRoleAuthService;
	
	/**
	 * @Title: getRoleList 
	 * @Description: 角色列表查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午2:43:00 
	*/
	@ApiOperation(value = "角色列表查询", httpMethod = "POST", notes = "角色列表查询")
    @RequestMapping(value = "/getRoleList", method = {RequestMethod.POST})
    public JsonResp getRoleList(
    		@ApiParam(name="roleQueryParam", value="角色列表筛选参数", required=false)@RequestBody RoleQueryParam roleQueryParam) {
		Supplier<IPage<TbRole>> businessHandler = () -> {
			try {
				Page<TbRole> page = new Page<TbRole>(roleQueryParam.getPageNo(), roleQueryParam.getPageSize());
		        IPage<TbRole> iPage = tbRoleService.selectRolePages(page, roleQueryParam);
		        return iPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addRole 
	 * @Description: 角色新增
	 * @param roleAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:13:54 
	*/
	@ApiOperation(value = "角色新增", httpMethod = "POST", notes = "角色新增")
    @RequestMapping(value = "/addRole", method = {RequestMethod.POST})
	public JsonResp addRole(
			@ApiParam(name="roleAddParam", value="角色新增参数", required=false)@RequestBody RoleAddParam roleAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbRoleService.addRole(roleAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getRoleInfo 
	 * @Description: 角色详情
	 * @param roleId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:26:31 
	*/
	@ApiOperation(value = "角色详情", httpMethod = "POST", notes = "角色详情")
    @RequestMapping(value = "/getRoleInfo", method = {RequestMethod.POST})
    public JsonResp getRoleInfo(
    		@ApiParam(name="roleId", value="角色id", required=false)@RequestParam String roleId) {
		Supplier<TbRole> businessHandler = () ->{
			try {
				return tbRoleService.getById(roleId);
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteRole 
	 * @Description: 角色编辑
	 * @param roleUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:27:13 
	*/
	@ApiOperation(value = "角色编辑", httpMethod = "POST", notes = "角色编辑")
	@RequestMapping(value = "/updteRole", method = {RequestMethod.POST})
	public JsonResp updteRole(
			@ApiParam(name="roleUpdateParam", value="角色编辑参数", required=false)@RequestBody RoleUpdateParam roleUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbRoleService.updteRole(roleUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: deleteRole 
	 * @Description: 删除角色
	 * @param roleId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:32:08 
	*/
	@ApiOperation(value = "删除角色", httpMethod = "POST", notes = "删除角色")
    @RequestMapping(value = "/deleteRole", method = {RequestMethod.POST})
    public JsonResp deleteRole(
    		@ApiParam(name="roleId", value="角色id", required=false)@RequestParam String roleId) {
		Supplier<String> businessHandler = () ->{
			try {
				tbRoleService.deleteRole(roleId);
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addRoleAuth 
	 * @Description: 角色权限新增
	 * @param roleAuthAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午3:57:42 
	*/
	@ApiOperation(value = "角色权限新增", httpMethod = "POST", notes = "角色权限新增")
    @RequestMapping(value = "/addRoleAuth", method = {RequestMethod.POST})
	public JsonResp addRoleAuth(
			@ApiParam(name="roleAuthAddParam", value="角色权限新增参数", required=false)@RequestBody RoleAuthAddParam roleAuthAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbRoleAuthService.addRoleAuth(roleAuthAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getRoleAuthInfo 
	 * @Description: 角色权限详情
	 * @param roleId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午4:03:26 
	*/
	@ApiOperation(value = "角色权限详情", httpMethod = "POST", notes = "角色权限详情")
    @RequestMapping(value = "/getRoleAuthInfo", method = {RequestMethod.POST})
    public JsonResp getRoleAuthInfo(
    		@ApiParam(name="roleId", value="角色id", required=false)@RequestParam String roleId) {
		Supplier<List<TbRoleAuth>> businessHandler = () ->{
			try {
				QueryWrapper<TbRoleAuth> queryWrapper = new QueryWrapper<TbRoleAuth>();
				queryWrapper.eq(TbRoleAuth.ROLE_ID, roleId);
				List<TbRoleAuth> list = tbRoleAuthService.list(queryWrapper);
				return list;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteRoleAuth 
	 * @Description: 角色权限编辑
	 * @param roleAuthUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年9月3日 下午4:05:49 
	*/
	@ApiOperation(value = "角色权限编辑", httpMethod = "POST", notes = "角色权限编辑")
	@RequestMapping(value = "/updteRoleAuth", method = {RequestMethod.POST})
	public JsonResp updteRoleAuth(
			@ApiParam(name="roleAuthUpdateParam", value="角色权限编辑参数", required=false)@RequestBody RoleAuthUpdateParam roleAuthUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbRoleAuthService.updteRoleAuth(roleAuthUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
}
