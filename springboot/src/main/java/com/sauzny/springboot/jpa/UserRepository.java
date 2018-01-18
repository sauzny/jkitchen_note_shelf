package com.sauzny.springboot.jpa;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//<User, Long> 泛型指定了能接受的对象类型和其主键类型，主键类型在一些方法里很有用。
public interface UserRepository extends JpaRepository<User, Long> {

    // JpaRepository 
    /*
        JpaRepository支持接口规范方法名查询。
                        意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现，目前支持的关键字如下。

                        可查看 md/JpaRepository.md 中的描述
     */
    
    // hql(sql)查询
    
    // @Query的1代表的是方法参数里面的顺序,hql
    @Query("select t from User t where t.taskName = ?1")
    User findByUserName1(String taskName);

    // 本地原生sql，注意表名，因为是原生sql
    @Query(value = "select * from user t where t.task_name = ?1", nativeQuery = true)
    User findByUserName2(String taskName);

    // @Param注解绑定参数
    @Query("select t from User t where t.taskName = :taskName and t.createTime = :createTime")
    User findByUserName3(@Param("taskName") String taskName, @Param("createTime") Date createTime);

    // 问号绑定参数
    @Query("select t from User t where t.taskName = ? and t.createTime = ?")
    User findByUserName4(String taskName, Date createTime);

    // SPEL表达式，泛型DAO的时候可能会用到
    /**
        　　实体类User,使用@Entity注解后，spring会将实体类User纳入管理。默认'#{#entityName}'的值就是'User'。
        
        　　但是如果使用了@Entity(name = "user")来注解实体类User,此时'#{#entityName}'的值就变成了'book'。
        
        　　到此，事情就明了了，只需要在用@Entity来注解实体类时指定name为此实体类对应的表名。在原生sql语句中，就可以把'#{#entityName}'来作为数据表名使用。
     */
    @Query("select t from #{#entityName} t where t.taskName = ? and t.createTime = ?")
    User findByUserName5(String taskName, Date createTime);

    
    //hql(sql)修改
    @Modifying
    @Query("update User t set t.taskName = ?1 where t.id = ?2")
    int updateUser(String taskName, Long id);
}
