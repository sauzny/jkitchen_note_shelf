package com.sauzny.springboot.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * *************************************************************************
 * @文件名称: MyDataSource.java
 *
 * @包路径  : com.sauzny.springboot.jdbc 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   更换JdbcTemplate中的DataSource
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月18日 - 上午11:21:44 
 *	
 **************************************************************************
 */
@Configuration
public class MyDataSource {

    @Autowired
    private Environment env;
    
    
    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
        // 其他需要的属性
        /*
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setValidationQuery(validationQuery);
        */
        return dataSource;
    }

    // 即使没有上面的druid的代码，下面的代码保持注释的状态，spring boot也能使用 HikariCP
    // 下面的代码是根据 https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
    // 对mysql的配置
    /*
    @Bean
    public HikariDataSource dataSource() {
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("spring.datasource.url"));
        config.setUsername(env.getProperty("spring.datasource.username"));//用户名
        config.setPassword(env.getProperty("spring.datasource.password"));//密码
        // driver 可以省略？？
        //config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        HikariDataSource dataSource = new HikariDataSource(config);
        
        return dataSource;
    }
    */
}
