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
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("tQuestionnaireService")
public class TQuestionnaireServiceImpl extends ServiceImpl<TQuestionnaireDao, TQuestionnaire> implements TQuestionnaireService {

    @Override
    public PageUtils getAll(Map<String, Object> params){
        Page<TQuestionnaire> page = this.selectPage(new Query<TQuestionnaire>(params).getPage());

        return new PageUtils(page);
    }
}
