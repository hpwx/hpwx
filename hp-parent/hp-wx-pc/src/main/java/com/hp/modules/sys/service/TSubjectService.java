package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;

import java.util.Map;

public interface TSubjectService extends IService<TSubject> {

    PageUtils getAll(Map<String, Object> params);

    void save(TSubject tSubject);

    void update(TSubject tSubject);

    void deleteBatch(Long[] ids);

}
