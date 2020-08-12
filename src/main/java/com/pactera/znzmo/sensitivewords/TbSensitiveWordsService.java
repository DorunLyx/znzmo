package com.pactera.znzmo.sensitivewords;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;

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

}
