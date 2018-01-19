package com.sauzny.springboot.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<Student> getList(){
        
        String sql = "SELECT ID,NAME,SUM_SCORE,AVG_SCORE, AGE FROM STUDENT";
        
        return (List<Student>) jdbcTemplate.query(sql,  (rs, rowNum) ->{
            Student stu = new Student();
            stu.setId(rs.getInt("ID"));
            stu.setAge(rs.getInt("AGE"));
            stu.setName(rs.getString("NAME"));
            stu.setSumScore(rs.getString("SUM_SCORE"));
            stu.setAvgScore(rs.getString("AVG_SCORE"));
            return stu;
        });
        
    }
    
    
    // 有的blog说需要在启动spring容器时候需要手动增加@EnableTransactionManagement
    // 测试发现，不需要手动增加@EnableTransactionManagement
    // 增加事务注解即可
    
    @Transactional
    // 设置 隔离级别 传播行为
    // @Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
    public void testTransactional(){

        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "bbb");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "ccc");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "ddd");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "eee");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "fff");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "aaa");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "ggg");
        jdbcTemplate.update("INSERT INTO student (`name`) VALUES (?)", "hhh");
    }
}
