package com.sauzny.guava.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

public class AdjCelSolve {
    private MutableGraph<String> graph;

    public AdjCelSolve(MutableGraph<String> graph) {
        this.graph = graph;
    }

    public static void main(String[] args) {
        MutableGraph<String> graph = buildGraph();
        AdjCelSolve adjCelSolve = new AdjCelSolve(graph);

        System.out.println(adjCelSolve.adjacentNodes("A", 1));
        System.out.println(adjCelSolve.adjacentNodes("A", 2));
        System.out.println(adjCelSolve.adjacentNodes("A", 3));
        System.out.println(adjCelSolve.adjacentNodes("A", 4));


        /*
        output:
        [B, C, D]
        [B, D, E]
        [D, E]
        [E]
         */
    }

    private Set<String> adjacentNodes(String sourceNode, int dimension) {
        // 相邻节点
        Set<String> adjacentNodes = new HashSet<>();
        adjacentNodes.add(sourceNode);
        for (int i = 0; i < dimension; i++) {
            Set<String> currentDimensionNodes = new HashSet<>(adjacentNodes);
            adjacentNodes.clear();
            for (String adjacentNode : currentDimensionNodes) {
                adjacentNodes.addAll(graph.nodes().stream().filter(
                        node -> graph.successors(adjacentNode).contains(node)).collect(Collectors.toSet()));
            }
        }

        return adjacentNodes;
    }

    private static MutableGraph<String> buildGraph() {
        MutableGraph<String> graph = GraphBuilder.directed()
                .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(false).build();

        graph.putEdge("A", "B");
        graph.putEdge("A", "C");
        graph.putEdge("A", "D");
        graph.putEdge("B", "D");
        graph.putEdge("C", "B");
        graph.putEdge("C", "E");
        graph.putEdge("D", "E");

        return graph;
    }
}
