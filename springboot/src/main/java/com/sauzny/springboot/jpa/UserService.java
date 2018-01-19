package com.sauzny.springboot.jpa;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // 增加此注解，即可
    @Transactional
    // 设置 隔离级别 传播行为
    // @Transactional(isolation=Isolation.DEFAULT, propagation=Propagation.REQUIRED)
    public void testTransactional(){
        
        // 创建10条记录
        userRepository.save(new User("AAA", "testAAAmail@gmail.com"));
        userRepository.save(new User("BBB", "testBBBmail@gmail.com"));
        userRepository.save(new User("CCC", "testCCCmail@gmail.com"));
        userRepository.save(new User("DDD", "testDDDmail@gmail.com"));
        userRepository.save(new User("EEE", "testEEEmail@gmail.com"));
        userRepository.save(new User("FFF", "testFFFmail@gmail.com"));
        userRepository.save(new User("GGG", "testGGGmail@gmail.com"));
        userRepository.save(new User("HHH", "testHHHmail@gmail.com"));
        userRepository.save(new User("III", "testIIImail@gmail.com"));
        userRepository.save(new User("JJJ", "testJJJmail@gmail.com"));
        
        userRepository.save(new User("JJJJJJJJJJJJJJJ", "testJJJmail@gmail.com"));
    }
    
    @Test
    public void save() {


        // 保存单个对象并返回。
        User savedUser = userRepository.save(new User("TestBob", "testmail@gmail.com")); 
        
        // 保存多个对象并返回。
        List<User> userList = Lists.newArrayList();
        User newUser;
        for (int i = 0; i < 10; i++) {
            newUser = new User("TestBob" + i, "testmail"+i+"@gmail.com");
            userList.add(newUser);
        }
        List<User> savedUserList = userRepository.save(userList);
        
    }
    
    @Test
    public void find(){
        
        // 根据主键查询单个对象。
        User foundUser = userRepository.findOne(1L);
        
        // 查询全部对象。
        List<User> foundUserList = userRepository.findAll();
        
        // 根据 id 字段查询并排序，默认是顺序（ASC）。
        List<User> foundASCSortedUserList = userRepository.findAll(new Sort("id"));
        
        // 根据 id 字段倒序查询（DESC）。
        List<User> foundDESCSortedUserList = userRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        
        
        User user = new User("TestBob", "test@gmail.com");
        // 1.使用 Example。
        // 创建 Example。
        Example<User> userExample = Example.of(user);
        User foundExampleUser = userRepository.findOne(userExample);
        // 2.使用 ExampleMatcher。
        // 创建 ExampleMatcher。
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                // 忽略 id 和 createTime 字段。
                .withIgnorePaths("id", "createTime")
                // 忽略大小写。
                .withIgnoreCase()
                // 忽略为空字段。
                .withIgnoreNullValues();
        // 携带 ExampleMatcher。
        userExample = Example.of(user, exampleMatcher);
        User foundExampleWithExampleMatcherUser = userRepository.findOne(userExample);
    }
    
    @Test
    public void page(){
        
        // 分页查询，从 0 页开始查询 5 个。
        Page<User> foundUserPage = userRepository.findAll(new PageRequest(0, 5));
        // 分页表。
        List<User> content = foundUserPage.getContent();
        // 总数量。
        long totalElements = foundUserPage.getTotalElements();
        // 总页数。
        long totalPages = foundUserPage.getTotalPages();
        // 分页表大小。
        int size = foundUserPage.getSize();
    }
    
    @Test
    public void del(){
        
        // 根据主键删除单个对象
        userRepository.delete(1L);
        
        // 根据对象删除单个对象
        User user = new User("TestBob", "testmail@gmail.com"); 
        userRepository.delete(user);
        
        // 删除全部
        userRepository.deleteAll();
        
        // 删除多个对象
        List<User> userList = Lists.newArrayList();
        User userTemp = null;
        for (int i = 0; i < 10; i++) {
            userTemp = new User("TestBob" + i, "testmail"+i+"@gmail.com");
            userList.add(userTemp);
        }
        userRepository.delete(userList);
    }
    
    @Test
    public void other(){
        
        // 统计对象数量
        long count = userRepository.count();
        
        // 判断对象是否存在
        boolean exists = userRepository.exists(1L);
    }
    
}
