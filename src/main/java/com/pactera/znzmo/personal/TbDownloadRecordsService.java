package com.pactera.znzmo.personal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.sysuser.SysUser;
import com.pactera.znzmo.vo.personal.DownloadRecordsAddParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsQueryParam;
import com.pactera.znzmo.vo.personal.DownloadRecordsVO;

/**
 * <p>
 * 下载记录表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-26
 */
public interface TbDownloadRecordsService extends IService<TbDownloadRecords> {

	/**
	 * @Title: selectDownloadRecordsPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param downloadRecordsQueryParam
	 * @return IPage<DownloadRecordsVO>
	 * @author liyongxu
	 * @param user 
	 * @date 2020年8月26日 上午11:03:28 
	*/
	IPage<DownloadRecordsVO> selectDownloadRecordsPages(Page<TbDownloadRecords> page,
			DownloadRecordsQueryParam downloadRecordsQueryParam);

	/**
	 * @Title: addDownloadRecords 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param downloadRecordsAddParam void
	 * @author liyongxu
	 * @param user 
	 * @date 2020年8月26日 下午2:07:37 
	*/
	void addDownloadRecords(DownloadRecordsAddParam downloadRecordsAddParam, SysUser user);

}
