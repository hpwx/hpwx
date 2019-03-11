package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.TQuestionnaireDao;
import com.hp.modules.sys.dao.TSubjectDao;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.service.TSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("tSubjectService")
public class TSubjectServiceImpl extends ServiceImpl<TSubjectDao, TSubject> implements TSubjectService {

    @Override
    public PageUtils getAll(Map<String, Object> params) {
        Page<TSubject> page = this.selectPage(new Query<TSubject>(params).getPage(), new EntityWrapper<TSubject>().eq("deleted", 2));

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
}
