package com.sauzny.guava.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

/**
 * *************************************************************************
 * @文件名称: NewCollections.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.collections 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  新集合
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年7月26日 - 下午2:02:29 
 *	
 **************************************************************************
 */
public class NewCollections {

	/**
	 * 	方法描述:  
	 *   
	 *   Multiset：
	    	没有元素顺序限制的ArrayList<E>
			Map<E, Integer>，键为元素，值为计数
		
		Multiset有多种实现
		
	 *  @author  ljx 创建时间 2017年7月26日 下午2:04:48
	 */
	@Test
	public void testMultiset(){
		
		List<String> list = Lists.newArrayList();
		list.add("a");
		list.add("b");
		list.add("c");
		
		Multiset<String> multiset = HashMultiset.create();
		multiset.add("a");
		multiset.addAll(list);
		multiset.add("b", 2);
		
		System.out.println("multiset长度：" + multiset.size());
		
		Iterator<String> iterator = multiset.iterator();
		while(iterator.hasNext()){
			System.out.print(iterator.next() + " | ");
		}
		System.out.println();
		
		
		System.out.println("b的次数：" + multiset.count("b"));
		System.out.println("elementSet：" + multiset.elementSet());
		

		System.out.println("获取其entrySet：" + multiset.entrySet());
		for(Multiset.Entry<String> entry : multiset.entrySet()){
			System.out.println("每一个元素输出：" + entry.getElement() + "  -  " + entry.getCount());
		}
		
	}
	
	
	/**
	 * 	方法描述:  
	 *   
	 *   Multimap 其数据结构为
	 *   a -> 1 a -> 2 a ->4 b -> 3 c -> 5
	 *   
	 *   但asMap()视图返回Map<K, Collection<V>>，可以理解为
	 *   a -> [1, 2, 4] b -> 3 c -> 5
	 *   
	 *   不会有任何键映射到空集合：一个键要么至少到一个值，要么根本就不在Multimap中。
	 *   
	 *  @author  ljx 创建时间 2017年7月27日 下午2:08:34
	 */
	@Test
	public void testMultimap(){
		
		ListMultimap<String, String> listMultimap = ArrayListMultimap.create();
		
		listMultimap.put("A", "郝老大");
		listMultimap.put("A", "钱小二");
		listMultimap.put("A", "张三");
		listMultimap.put("B", "李四");
		listMultimap.put("B", "王五");
		listMultimap.put("C", "赵六");
		listMultimap.put("C", "洪七公");
		listMultimap.put("D", "于八爷");
		listMultimap.put("D", "毛小九");
        listMultimap.put("E", "1");
        listMultimap.put("E", "2");
        listMultimap.put("E", "4");
        listMultimap.put("E", "3");
		
		//putAll(K, Iterable<V>)
		//remove(K, V)
		//removeAll(K)
		//replaceValues(K, Iterable<V>)
		
		Collection<String> aCollection = listMultimap.asMap().get("A");
		System.out.println("A类型：" + aCollection);
		
		System.out.println("entries：" + listMultimap.entries());
		System.out.println("keySet：" + listMultimap.keySet());
		System.out.println("values：" + listMultimap.values());
		
		//用Multiset表示Multimap中的所有键，每个键重复出现的次数等于它映射的值的个数。
		System.out.println("keys：" + listMultimap.keys());

		System.out.println("asMap().get('F')：" + listMultimap.asMap().get("F"));
		System.out.println("get('F')：" + listMultimap.get("F"));
        System.out.println("get('E')：" + listMultimap.get("E"));
        listMultimap.get("E").add("6");
        System.out.println("get('E')：" + listMultimap.get("E"));
		
	} 
	
	/**
	 * 	方法描述:  
	 *  	BiMap  双向map
	 *   
	 *  @author  ljx 创建时间 2017年7月27日 下午2:46:03
	 */
	/*
	Map<String, Integer> nameToId = Maps.newHashMap();
	Map<Integer, String> idToName = Maps.newHashMap();

	nameToId.put("Bob", 42);
	idToName.put(42, "Bob");
	*/
	// what happens if "Bob" or 42 are already present?
	// weird bugs can arise if we forget to keep these in sync...
	
	@Test
	public void testBiMap(){
		// 电话号 身份证号
		String phoneNum = "18512345678";
		String idNum = "123456788901234567";
		
		BiMap<String, String> phoneNum2IdNum = HashBiMap.create();
		phoneNum2IdNum.put(phoneNum, idNum);
		
		String idNum_ = phoneNum2IdNum.get(phoneNum);
		String phoneNum_ = phoneNum2IdNum.inverse().get(idNum);
		
		System.out.println("idNum_：" + idNum_);
		System.out.println("phoneNum_：" + phoneNum_);
		
		
		try{
			phoneNum2IdNum.put("phoneNum", idNum);
		}catch(IllegalArgumentException e){
			System.out.println("键映射到已经存在的值，此时抛出异常（IllegalArgumentException）：" + e.getMessage());
			System.out.println("强制替换，使用方法：BiMap.forcePut(key, value)");
		}
	}
	
