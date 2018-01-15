package com.sauzny.guava.collections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeMultimap;
import com.google.common.primitives.Ints;
import com.sauzny.guava.Print;

public class CollectionUtilities {

    /**
     * @描述: 静态构造器
     * @返回 void
     * @创建人  ljx 创建时间 2017年12月26日 上午9:15:50
     */
    @SuppressWarnings("unused")
    @Test
    public void testStaticConstructors(){
        // 无参 构造函数
        List<ValueType> list = Lists.newArrayList();
        Map<KeyType, ValueType> map = Maps.newLinkedHashMap();
        // 有参 构造函数
        Set<ValueType> copySet = Sets.newHashSet(list);
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
        
        // 设置初始化大小
        List<Integer> exactly100 = Lists.newArrayListWithCapacity(100);
            // 这个方法 在 api note中说快过期了
        List<Integer> approx100 = Lists.newArrayListWithExpectedSize(100);
        Set<ValueType> approx100Set = Sets.newHashSetWithExpectedSize(100);
        
        // guava 新集合
        Multiset<String> multiset = HashMultiset.create();
        
    }
    
    @Test
    public void testIterables() {
        
        // concatenated has elements 1, 2, 3, 4, 5, 6
        //串联多个iterables的懒视图
        Iterable<Integer> concatenated = Iterables.concat(Ints.asList(1, 2, 3), Ints.asList(4, 5, 6));
        Print.sysoutThree("Iterables.concat", "串联多个iterables的懒视图", concatenated);

        // 测试数据
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma","gamma", "bob", "alice");
        List<String> theseElements1 = Lists.newArrayList("alpha", "beta", "gamma","gamma", "bob", "alice");
        
        // 返回对象在iterable中出现的次数
        int gammaCount = Iterables.frequency(theseElements, "gamma");
        Print.sysoutThree("Iterables.frequency", "gamma在theseElements中出现过的次数", gammaCount);

        // 把iterable按指定大小分割，得到的子集都不能进行修改操作
        Iterable<List<String>> partitions = Iterables.partition(theseElements, 2);
        Print.sysoutThree("Iterables.partition", "theElement按照2进行分割", partitions);
        
        // 返回iterable的第一个元素，若iterable为空则返回默认值
        String first = Iterables.getFirst(theseElements, "");
        Print.sysoutThree("Iterables.getFirst", "theseElements中的第一个元素", first);
        
        // 返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException
        String lastAdded = Iterables.getLast(theseElements);
        Print.sysoutThree("Iterables.lastAdded", "theseElements中的最后一个元素", lastAdded);

        // 如果两个iterable中的所有元素相等且顺序一致，返回true
        boolean isEqual = Iterables.elementsEqual(theseElements, theseElements1);
        Print.sysoutThree("Iterables.elementsEqual", "比较两个集合", isEqual);
        
        // 返回iterable的不可变视图
        Iterable<String> theseElements2 = Iterables.unmodifiableIterable(theseElements);
        Print.sysoutThree("Iterables.unmodifiableIterable", "不可变视图", theseElements2);
        
        // 限制iterable的元素个数限制给定值
        Iterable<String> theseElements3 = Iterables.limit(theseElements, 3);
        Print.sysoutThree("Iterables.limit", "前3个元素", theseElements3);
        
        // 获取iterable中唯一的元素，如果iterable为空或有多个元素，则快速失败
        Integer theElement = Iterables.getOnlyElement(Ints.asList(1));
        Print.sysoutThree("Iterables.theElement", "theElement中的唯一一个元素", theElement);
    }
    
