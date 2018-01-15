package com.sauzny.guava.caches;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;


/**
 * *************************************************************************
 * 
 * @文件名称: GuavaCache.java
 *
 * @包路径 : com.sauzny.jkitchen_note.guava.caches
 *
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述: 缓存
 * 
 * @创建人: ljx
 *
 * @创建时间: 2017年7月26日 - 下午2:36:23
 *
 **************************************************************************
 */
public class GuavaCache {

    
    public LoadingCache<String, Student> init01() {
        
        // 同步监听器
        RemovalListener<String, Student> studentRemovalListener = new StudentRemovalListener();
        // 异步监听器
        RemovalListener<String, Student> studentAsyncRemovalListener = RemovalListeners.asynchronous(studentRemovalListener, Executors.newFixedThreadPool(4));
        
        LoadingCache<String, Student> studentCaches = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置写缓存后8秒钟过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                // 设置总的权重值 不能与maximumSize同时使用
                //.maximumWeight(100000)
                //设置没一项的权重值，权重值越大，越先被回收 不能与maximumSize同时使用
                /*
                .weigher(new Weigher<String, Student>() {
                    public int weigh(String k, Student g) {
                      return g.vertices().size();
                    }
                  })
                  */
                //设置要统计缓存的命中率
                .recordStats()  
                // 缓存被移除时，触发监听器
                //.removalListener(studentRemovalListener)
                .removalListener(studentAsyncRemovalListener)
                // 加载器
                .build(new StudentCacheLoader());
        
        return studentCaches;
    }   
    
    // 在构建缓存的时候不使用加载器，在get的时候使用Callable实现自动加载数据
    public Cache<String, Student> init02() {
        
        // 同步监听器
        RemovalListener<String, Student> studentRemovalListener = new StudentRemovalListener();
        // 异步监听器
        RemovalListener<String, Student> studentAsyncRemovalListener = RemovalListeners.asynchronous(studentRemovalListener, Executors.newFixedThreadPool(4));
        
        Cache<String, Student> studentCaches = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置写缓存后8秒钟过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                // 设置总的权重值
                .maximumWeight(100000)
                //设置没一项的权重值，权重值越大，越先被回收
                .weigher(new Weigher<String, Student>() {
                    public int weigh(String k, Student g) {
                      return g.vertices().size();
                    }
                  })
                //设置要统计缓存的命中率
                .recordStats()  
                // 缓存被移除时，触发监听器
                //.removalListener(studentRemovalListener)
                .removalListener(studentAsyncRemovalListener)
                // 不使用加载器
                .build();
        
        return studentCaches;
    }
    
    
    public void setCache(LoadingCache<String, Student> studentCaches) throws Exception{
        
        for(int i=0; i<20; i++){
            Thread.sleep(1200);
            String temp = ""+(i+1);
            studentCaches.put(temp, new Student(){{this.setId(temp);this.setName("name"+temp);}});
        }
        
    }
    
    public void getCache(LoadingCache<String, Student> studentCaches) throws ExecutionException{
        for(int i=0; i<20; i++){
            String temp = ""+(i+1);
            System.out.println(studentCaches.get(temp));
        }
        
    }
    
    // 在构建缓存的时候不使用加载器，在get的时候使用Callable实现自动加载数据
    public void getCache02(Cache<String, Student> studentCaches) throws ExecutionException{
        for(int i=0; i<20; i++){
            String temp = ""+(i+1);
            Student student = studentCaches.get(temp, new Callable<Student>() {
                @Override
                public Student call() throws Exception {
                    return getStudentFromDB02(temp);
                }
            });
            System.out.println(student);
        }
        
    }
    
    public Student getStudentFromDB02(String id){
        long currTime = System.currentTimeMillis();
        Student student = new Student();
        student.setId(id + " from DB02 - " + currTime);
        student.setName("name from DB02 - " + currTime);
        return student;
    }
    
    @Test
    public void foo01(){
        try {
            LoadingCache<String, Student> studentCaches = this.init01();
            this.setCache(studentCaches);
            this.getCache(studentCaches);
            
            CacheStats cacheStats = studentCaches.stats();
            System.out.println(cacheStats);
            
            Map<String, Student> map = studentCaches.asMap();
            
            //重置缓存项的读取时间。
            //map.get(key);
            //map.put(key, value);
            
            // 不会重置缓存项的读取时间。
            for(Map.Entry<String, Student> entry : map.entrySet()){
                entry.getKey();
                entry.getValue();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 移除数据时的监听器
class StudentRemovalListener implements RemovalListener<String, Student>{

    @Override
    public void onRemoval(RemovalNotification<String, Student> notification) {
        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());        
    }
    
}

// 加载器
class StudentCacheLoader extends CacheLoader<String, Student> {

    // 加载数据
    @Override
    public Student load(String id) throws Exception {
        return this.getStudentFromDB(id);
    }
    
    // 刷新数据
    public ListenableFuture<Student> reload(String key, Student oldValue) throws Exception {
        checkNotNull(key);
        checkNotNull(oldValue);
        //如果不需要刷新，仍然使用就的值
        //return Futures.immediateFuture(load(key));
        
        // 如果需要刷新，异步加载新的值
        ListenableFutureTask<Student> task = ListenableFutureTask.create(new Callable<Student>() {
            public Student call() {
                return getStudentFromDB(key);
            }
        });
        // 自定义一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(task);
        return task;
      }
    
    public Student getStudentFromDB(String id){
        long currTime = System.currentTimeMillis();
        Student student = new Student();
        student.setId(id + " from DB - " + currTime);
        student.setName("name from DB - " + currTime);
        return student;
    }
}

class Student {
    
    private String id;
    private String name;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + "]";
    }
    
    // 权重回收时使用，计算每一项的权重，这里是字符串越长，权重越大
    public List<String> vertices(){
        List<String> vertices = Lists.newArrayList();
        for(char c : this.getId().toCharArray()){
            vertices.add(String.valueOf(c));
        }
        for(char c : this.getName().toCharArray()){
            vertices.add(String.valueOf(c));
        }
        return vertices;
    }
}