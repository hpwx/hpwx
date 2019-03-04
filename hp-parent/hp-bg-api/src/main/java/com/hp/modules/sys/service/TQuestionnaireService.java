package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.entity.TQuestionnaire;

import java.util.Map;

public interface TQuestionnaireService  extends IService<TQuestionnaire> {

    PageUtils getAll(Map<String, Object> params);

}
