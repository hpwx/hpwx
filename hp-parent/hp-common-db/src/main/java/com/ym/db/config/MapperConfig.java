package com.ym.db.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ym.db.annotation.DataSource1;
import com.ym.db.annotation.DataSource2;

@Configuration
@AutoConfigureAfter(DruidDBConfig.class)
public class MapperConfig {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigure1() {
		MapperScannerConfigurer config = new MapperScannerConfigurer();
		config.setSqlSessionFactoryBeanName("sqlSessionFactory1");
		config.setBasePackage("com.ym.**.mapper");
		config.setAnnotationClass(DataSource1.class);
		return config;
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigure2() {
		MapperScannerConfigurer config = new MapperScannerConfigurer();
		config.setSqlSessionFactoryBeanName("sqlSessionFactory2");
		config.setBasePackage("com.ym.**.mapper");
		config.setAnnotationClass(DataSource2.class);
		return config;
	}
}
