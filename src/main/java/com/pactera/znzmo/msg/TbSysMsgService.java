package com.pactera.znzmo.msg;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.msg.MsgAddParam;
import com.pactera.znzmo.vo.msg.MsgQueryParam;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbSysMsgService extends IService<TbSysMsg> {
	/**
	 * @param msgAddParam
	 */
	void addSysMsg(MsgAddParam msgAddParam);
	
	IPage<TbSysMsg> selectSysMsgPages(Page<TbSysMsg> page, MsgQueryParam msgQueryParam);

	void updateSysMsg(MsgAddParam msgAddParam);
}
