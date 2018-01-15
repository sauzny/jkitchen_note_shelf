package com.sauzny.guava.graph;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

public class GraphTools {
    
    // 一个有向图中的目标点，关联的线段集合
    public static List<List<String>> edgeList(MutableGraph<String> graph, Set<String> target){
        
        List<List<String>> result = Lists.newArrayList();
        
        // 所有的直线线路
        List<List<String>> straightLine = straightLine(graph);
        
        // 行key， 列key，值
        Table<String, String, ValueLine> table = HashBasedTable.create();

        // 行：目标点 
        // 列：所有点
        // 值：距离+路线
        // 填充 table
        for(String t : target){
            for(String n : graph.nodes()){
                for(List<String> line : straightLine){
                    ValueLine valueLine = new ValueLine();
                    if(t.equals(n)){
                        valueLine.setValue(0);
                    }else if(line.contains(t) && line.contains(n)){
                        // 目标点所在index
                        int tindex = line.indexOf(t);
                        // 
                        int nindex = line.indexOf(n);
                        
                        if(tindex < nindex){
                            valueLine.setValue(nindex - tindex);
                            valueLine.setLine(line.subList(tindex, nindex + 1));
                        }else{
                            valueLine.setValue(tindex - nindex);
                            valueLine.setLine(line.subList(nindex, tindex + 1));
                        }
                    }else{
                        valueLine.setValue(-1);
                    }
                    
                    if(table.get(t, n) != null){
                        if(table.get(t, n).getValue() == -1){
                            table.put(t, n , valueLine);
                        }else if(valueLine.getValue() > 0 && table.get(t, n).getValue() > valueLine.getValue()){
                            table.put(t, n , valueLine);
                        }
                    }else{
                        table.put(t, n , valueLine);
                    }
                }
            }
        }
        
        
        // 打印 table
        //System.out.println(table.cellSet());
        
        // 公共点 到 每个目标点的 距离之和
        Map<String, Integer> map = Maps.newHashMap();
        
        for(String n : table.columnKeySet()){
            int totalValue = 0;
            for(String t: table.rowKeySet()){
                int value = table.get(t, n).getValue();
                if(value == -1){
                    totalValue = -1;
                    break;
                }else{
                    totalValue += value;
                }
            }
            if(totalValue > 0){
                map.put(n, totalValue);
            }
        }
        
        //System.out.println(map);
        
        // 路径最短的公共点
        String shortestNode = "";
        Integer shortestValue = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if(shortestValue == 0){
                shortestNode = entry.getKey();
                shortestValue = entry.getValue();
            }else if(shortestValue > entry.getValue()){
                shortestNode = entry.getKey();
                shortestValue = entry.getValue();
            }
        }
        
        if(StringUtils.isNotBlank(shortestNode)){
            for(String t : table.rowKeySet()){
                List<String> line = table.get(t, shortestNode).getLine();
                if(line.size() == 0){
                    continue;
                }
                if(line.size() >2){
                    for(int i=0;i<line.size()-1;i++){
                        result.add(line.subList(i, i+1));
                    }
                }else{
                    result.add(line);
                }
            }
        }
        
        return result;
    }
    
    // 直线
    public static List<List<String>> straightLine(MutableGraph<String> graph){
        
        List<List<String>> result = Lists.newArrayList();
        
        // 获取 所有的点，作为直线的起点
        for(String node : graph.nodes()){
            List<String> list = Lists.newArrayList();
            list.add(node);
            result.add(list);
        }
        
        // 获取直线，并将直线放入result中
        straightLine(graph, result);
        
        // 去除 重复直线 元素
        Set<List<String>> temp = Sets.newHashSet(result);
        result.clear();      
        result.addAll(temp);

        // 去除 直线中只有一个点 的 元素
        for(Iterator<List<String>> iterator = result.iterator();iterator.hasNext();){
            if(iterator.next().size() == 1){
                iterator.remove();
            }
        }
        // 打印直线
        //System.out.println(result.size());
        //result.forEach(System.out::println);
        
        return result;
    }
    
    public static void straightLine(MutableGraph<String> graph, List<List<String>> result){
        
        // 确认 result 中哪些线 需要移除
        List<List<String>> needRemove = Lists.newArrayList();
        
        for(int i=0;i<result.size();i++){
            Set<String> next = graph.successors(result.get(i).get(result.get(i).size()-1));
            if(next.size() > 0) needRemove.add(result.get(i));
        }
        
        for(List<String> remove : needRemove){
            // 将需要移除的线 暂时存在临时变量中
            List<String> temp = Lists.newArrayList();
            temp.addAll(remove);
            
            // 移除
            result.remove(remove);
            
            // 将新的结果加入result中
            for(String b : graph.successors(temp.get(temp.size()-1))){
                List<String> newList = Lists.newArrayList();
                newList.addAll(temp);
                newList.add(b);
                result.add(newList);
                straightLine(graph, result);
            }
        }
        
    }

    public static void main(String[] args) {

        // directed 定向
        // undirected 五定向
        MutableGraph<String> graph = GraphBuilder.directed()
                .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(false).build();
        
        // 制作图
        
        graph.putEdge("A", "B");
        graph.putEdge("A", "C"); 
        graph.putEdge("A", "D");
        graph.putEdge("C", "B");
        graph.putEdge("C", "E");
        graph.putEdge("D", "E");
//        graph.putEdge("B", "D");

        Set<String> target = Sets.newHashSet();
        target.add("B");
        target.add("E");
        
        List<List<String>> result = GraphTools.edgeList(graph, target);
        
        result.forEach(System.out::println);
    }
}

class ValueLine{
    
    private Integer value = 0;
    private List<String> line = Lists.newArrayList();
    
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public List<String> getLine() {
        return line;
    }
    public void setLine(List<String> line) {
        this.line = line;
    }
    
    @Override
    public String toString() {
        return "ValueLine [value=" + value + ", line=" + line + "]";
    }
}