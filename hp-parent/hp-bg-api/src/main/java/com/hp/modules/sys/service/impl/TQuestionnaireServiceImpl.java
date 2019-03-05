package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.TQuestionnaireDao;
import com.hp.modules.sys.entity.SysUserEntity;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.service.TQuestionnaireService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service("tQuestionnaireService")
public class TQuestionnaireServiceImpl extends ServiceImpl<TQuestionnaireDao, TQuestionnaire> implements TQuestionnaireService {

    @Override
    public PageUtils getAll(Map<String, Object> params) {
        Page<TQuestionnaire> page = this.selectPage(new Query<TQuestionnaire>(params).getPage(), new EntityWrapper<TQuestionnaire>().eq("deleted", 2));

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(TQuestionnaire tQuestionnaire) {
        tQuestionnaire.setCreateTime(new Date());
        this.insert(tQuestionnaire);
    }


    @Override
    @Transactional
    public void update(TQuestionnaire tQuestionnaire) {

        this.updateById(tQuestionnaire);

    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }
}
