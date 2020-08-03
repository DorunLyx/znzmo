/**
 * 
 */
package com.pactera.znzmo.admin.hd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.znzmo.util.PageRequest;
import com.pactera.znzmo.vo.HttpResult;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("user")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	@GetMapping(value = "/findByUserId")
	public Object findByUserId(@RequestParam Long userId) {
		return sysUserService.findByUserId(userId);
	}

	@PreAuthorize("hasAuthority('sys:user:view')")
	@GetMapping(value = "/findAll")
	public Object findAll() {
		return sysUserService.findAll();
	}

	@PostMapping(value = "/findPage")
	public Object findPage(@RequestBody PageRequest pageQuery) {
		return sysUserService.findPage(pageQuery);
	}

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

}
