package com.pactera.znzmo.log;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.LogQueryParam;

/**
 * <p>
 * 日志记录表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */

public interface TbLogService extends IService<TbLog> {
	
	/**
	 * @param page
	 * @param logQueryParam
	 * @return
	 */
	IPage<TbLog> selectTbLogPages(Page<TbLog> page, LogQueryParam logQueryParam);
}
