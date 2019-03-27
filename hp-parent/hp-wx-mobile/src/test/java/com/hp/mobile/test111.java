package com.hp.mobile;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.google.common.base.Joiner;
import com.hp.mobile.mapper.TSurveyAnswersMapper;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MobileApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class test111 {
  @Autowired


  TSurveyAnswersMapper mapper;

  @Test
  public void SNWSWW() {
    List<String> list = new ArrayList<>();


    list.add("201");
    list.add("202");
    list.add("203");
    String ansserresultids = Joiner.on(",").join(list);
    // 修改投票次数
    int rows = mapper.updateByobjectIds(list);

    System.out.println(rows);
  }

}
