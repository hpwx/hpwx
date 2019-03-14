package com.ym.db.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;

@Configuration
public class DruidDBConfig {
	private static final Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
	
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    @Value("${spring.datasource.url2}")
    private String dbUrl2;
    
    @Value("${spring.datasource.username2}")
    private String username2;
    
    @Value("${spring.datasource.password2}")
    private String password2;
    
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    
    @Value("${spring.datasource.filters}")
    private String filters;
    
    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;
    
    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    @Qualifier("dataSource")
    public DataSource dataSource(){
    	DruidDataSource datasource = new DruidDataSource();
    	
    	datasource.setUrl(this.dbUrl);
    	datasource.setUsername(username);
    	datasource.setPassword(password);
    	datasource.setDriverClassName(driverClassName);
    	
    	//configuration
    	datasource.setInitialSize(initialSize);
    	datasource.setMinIdle(minIdle);
    	datasource.setMaxActive(maxActive);
    	datasource.setMaxWait(maxWait);
    	datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    	datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    	datasource.setValidationQuery(validationQuery);
    	datasource.setTestWhileIdle(testWhileIdle);
    	datasource.setTestOnBorrow(testOnBorrow);
    	datasource.setTestOnReturn(testOnReturn);
    	datasource.setPoolPreparedStatements(poolPreparedStatements);
    	datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    	try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
    	datasource.setConnectionProperties(connectionProperties);
    	
    	return datasource;
    }
    
    @Bean
    @Primary
    @Qualifier("sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory1() {
    	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    	bean.setDataSource(dataSource());
    	org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    	config.setMapUnderscoreToCamelCase(true);
    	config.setLazyLoadingEnabled(true);
    	config.setCacheEnabled(false);
    	bean.setConfiguration(config);
    	PageHelper helper = new PageHelper();
    	Properties properties = new Properties();
    	properties.setProperty("dialect", "mysql");
    	properties.setProperty("reasonable", "false");
    	properties.setProperty("pageSizeZero", "true");
    	helper.setProperties(properties);
    	bean.setPlugins(new Interceptor[] {helper});
    	
    	try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    
    @Bean     //声明其为Bean实例
    @Qualifier("dataSource2")
    public DataSource dataSource2(){
    	DruidDataSource datasource = new DruidDataSource();
    	
    	datasource.setUrl(this.dbUrl2);
    	datasource.setUsername(username2);
    	datasource.setPassword(password2);
    	datasource.setDriverClassName(driverClassName);
    	
    	//configuration
    	datasource.setInitialSize(initialSize);
    	datasource.setMinIdle(minIdle);
    	datasource.setMaxActive(maxActive);
    	datasource.setMaxWait(maxWait);
    	datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    	datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    	datasource.setValidationQuery(validationQuery);
    	datasource.setTestWhileIdle(testWhileIdle);
    	datasource.setTestOnBorrow(testOnBorrow);
    	datasource.setTestOnReturn(testOnReturn);
    	datasource.setPoolPreparedStatements(poolPreparedStatements);
    	datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
    	try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
    	datasource.setConnectionProperties(connectionProperties);
    	
    	return datasource;
    }
    
    @Bean
    @Qualifier("sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2() {
    	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    	bean.setDataSource(dataSource2());
    	org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    	config.setMapUnderscoreToCamelCase(true);
    	config.setLazyLoadingEnabled(true);
    	config.setCacheEnabled(false);
    	bean.setConfiguration(config);
    	PageHelper helper = new PageHelper();
    	Properties properties = new Properties();
    	properties.setProperty("dialect", "mysql");
    	properties.setProperty("reasonable", "false");
    	properties.setProperty("pageSizeZero", "true");
    	helper.setProperties(properties);
    	bean.setPlugins(new Interceptor[] {helper});
    		
    	try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
    }
    
    /******配置事务管理********/
    @Bean
    public PlatformTransactionManager bfTransactionManager(@Qualifier("dataSource")DataSource dataSource) {
     return new DataSourceTransactionManager(dataSource);
    }
     
    @Bean
    public PlatformTransactionManager bfscrmTransactionManager(@Qualifier("dataSource2")DataSource dataSource) {
     return new DataSourceTransactionManager(dataSource);
    }
}
