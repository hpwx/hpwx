package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TSurveyAnswers;

import java.util.List;
import java.util.Map;

public interface TSurveyAnswersService extends IService<TSurveyAnswers> {

    List<TSurveyAnswers> getBySubjectIds(String ids);

    void save(TSurveyAnswers tSurveyAnswers);

    void update(TSurveyAnswers tSurveyAnswers);

    void deleteBatch(Long[] ids);

}
