package com.hp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import com.hp.datasources.DynamicDataSourceConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceConfig.class})
public class PcApplication extends SpringBootServletInitializer {

  private final static Logger Log = LoggerFactory.getLogger(PcApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(PcApplication.class, args);
    Log.info("微信后台服务已启动......");
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(PcApplication.class);
  }

}