	/**
	 * 	方法描述:  
	 * 		Table 行，列，值
	 *		其数据结构为Table<R,C,V>
	 *		也可以理解为Map<R, Map<C, V>
	 *  @author  ljx 创建时间 2017年7月27日 下午2:57:47
	 */
	@Test
	public void testTable(){
		// 行，排名，A B C D
		String ranking = "A";
		// 列，班级
		String className = "3-";
		// 值，学生名字
		String studentName = "";
		
		Table<String, String, String> table = HashBasedTable.create();
		
		for(int i=0; i<4; i++){
			String ranking_ = "" + (char) ((int) ranking.toCharArray()[0] + i);
			for(int j=0;j<5;j++){
				String className_ = className + (j+1);
				studentName = ranking_ + className_.replace("-", "");
				table.put(ranking_, className_ , studentName);
			}
		}

		System.out.println("C | 3-3 :" + table.get("C", "3-3"));
		
		System.out.println("rowMap :" + table.rowMap());
		System.out.println("row :" + table.row("D"));
		System.out.println("cellSet :" + table.cellSet());
		
		for(Table.Cell<String, String, String> cell : table.cellSet()){
			cell.getRowKey();
			cell.getColumnKey();
			cell.getValue();
		}	
		
	}
	
	/**
	 * 	方法描述:  
	 * 
	 * 		它的键是类型，而值是符合键所指类型的对象。
	 *   	ClassToInstanceMap<B>实现了Map<Class<? extends B>, B>
	 *   
	 *  @author  ljx 创建时间 2017年7月27日 下午3:49:39
	 */
	@Test
	public void testClassToInstanceMap(){
		
		ClassToInstanceMap<Number> numberDefaults=MutableClassToInstanceMap.create();
		numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
		numberDefaults.getInstance(Integer.class);
	}
	
	/**
	 * 	方法描述:
	 *   
	 *   	RangeSet描述了一组不相连的、非空的区间。
	 *   
	 *   当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略。	
	 *   
	 *  @author  ljx 创建时间 2017年7月27日 下午3:54:36
	 */
	@Test
	public void testRangeSet(){
		RangeSet<Integer> rangeSet = TreeRangeSet.create();
		rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
		rangeSet.add(Range.closedOpen(11, 15)); // 不相连区间	: {[1, 10], [11, 15)}
		rangeSet.add(Range.closedOpen(15, 20)); // 相连区间	; {[1, 10], [11, 20)}
		rangeSet.add(Range.openClosed(0, 0)); // 空区间; {[1, 10], [11, 20)}
		rangeSet.remove(Range.open(5, 10)); // 分割 [1, 10]; {[1, 5], [10, 10], [11, 20)}

        System.out.println("当前rangeSet：" + rangeSet);
		System.out.println("补集complement：" + rangeSet.complement());
        System.out.println("交集subRangeSet：" + rangeSet.subRangeSet(Range.openClosed(3, 16)));
        System.out.println("set类型，asRanges：" + rangeSet.asRanges());
		
        //asSet(DiscreteDomain<C>)（仅ImmutableRangeSet支持）：
          //用ImmutableSortedSet<C>表现RangeSet，以区间中所有元素的形式而不是区间本身的形式查看。
            //（这个操作不支持DiscreteDomain 和RangeSet都没有上边界，或都没有下边界的情况）
        
        //RangeSet最基本的操作，判断RangeSet中是否有任何区间包含给定元素。
        System.out.println("contains：" + rangeSet.contains(14));
        //返回包含给定元素的区间；若没有这样的区间，则返回null。
        System.out.println("rangeContaining：" + rangeSet.rangeContaining(2));
        //简单明了，判断RangeSet中是否有任何区间包括给定区间。
        System.out.println("encloses：" + rangeSet.encloses(Range.open(13, 18)));
        //返回包括RangeSet中所有区间的最小区间。
        System.out.println("span：" + rangeSet.span());
	}
	
	/**
	 * 	方法描述:  
	 *   
	 *   RangeMap描述了”不相交的、非空的区间”到特定值的映射。
	 *   
	 *   和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值。
	 *   
	 *  @author  ljx 创建时间 2017年7月28日 上午11:26:18
	 */
	@Test
	public void testRangeMap(){
	    RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
	    rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
	    rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
	    rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
	    rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
	    

	    System.out.println("rangeMap:" + rangeMap);
	    
        System.out.println("asMapOfRanges：" + rangeMap.asMapOfRanges());
        
        for(Map.Entry<Range<Integer>, String> entry : rangeMap.asMapOfRanges().entrySet()){
            entry.getKey();
            entry.getValue();
        }
        
	    System.out.println("subRangeMap：" + rangeMap.subRangeMap(Range.open(14, 16)));
	}
}
