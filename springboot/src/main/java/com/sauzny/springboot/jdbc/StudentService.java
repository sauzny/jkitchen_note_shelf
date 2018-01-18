package com.sauzny.springboot.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
}
