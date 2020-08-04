/**
 * 
 */
package com.pactera.znzmo.sysuser;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Administrator
 *
 */
@Api(value = "权限-auth-接口")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {
	@Autowired
	private TbAuthService authService;

	@ApiOperation(value = "通过id获取权限", notes = "通过id获取权限")
	@RequestMapping(value = "/findAuthById", method = RequestMethod.GET)
	public JsonResp findAuthById(@RequestParam("id") Long id) {
		Supplier<TbAuth> businessHandler = () -> {
			try {
				return authService.findByAuthId(id);
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
	}

}
