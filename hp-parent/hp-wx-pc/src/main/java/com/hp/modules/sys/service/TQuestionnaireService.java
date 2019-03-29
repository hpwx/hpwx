package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.modules.sys.entity.TQuestionnaire;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TQuestionnaireService extends IService<TQuestionnaire> {

    PageUtils getAll(Map<String, Object> params);

    void save(TQuestionnaire tQuestionnaire);

    void update(TQuestionnaire tQuestionnaire);

    void deleteBatch(Long[] ids);

    void deleteByIds(Long[] ids);

    PageUtils selectStatistics(Map<String, Object> params);

    Map<String,Object> statisticsOnResult(Map<String, Object> params);

    void top(Long id);

    void down(Long id);

    void del(Long[] ids);

    void updateQuestionEnable(Map<String,Object> map);

}
