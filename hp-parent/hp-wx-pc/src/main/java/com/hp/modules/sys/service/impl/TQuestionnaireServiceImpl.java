package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.Constant;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.common.utils.R;
import com.hp.modules.sys.dao.TQuestionnaireDao;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.service.TQuestionnaireService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("tQuestionnaireService")
public class TQuestionnaireServiceImpl extends ServiceImpl<TQuestionnaireDao, TQuestionnaire> implements TQuestionnaireService {

    @Autowired
    private TQuestionnaireDao tQuestionnaireDao;

    @Override
    public PageUtils getAll(Map<String, Object> params) {
        Page<TQuestionnaire> page = null;

        if(params.containsKey("title")){
            page = this.selectPage(new Query<TQuestionnaire>(params).getPage(), new EntityWrapper<TQuestionnaire>().eq("deleted", 0).like(params.containsKey("title"),"title",params.get("title").toString(), SqlLike.RIGHT));
        }else{
           page = this.selectPage(new Query<TQuestionnaire>(params).getPage(), new EntityWrapper<TQuestionnaire>().eq("deleted", 0));
        }

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


    @Override
    public void deleteByIds(Long[] ids) {

        for(int i = 0 ; i< ids.length ; i++){
            TQuestionnaire tQuestionnaire = new TQuestionnaire();
            tQuestionnaire.setDeleted(Constant.YES);
            this.update(tQuestionnaire,
                    new EntityWrapper<TQuestionnaire>().eq("object_id", ids[i]));
        }

    }

    /**
     * Author
     * cz
     * 置顶的Service
     * 问卷objectId
     * @param id
     */
    @Override
    @Transactional
    public void top(Long id) {
        //查看是否有置顶的
        TQuestionnaire tQuestionnaire = tQuestionnaireDao.selectByIdIsHaveTop(id);
        if(tQuestionnaire != null){
            tQuestionnaire.setQuestionnaireIsTop((byte)0);
            this.update(tQuestionnaire);
            tQuestionnaireDao.updateTopById(id);
        }else{
            tQuestionnaireDao.updateTopById(id);
        }
    }

    @Override
    public void down(Long id) {

        tQuestionnaireDao.updateDownById(id);

    }

    /**
     * Author
     * cz
     * 置顶的Service
     * 问卷删除objectId
     * @param ids
     */
    @Override
    public void del(Long[] ids) {

        for (int i = 0; i < ids.length; i++) {
            tQuestionnaireDao.deleteByPrimaryKey(ids[i]);
        }

    }

    @Override
    public PageUtils selectStatistics(Map<String, Object> params) {

        int currentPage =Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        params.put("currentCounts",(currentPage-1)*limit);

        List<Map<String, Object>> list = baseMapper.selectStatistics(params);
        int counts = baseMapper.selectCountStatistics(params);

        PageUtils pageUtils = new PageUtils(list,counts,limit,currentPage);

        return pageUtils;
    }

    @Override
    public Map statisticsOnResult(Map<String, Object> params) {
        Map map = new HashMap<String,Object>();
        List<Map<String,Object>> trueMap = tQuestionnaireDao.StatisticsOnTrue(params);
        List<Map<String,Object>> allMap =  tQuestionnaireDao.StatisticsOnAll(params);
        map.put("all",allMap);
        map.put("true",trueMap);
        return map;
    }

    @Override
    public void updateQuestionEnable(Map<String, Object> map) {

        tQuestionnaireDao.updateEnableById(map);
    }
}
