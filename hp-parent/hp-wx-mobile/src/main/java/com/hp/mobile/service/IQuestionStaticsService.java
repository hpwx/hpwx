package com.hp.mobile.service;

import java.util.List;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.ym.ms.paging.PagerEO;


public interface IQuestionStaticsService {


  List<PoJoSubjectInfo> getSingleSubjectStatics(String questionNaireId, String typeid,
      PagerEO<?> pe);

  List<PoJoSubjectInfo> getMultipSubjectStatics(String questionNaireId, String typeid,
      PagerEO<?> pe);

  List<PoJoSubjectInfo> getScoreSubjectStatics(String questionNaireId, String typeid);

  List<PoJoSubjectInfo> getCompletionSubjectStatics(String questionNaireId, String typeid);
}