    @SuppressWarnings("unused")
    @Test
    public void testLists(){
        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        List<Integer> countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}
        List<List<Integer>> parts = Lists.partition(countUp, 2);//{{1,2}, {3,4}, {5}}
    }
    
    @Test
    public void testSets(){
        
        Set<String> s1 = Sets.newHashSet("a","b","c","d");
        Set<String> s2 = Sets.newHashSet("d","e","f","g");
        
        Print.sysoutThree("Sets.union", "并集",Sets.union(s1, s2));
        Print.sysoutThree("Sets.intersection", "交集",Sets.intersection(s1, s2));
        Print.sysoutThree("Sets.difference", "s1对s2的差集",Sets.difference(s1, s2));
        Print.sysoutThree("Sets.symmetricDifference", "互相做差集的并集",Sets.symmetricDifference(s1, s2));
    
        
        // 不可变集合的效率更高
        Set<String> animals = ImmutableSet.of("gerbil", "hamster");
        Set<String> fruits = ImmutableSet.of("apple", "orange", "banana");

        // 返回结果的类型是 Sets$CartesianSet
        //返回所有集合的笛卡儿积
        // {{"gerbil", "apple"}, {"gerbil", "orange"}, {"gerbil", "banana"},
        //  {"hamster", "apple"}, {"hamster", "orange"}, {"hamster", "banana"}}
        Set<List<String>> product = Sets.cartesianProduct(animals, fruits);
        Print.sysoutThree("Sets.cartesianProduct", "所有集合的笛卡儿积", product);

        // 返回结果的类型是 Sets$PowerSet
        // 给定集合的所有子集
        // {{}, {"gerbil"}, {"hamster"}, {"gerbil", "hamster"}}
        Set<Set<String>> animalSets = Sets.powerSet(animals);
        Print.sysoutThree("Sets.powerSet", "给定集合的所有子集", animalSets);
    }
    
    @Test
    public void testMaps(){
        
        Student s1 = new Student(1,"Alice");
        Student s2 = new Student(2,"Bob");
        Student s3 = new Student(3,"李磊");
        Student s4 = new Student(4,"韩梅梅");
        
        List<Student> students = Lists.newArrayList(s1, s2, s3, s4);

        ImmutableMap<Integer, Student> studentByIndex = Maps.uniqueIndex(students, new Function<Student, Integer>() {
            public Integer apply(Student student) {
                return student.id;
            }
        });
        Print.sysoutThree("Maps.uniqueIndex", "集合->map", studentByIndex);
        
        // 也可以这样写
        ImmutableMap<Integer, Student> studentByIndex1 = Maps.uniqueIndex(students, student -> student.id);
        Print.sysoutThree("Maps.uniqueIndex", "集合->map", studentByIndex1);

        // 比较两个map的不同
        
        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        Print.sysoutThree("MapDifference.entriesInCommon", "key相同且value相同", diff.entriesInCommon()); // {"b" => 2}
        Print.sysoutThree("MapDifference.entriesDiffering", "key相同且value不同", diff.entriesDiffering()); // {"c" => (3, 4)}
        Print.sysoutThree("MapDifference.entriesOnlyOnLeft", "只在左侧有", diff.entriesOnlyOnLeft()); // {"a" => 1}
        Print.sysoutThree("MapDifference.entriesOnlyOnRight", "只在右侧有", diff.entriesOnlyOnRight()); // {"d" => 5}
        
    }
    
    @Test
    public void testMultiset(){
        
        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.add("a", 2);

        Multiset<String> multiset2 = HashMultiset.create();
        multiset2.add("a", 5);
        
        // returns true: all unique elements are contained,
        // even though multiset1.count("a") == 2 < multiset2.count("a") == 5
        Print.sysoutThree("multiset1.containsAll(multiset2)", "判断是否包含", multiset1.containsAll(multiset2)); 
        
        // removes all occurrences of "a" from multiset2, even though multiset1.count("a") == 2
        multiset2.removeAll(multiset1);
        Print.sysoutThree("multiset2.removeAll(multiset1)", "移除了元素之后的multiset2", multiset2); 
        
        // returns true
        Print.sysoutThree("multiset2.isEmpty()", "判断是否为空", multiset2.isEmpty());
        
        // 测试数据被清空了， 重新赋值
        multiset2.add("a", 5);
        
        // 对任意o，如果sub.count(o)<=super.count(o)，返回true
        Print.sysoutThree("Multisets.containsOccurrences", "包含，multiset1, multiset2", Multisets.containsOccurrences(multiset1, multiset2)); 
        Print.sysoutThree("Multisets.containsOccurrences", "包含，multiset2, multiset1", Multisets.containsOccurrences(multiset2, multiset1));
        
        Print.sysoutThree("Multisets.difference", "差集，multiset1, multiset2", Multisets.difference(multiset1, multiset2));
        Print.sysoutThree("Multisets.difference", "差集，multiset2, multiset1", Multisets.difference(multiset2, multiset1));
        
        Print.sysoutThree("Multisets.intersection", "交集，multiset1, multiset2", Multisets.intersection(multiset1, multiset2));
        Print.sysoutThree("Multisets.intersection", "交集，multiset2, multiset1", Multisets.intersection(multiset2, multiset1));
        
        // multiset2 now contains 3 occurrences of "a"
        Multisets.removeOccurrences(multiset2, multiset1);
        Print.sysoutThree("Multisets.removeOccurrences", "移除, multiset2, multiset1", multiset2); 
        
        // 测试数据被删除了一些， 重新补充
        multiset2.add("a", 2);

        Multisets.retainOccurrences(multiset2, multiset1);
        Print.sysoutThree("Multisets.retainOccurrences", "保留, multiset2, multiset1", multiset2); 
        
        
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a", 3);
        multiset.add("b", 5);
        multiset.add("c", 1);
        
        // highestCountFirst, like its entrySet and elementSet, iterates over the elements in order {"b", "a", "c"}

        ImmutableMultiset<String> highestCountFirst = Multisets.copyHighestCountFirst(multiset);
        Print.sysoutThree("Multisets.copyHighestCountFirst", "将元素按重复出现的次数做降序排列", highestCountFirst); 

    }
    
    @Test
    public void testMultimaps(){
        
        /*
         * digitsByLength maps:
         *  3 => {"one", "two", "six"}
         *  4 => {"zero", "four", "five", "nine"}
         *  5 => {"three", "seven", "eight"}
         */
        
        ImmutableSet<String> digits = ImmutableSet.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        ImmutableListMultimap<Integer, String> digitsByLength= Multimaps.index(digits, string -> string.length());
        
        Print.sysoutThree("Multimaps.index", "集合->Multimap", digitsByLength); 
        
        // note that we choose the implementation, so if we use a TreeMultimap, we get results in order
        /*
         * inverse maps:
         *  1 => {"a"}
         *  2 => {"a", "b", "c"}
         *  3 => {"c"}
         *  4 => {"a", "b"}
         *  5 => {"c"}
         *  6 => {"b"}
         */
        ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.putAll("b", Ints.asList(2, 4, 6));
        multimap.putAll("a", Ints.asList(4, 2, 1));
        multimap.putAll("c", Ints.asList(2, 5, 3));
        
        TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap , TreeMultimap.<Integer, String>create());
        
        Print.sysoutThree("Multimaps.invertFrom", "multimap->Multimap", inverse); 
        
        
        Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
        
        // multimap maps ["a" => {1}, "b" => {1}, "c" => {2}]
        SetMultimap<String, Integer> multimap1 = Multimaps.forMap(map);
        Print.sysoutThree("Multimaps.forMap", "map->Multimap", multimap1); 
        
        // inverse maps [1 => {"a", "b"}, 2 => {"c"}]
        Multimap<Integer, String> inverse1 = Multimaps.invertFrom(multimap, HashMultimap.<Integer, String> create());
        Print.sysoutThree("Multimaps.invertFrom", "multimap->Multimap", inverse1); 
        
        // 包装器
        ListMultimap<String, Integer> myMultimap =
                Multimaps.newListMultimap(Maps.<String, Collection<Integer>>newTreeMap(), new Supplier<LinkedList<Integer>>() {
                    public LinkedList<Integer> get() {
                        return Lists.newLinkedList();
                    }
                });
        
        // lambda表达式写法
        ListMultimap<String, Integer> myMultimap1 = Multimaps.newListMultimap(Maps.<String, Collection<Integer>>newTreeMap(), () -> Lists.newLinkedList());

    }
    
    @Test
    public void testTables(){
        // use LinkedHashMaps instead of HashMaps
        Table<String, Character, Integer> table =
                Tables.newCustomTable(Maps.<String, Map<Character, Integer>>newLinkedHashMap(), new Supplier<Map<Character, Integer>>() {
                    public Map<Character, Integer> get() {
                        return Maps.newLinkedHashMap();
                    }
                });

        // lambda表达式写法
        Table<String, Character, Integer> table1 =
                Tables.newCustomTable(Maps.<String, Map<Character, Integer>>newLinkedHashMap(), () -> Maps.newLinkedHashMap());
    
        // 测试数据
        // 行，排名，A B C D
        String ranking = "A";
        // 列，班级
        String className = "3-";
        // 值，学生名字
        String studentName = "";
        
        Table<String, String, String> tableTest = HashBasedTable.create();
        
        for(int i=0; i<4; i++){
            String ranking_ = "" + (char) ((int) ranking.toCharArray()[0] + i);
            for(int j=0;j<5;j++){
                String className_ = className + (j+1);
                studentName = ranking_ + className_.replace("-", "");
                tableTest.put(ranking_, className_ , studentName);
            }
        }
        

        System.out.println("测试数据cellSet :" + tableTest.cellSet());

        Print.sysoutThree("Tables.transpose", "行列转换", Tables.transpose(tableTest).cellSet()); 
    }
}

class KeyType{
    
}

class ValueType{
    
}

class Student{
    public int id;
    public String name;
    public Student(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + "]";
    }
}