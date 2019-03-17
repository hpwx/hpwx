package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.Constant;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.TQuestionnaireDao;
import com.hp.modules.sys.dao.TQuestionnaireSubjectDao;
import com.hp.modules.sys.dao.TSubjectDao;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TQuestionnaireSubject;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.service.TSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("tSubjectService")
public class TSubjectServiceImpl extends ServiceImpl<TSubjectDao, TSubject> implements TSubjectService {

    @Autowired
    private TQuestionnaireSubjectDao tQuestionnaireSubjectDao;

    @Override
    public PageUtils getAll(Map<String, Object> params) {
        Page<TSubject> page = this.selectPage(new Query<TSubject>(params).getPage(), new EntityWrapper<TSubject>().eq("deleted", 0));

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(TSubject tSubject) {
        tSubject.setCreateTime(new Date());
        this.insert(tSubject);
    }


    @Override
    @Transactional
    public void update(TSubject tSubject) {

        this.updateById(tSubject);

    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }

    @Override
    public List<TSubject> selectSubjectsByQID(Long objectId) {
        return baseMapper.selectSubjectsByQID(objectId);
    }

    @Override
    public List<Map<String, Object>> selectAnswersByQID(Long objectId) {
        return baseMapper.selectAnswersByQID(objectId);
    }

    @Override
    @Transactional
    public void insertSubject(List<TSubject> subjects) {
        baseMapper.insertBatch(subjects);
        List<TQuestionnaireSubject> list = new ArrayList<>();
        for(TSubject tSubject : subjects){
            TQuestionnaireSubject tQuestionnaireSubject = new TQuestionnaireSubject();
            tQuestionnaireSubject.setSubjectId(tSubject.getObjectId());
//            tQuestionnaireSubject.setQuestionnaireId(tSubject.gettQuestionnaireId());
//            tQuestionnaireSubject.setMustAnswer(tSubject.getMustAnswer());
            tQuestionnaireSubject.setCreateUser(tSubject.getCreateUser());
            tQuestionnaireSubject.setDeleted(Constant.NO);
            list.add(tQuestionnaireSubject);
        }
        tQuestionnaireSubjectDao.insertBatch(list);
    }
}
