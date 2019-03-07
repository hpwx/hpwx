package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.TSubjectDao;
import com.hp.modules.sys.dao.TSurveyAnswersDao;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TSurveyAnswers;
import com.hp.modules.sys.service.TSubjectService;
import com.hp.modules.sys.service.TSurveyAnswersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("tSurveyAnswersService")
public class TSurveyAnswersServiceImpl extends ServiceImpl<TSurveyAnswersDao, TSurveyAnswers> implements TSurveyAnswersService {



    @Override
    public List<TSurveyAnswers> getBySubjectIds(String ids) {
        List<TSurveyAnswers> list = this.selectList(new EntityWrapper<TSurveyAnswers>().in("question_id", ids).orderBy("object_id"));
        
        return list;
    }

    @Override
    @Transactional
    public void save(TSurveyAnswers tSurveyAnswers) {
        tSurveyAnswers.setCreateTime(new Date());
        this.insert(tSurveyAnswers);
    }


    @Override
    @Transactional
    public void update(TSurveyAnswers tSurveyAnswers) {

        this.updateById(tSurveyAnswers);

    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }
}
