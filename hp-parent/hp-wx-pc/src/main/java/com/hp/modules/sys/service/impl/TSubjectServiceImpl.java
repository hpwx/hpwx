package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.Constant;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.common.utils.R;
import com.hp.hpenum.SubjectEnum;
import com.hp.modules.sys.dao.TQuestionnaireDao;
import com.hp.modules.sys.dao.TQuestionnaireSubjectDao;
import com.hp.modules.sys.dao.TSubjectDao;
import com.hp.modules.sys.dao.TSurveyAnswersDao;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TQuestionnaireSubject;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TSurveyAnswers;
import com.hp.modules.sys.form.SubjectForm;
import com.hp.modules.sys.service.TSubjectService;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("tSubjectService")
public class TSubjectServiceImpl extends ServiceImpl<TSubjectDao, TSubject> implements TSubjectService {

    @Autowired
    private TQuestionnaireSubjectDao tQuestionnaireSubjectDao;

    @Autowired
    private TSubjectDao tSubjectDao;

    @Autowired
    private TSurveyAnswersDao tSurveyAnswersDao;

    @Autowired
    private TQuestionnaireDao tQuestionnaireDao;


    @Override
    public Page<TSubject> getSubjectByQuestionnaireId(Map<String,Object> map,Page<TSubject> page) {

        return page.setRecords(tSubjectDao.selectSubjectListByQuestionnaireId(map,page));
    }

