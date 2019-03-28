package com.hp.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.hp.modules.sys.service.IQuestionStaticsService;
import com.ym.ms.paging.PagerEO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hp.modules.sys.entity.PoJoSubjectInfo;
import com.hp.modules.sys.entity.PollStarInfo;
import com.hp.modules.sys.entity.TSurveyAnswers;
import com.hp.modules.sys.dao.PoJoSubjectInfoDao;
import com.hp.modules.sys.dao.TSurveyAnswersDao;
import com.hp.modules.sys.dao.UserAnswerDetailDao;

@Service
public class QuestionStaticsServiceImpl implements IQuestionStaticsService {


    @Autowired
    private PoJoSubjectInfoDao subjectinfoMapper;



    @Autowired
    private TSurveyAnswersDao answersMapper;


    @Autowired
    private UserAnswerDetailDao userAnswerDetail;

    /**
     * 单选统计
     */
    @Override
    public List<PoJoSubjectInfo> getSingleSubjectStatics(String questionNaireId, String typeid,
                                                         PagerEO<?> pe) {

//        PageHelper.startPage(pe.getPage(), pe.getLimit());

        List<PoJoSubjectInfo> subjectlist =
                subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
                        Long.parseLong(questionNaireId), Integer.valueOf(typeid));
        for (PoJoSubjectInfo subjectInfo : subjectlist) {
            Long subjectid = subjectInfo.getSubjectid();
            List<TSurveyAnswers> surveryAnserslist = answersMapper.selectListBySubjectId(subjectid);

            BuiderSubjectlist(subjectInfo, surveryAnserslist);
        }

        return subjectlist;
    }


    /**
     * 多选统计
     */
    @Override
    public List<PoJoSubjectInfo> getMultipSubjectStatics(String questionNaireId, String typeid,
                                                         PagerEO<?> pe) {

//        PageHelper.startPage(pe.getPage(), pe.getLimit());

        List<PoJoSubjectInfo> subjectlist =
                subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
                        Long.parseLong(questionNaireId), Integer.valueOf(typeid));
        for (PoJoSubjectInfo subjectInfo : subjectlist) {
            Long subjectid = subjectInfo.getSubjectid();
            List<TSurveyAnswers> surveryAnserslist = answersMapper.selectListBySubjectId(subjectid);

            BuiderSubjectlist(subjectInfo, surveryAnserslist);
        }



        return subjectlist;

    }


    /***
     * 打分题
     */
    @Override
    public List<PoJoSubjectInfo> getScoreSubjectStatics(String questionNaireId, String typeid) {

        List<PoJoSubjectInfo> subjectlist = subjectinfoMapper
                .getSubjectListForScoreStatics(Long.parseLong(questionNaireId), Integer.valueOf(typeid));

        for (PoJoSubjectInfo subjectinfo : subjectlist) {
            Integer totalPoll = userAnswerDetail.getToalPoll(questionNaireId,
                    subjectinfo.getSubjectid().toString(), typeid);
            Integer starcount = subjectinfo.getGradecount();
            Long subjectId = subjectinfo.getSubjectid();
            // 星星个数

            List<PollStarInfo> pollstarInfo = new ArrayList<>();

            for (int i = starcount; i >= 1; i--) {
                PollStarInfo p = new PollStarInfo();
                p.setStarnum(i);// 星星数
                Integer starnumpoll = userAnswerDetail.getPollByStarNum(String.valueOf(i), subjectId);
                p.setPoll(starnumpoll); // 投票 数量
                p.setTotalpoll(totalPoll); // 投票总数
                pollstarInfo.add(p);
            }
            subjectinfo.setPolllist(pollstarInfo);
        }
        return subjectlist;
    }



    /***
     * 填空题
     */
    @Override
    public List<PoJoSubjectInfo> getCompletionSubjectStatics(String questionNaireId, String typeid) {
        // TODO Auto-generated method stub
        List<PoJoSubjectInfo> subjectlist =
                subjectinfoMapper.getSubjectListByQustionNaireIdAndSubjectType(
                        Long.parseLong(questionNaireId), Integer.valueOf(typeid));



        return subjectlist;

    }



    /***
     *
     * @Author yuruyi
     * @Description 将选项拼接到 subject下的 choicelist
     * @Date 2019年3月25日
     * @Param
     * @return
     *
     */

    private void BuiderSubjectlist(PoJoSubjectInfo pojosubjectinfo,
                                   List<TSurveyAnswers> surveryanserlist) {
        List<Map<String, Object>> list = new ArrayList<>();

        Integer totalpoll = 0;
        for (TSurveyAnswers sub : surveryanserlist) {
            Map<String, Object> item = new HashMap<>();
            item.put("subjectId", sub.getQuestionId());
            item.put("choiceId", sub.getObjectId());
            item.put("choicetext", sub.getChoiceText());
            item.put("poll", sub.getPoll());
            list.add(item);
            Integer curentpoll = sub.getPoll() == null ? 0 : sub.getPoll();
            totalpoll += curentpoll;
            pojosubjectinfo.setChocelist(list);
        }
        pojosubjectinfo.setTotalpoll(totalpoll);

    }



}

