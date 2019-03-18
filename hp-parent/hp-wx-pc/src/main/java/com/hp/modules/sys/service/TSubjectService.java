 
package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.hpenum.SubjectEnum;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.form.SubjectForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TSubjectService extends IService<TSubject> {

    PageUtils getAll(Map<String, Object> params);

    void save(TSubject tSubject);

    void update(TSubject tSubject);

    void deleteBatch(Long[] ids);

    List<TSubject> selectSubjectsByQID(Long objectId);

    List<Map<String,Object>> selectAnswersByQID(Long objectId);

    void insertSubject(List<TSubject> subjects);

    R insertSubject(SubjectForm subjectForm, String userId);

    SubjectForm selectSubjectForm(Long id);

    R updateSubject(SubjectForm subjectForm,String userId);
}
 
