package com.pactera.znzmo.sensitivewords;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;
import com.pactera.znzmo.vo.sensitivewords.SensiWordQueryParam;

/**
 * <p>
 * 敏感词表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbSensitiveWordsService extends IService<TbSensitiveWords> {

	void addSensitiveWord(SensiWordAddParam sensiWordAddParam);

	IPage<TbSensitiveWords> selectSensiWordPages(Page<TbSensitiveWords> page, SensiWordQueryParam sensiWordQueryParam);

}
