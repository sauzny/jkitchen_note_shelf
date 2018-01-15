package com.sauzny.guava.graph;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.HashSet;
import java.util.Set;

public class DijkstraSolve {

    private final String sourceNode;
    private final MutableValueGraph<String, Integer> graph;

    public DijkstraSolve(String sourceNode, MutableValueGraph<String, Integer> graph) {
        this.sourceNode = sourceNode;
        this.graph = graph;
    }

    public static void main(String[] args) {
        MutableValueGraph<String, Integer> graph = buildGraph();
        DijkstraSolve dijkstraSolve = new DijkstraSolve("A", graph);

        dijkstraSolve.dijkstra();
        dijkstraSolve.printResult();
    }

    private void dijkstra() {
        initPathFromSourceNode(sourceNode);
        Set<String> nodes = graph.nodes();
        if(!nodes.contains(sourceNode)) {
            throw new IllegalArgumentException(sourceNode +  " is not in this graph!");
        }

        Set<String> notVisitedNodes = new HashSet<>(graph.nodes());
        String currentVisitNode = sourceNode;
        while(!notVisitedNodes.isEmpty()) {
            String nextVisitNode = findNextNode(currentVisitNode, notVisitedNodes);
            if(nextVisitNode.equals("")) {
                break;
            }
            notVisitedNodes.remove(currentVisitNode);
            currentVisitNode = nextVisitNode;
        }
    }

    private String findNextNode(String currentVisitNode, Set<String> notVisitedNodes) {
        int shortestPath = Integer.MAX_VALUE;
        String nextVisitNode = "";

        for (String node : graph.nodes()) {
            if(currentVisitNode.equals(node) || !notVisitedNodes.contains(node)) {
                continue;
            }

            if(graph.successors(currentVisitNode).contains(node)) {
                Integer edgeValue = graph.edgeValue(sourceNode, currentVisitNode).get() + graph.edgeValue(currentVisitNode, node).get();
                Integer currentPathValue = graph.edgeValue(sourceNode, node).get();
                if(edgeValue > 0) {
                    graph.putEdgeValue(sourceNode, node, Math.min(edgeValue, currentPathValue));
                }
            }

            if(graph.edgeValue(sourceNode, node).get() < shortestPath) {
                shortestPath = graph.edgeValue(sourceNode, node).get();
                nextVisitNode = node;
            }
        }

        return nextVisitNode;
    }

    private void initPathFromSourceNode(String sourceNode) {
        graph.nodes().stream().filter(
                node -> !graph.adjacentNodes(sourceNode).contains(node))
                .forEach(node -> graph.putEdgeValue(sourceNode, node, Integer.MAX_VALUE));
        graph.putEdgeValue(sourceNode, sourceNode, 0);
    }

    private void printResult() {
        for (String node : graph.nodes()) {
            System.out.println(sourceNode + "->" + node + " shortest path is:" + graph.edgeValue(sourceNode, node));
        }
    }

    private static MutableValueGraph<String, Integer> buildGraph() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                                        .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(true).build();

        graph.putEdgeValue("A", "B", 10);
        graph.putEdgeValue("A", "C", 3);
        graph.putEdgeValue("A", "D", 20);
        graph.putEdgeValue("B", "D", 5);
        graph.putEdgeValue("C", "B", 2);
        graph.putEdgeValue("C", "E", 15);
        graph.putEdgeValue("D", "E", 11);
        
        return graph;
    }
}
