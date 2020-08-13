package com.pactera.znzmo.drawing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.drawing.DrawingAddParam;
import com.pactera.znzmo.vo.drawing.DrawingUpdateParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;

/**
 * <p>
 * 图纸方案表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbDrawingSchemeService extends IService<TbDrawingScheme> {

	/**
	 * @Title: selectDrawingPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param modelQueryParam
	 * @return IPage<TbDrawingScheme>
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:50:26 
	*/
	IPage<TbDrawingScheme> selectDrawingPages(Page<TbDrawingScheme> page, ModelQueryParam modelQueryParam);

	/**
	 * @Title: addDrawing 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param drawingAddParam void
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:54:30 
	*/
	void addDrawing(DrawingAddParam drawingAddParam);

	/**
	 * @Title: updteDrawing 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param drawingUpdateParam void
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:56:25 
	*/
	void updteDrawing(DrawingUpdateParam drawingUpdateParam);

}
