package com.sauzny.guava.collections;

import java.awt.Color;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

/**
 * *************************************************************************
 * @文件名称: ImmutableCollections.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.collections 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  不可变集合
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年7月26日 - 下午1:25:14 
 *	
 **************************************************************************
 */
public class ImmutableCollections {

	public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
			  "red",
			  "orange",
			  "yellow",
			  "green",
			  "blue",
			  "purple");
	
	ImmutableSet<Bar> bars = null;
	
	public void foo01(Set<Bar> bars) {
	    this.bars = ImmutableSet.copyOf(bars); // defensive copy!
		System.out.println(bars);
	}
	
	@Test
	public void foo02(){
		ImmutableSet<String> bars = ImmutableSet.of("a", "b", "c", "a", "d", "b");
		System.out.println(bars);
	}
	
	public static final ImmutableList<Color> WEBSAFE_COLORS = ImmutableList.of(Color.BLACK, Color.WHITE);
	
	public static final ImmutableSet<Color> GOOGLE_COLORS =
		       ImmutableSet.<Color>builder()
		           .addAll(WEBSAFE_COLORS)
		           .add(new Color(0, 191, 255))
		           .build();
	
	@Test
	public void foo03(){
		System.out.println(GOOGLE_COLORS);
	}
	
	ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");

	public static void thingamajig(Collection<String> collection) {
	   ImmutableList<String> defensiveCopy = ImmutableList.copyOf(collection);
	   System.out.println(defensiveCopy);
	}
	
	@Test
	public void foo04(){
		thingamajig(foobar);
	}
	
	public static final ImmutableList<Integer> imlist = ImmutableList.of(78, 75, 400, 5, 68, 19);
	
	@Test
	public void foo05(){
		ImmutableSortedSet<Integer> defensiveCopy = ImmutableSortedSet.copyOf(imlist);
		System.out.println(defensiveCopy);
		// 获取第三小的数字
		System.out.println(defensiveCopy.asList().get(2));
	}
}

class Bar {
	
}