    @Override
    public PageUtils getAll(Map<String, Object> params) {

        Page<TSubject>  page = this.selectPage(new Query<TSubject>(params).getPage(), new EntityWrapper<TSubject>());

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

    @Transactional
    @Override
    public R insertSubject(SubjectForm subjectForm,String userId) {
        if(org.apache.commons.lang.StringUtils.isBlank(userId)){
            return R.error("userId不能为空");
        }
        //查不到问卷的存在
        TQuestionnaire tQuestionnaire = tQuestionnaireDao.selectByPrimaryKey(subjectForm.getQuestionnaireId());
        if(tQuestionnaire == null){
            return R.error("问卷不存在");
        }
        //单选
        // 1 把题目的标题和类型type插入subject 返回主键
        TSubject tSubject = new TSubject();
        tSubject.setCreateUser(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tSubject.setCreateTime(date);
        tSubject.setName(subjectForm.getSubjectName());
        tSubject.setNameImage(subjectForm.getNameImage());
        tSubject.setDeleted(Constant.NO);
        tSubject.setTypeId(subjectForm.getType());
        tSubject.setMustAnswer(subjectForm.getIsRequired());
        tSubjectDao.insertSelective(tSubject);
        //1问题id  subjectId
        Long subjectId = tSubject.getObjectId();
        //2 把题目id和问卷id插入中间表
        TQuestionnaireSubject tQuestionnaireSubject = new TQuestionnaireSubject();
        tQuestionnaireSubject.setCreateUser(userId);
        tQuestionnaireSubject.setDeleted(Constant.NO);
        tQuestionnaireSubject.setMustAnswer(subjectForm.getIsRequired());
        tQuestionnaireSubject.setSubjectId(subjectId);
        tQuestionnaireSubject.setQuestionnaireId(subjectForm.getQuestionnaireId());
        tQuestionnaireSubjectDao.insert(tQuestionnaireSubject);

        if(subjectForm.getType() == SubjectEnum.SUBJECT_RADIO.getCode()){

            // 3 把题目id和问题插入t_survey_answer
            String[] questionRadioItems = subjectForm.getQuestionRadioItems();
            for (int i = 0; i < questionRadioItems.length ; i++) {
                TSurveyAnswers tSurveyAnswers = new TSurveyAnswers();
                tSurveyAnswers.setQuestionId(subjectId);
                tSurveyAnswers.setChoiceText(questionRadioItems[i]);
                tSurveyAnswers.setCreateUser(userId);
                tSurveyAnswers.setCreateTime(new Date());
                tSurveyAnswers.setDeleted(Constant.NO);
                tSurveyAnswersDao.insert(tSurveyAnswers);
            }
            // 4 select出正确答案id
            int answerId = tSurveyAnswersDao.selectIdByChoiceText(subjectForm.getRadioAnswer(),subjectId);

            // 5 更新正确答案id到subject表
            tSubject.setObjectId(subjectId);
            tSubject.setSubjectAnswer(String.valueOf(answerId));
            tSubjectDao.updateByPrimaryKeySelective(tSubject);

        }else if(subjectForm.getType() == SubjectEnum.SUBJECT_CHECKBOX.getCode()){
            //多选
            // 3 把题目id和问题插入t_survey_answer
            String[] questionCheckboxItems = subjectForm.getQuestionCheckboxItems();
            if(questionCheckboxItems.length == 0){
                return R.error("问题不能为空");
            }
            for (int i = 0; i < questionCheckboxItems.length ; i++) {
                TSurveyAnswers tSurveyAnswers = new TSurveyAnswers();
                tSurveyAnswers.setQuestionId(subjectId);
                tSurveyAnswers.setChoiceText(questionCheckboxItems[i]);
                tSurveyAnswers.setCreateUser(userId);
                tSurveyAnswers.setCreateTime(new Date());
                tSurveyAnswers.setDeleted(Constant.NO);
                tSurveyAnswersDao.insert(tSurveyAnswers);
            }
            if(subjectForm.getCheckBoxAnswer().length == 0){
                return R.error("问题答案不能为空");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < subjectForm.getCheckBoxAnswer().length; i++) {
                // 4 select出正确答案id，用逗号拼接起来
                int id = tSurveyAnswersDao.selectIdByChoiceText(subjectForm.getCheckBoxAnswer()[i],subjectId);
                sb.append(id).append(",");
            }
            //多选题答案拼接
            String answerIdMore = sb.substring(0,sb.length()-1);
            // 5 更新正确答案id到subject表
            tSubject.setObjectId(subjectId);
            tSubject.setSubjectAnswer(answerIdMore);
            tSubjectDao.updateByPrimaryKeySelective(tSubject);

        }else if(subjectForm.getType() == SubjectEnum.SUBJECT_INPUT.getCode()){
            // 填空题
            // 3 更新正确答案插入到subject表
            tSubject.setObjectId(subjectId);
//            if(org.apache.commons.lang.StringUtils.isBlank(subjectForm.getInputAnswer())){
//                return R.error("填空题答案不能为空");
//            }
//            tSubject.setSubjectAnswer(subjectForm.getInputAnswer());
            tSubjectDao.updateByPrimaryKeySelective(tSubject);
        }else if(subjectForm.getType() == SubjectEnum.SUBJECT_STAR.getCode()){
            // 打分题
            // 3 插入分值和星星的个数插入到subject表
            tSubject.setObjectId(subjectId);
            tSubject.setGradeCount(subjectForm.getStarNum());
            tSubject.setEveryGradeScore(subjectForm.getStarScore().toString());
            tSubjectDao.updateByPrimaryKeySelective(tSubject);
        }else{
            return  R.error("暂时不支持传过来的类型哦！");
        }

        return R.ok();
    }

    /***
     * 传题目的id SubjectId
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SubjectForm selectSubjectForm(Long id) {

        SubjectForm subjectForm = new SubjectForm();

        TSubject tSubject = tSubjectDao.selectById(id);

        subjectForm.setSubjectName(tSubject.getName());

        subjectForm.setType(tSubject.getTypeId());

        subjectForm.setSubjectId(tSubject.getObjectId().toString());

        subjectForm.setNameImage(tSubject.getNameImage());

        TQuestionnaireSubject tQuestionnaireSubject = tQuestionnaireSubjectDao.selectQuestionIdBySubjectId(id);

        subjectForm.setQuestionnaireId(tQuestionnaireSubject.getQuestionnaireId());

        subjectForm.setIsRequired(tQuestionnaireSubject.getMustAnswer());

        //回答的正确的id 例如单选是1   多选是1,2,3  填空题是文本字符串，打分题目没有，只有分值和星星数
        String subjectAnswer = tSubject.getSubjectAnswer();

        if(tSubject.getTypeId() == SubjectEnum.SUBJECT_RADIO.getCode()){
            //单选
            //通过问题id查出所有的问题
            List<TSurveyAnswers> tSurveyAnswers = tSurveyAnswersDao.selectTSurveyAnswersBySubjectId(id);
            //所有问题的文案集合
            ArrayList<String> questionList = new ArrayList<>();
            for ( TSurveyAnswers tSurveyAnswer:tSurveyAnswers) {
                questionList.add(tSurveyAnswer.getChoiceText());
                if(tSurveyAnswer.getObjectId().toString().equals(subjectAnswer)){
                    subjectForm.setRadioAnswer(tSurveyAnswer.getChoiceText());
                }
            }
            subjectForm.setQuestionRadioItems(questionList.toArray(new String[questionList.size()]));

        }else if(tSubject.getTypeId() == SubjectEnum.SUBJECT_CHECKBOX.getCode()){
            //多选
            String[] answerIds = subjectAnswer.split(",");

            //通过问题id查出所有的问题
            List<TSurveyAnswers> tSurveyAnswers = tSurveyAnswersDao.selectTSurveyAnswersBySubjectId(id);
            //所有问题的文案集合
            ArrayList<String> questionList = new ArrayList<>();
            //所有答案的集合
            ArrayList<String> answerList = new ArrayList<>();
            for ( TSurveyAnswers tSurveyAnswer:tSurveyAnswers) {
                questionList.add(tSurveyAnswer.getChoiceText());
                for (int i = 0; i < answerIds.length; i++) {
                    if(tSurveyAnswer.getObjectId().toString().equals(answerIds[i])){
                        answerList.add(tSurveyAnswer.getChoiceText());
                    }
                }
            }
            subjectForm.setQuestionCheckboxItems(questionList.toArray(new String[questionList.size()]));
            subjectForm.setCheckBoxAnswer(answerList.toArray(new String[answerList.size()]));

        }else if(tSubject.getTypeId() == SubjectEnum.SUBJECT_INPUT.getCode()){
            //填空题
            subjectForm.setInputAnswer(tSubject.getSubjectAnswer());

        }else if(tSubject.getTypeId() == SubjectEnum.SUBJECT_STAR.getCode()){
            //打分题
            subjectForm.setStarNum(tSubject.getGradeCount());
            subjectForm.setStarScore(Integer.parseInt(tSubject.getEveryGradeScore()));
        }
        return subjectForm;
    }


    @Override
    @Transactional
    public R updateSubject(SubjectForm subjectForm,String userId) {

        String subjectId = subjectForm.getSubjectId();

        tSubjectDao.deleteByPrimaryKey(Long.valueOf(subjectId));

        tQuestionnaireSubjectDao.deleteQuestionnaireBySubjectId(Long.valueOf(subjectId));

        return insertSubject(subjectForm, userId);
    }
}
