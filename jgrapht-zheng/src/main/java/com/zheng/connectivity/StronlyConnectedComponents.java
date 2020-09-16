package com.zheng.connectivity;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class StronlyConnectedComponents {

    public static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();
    public static void main(String [] args) {

        solve("book_p357");
        solve("geeksforgeeks_strongly-connected-components");

    }

    private static void solve(String graphName) {
        System.out.println();
        System.out.println("Solving " + graphName);
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph(graphName);
        for (String vertex : g1.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        //dfs -> sets
        List<Set<String>> setList = new ArrayList<>();

        for (String vertex : g1.vertexSet()) {
            if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                GraphUtils.changeColor(vertex, GraphNodeColor.GREY, graphNodeInfoMap);
                Set<String> connectedVerticesSet = new HashSet<>();
                connectedVerticesSet.add(vertex);
                dfs(g1, vertex, connectedVerticesSet);
                setList.add(connectedVerticesSet);
            }
        }
        // reverse graph - use incoming edges
        for (String vertex : g1.vertexSet()) {
            graphNodeInfoMap.get(vertex).color = GraphNodeColor.WHITE;
        }

        for (Set<String> vertexSet : setList) {

            for (String vertex : vertexSet) {
                if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                    GraphUtils.changeColor(vertex, GraphNodeColor.GREY, graphNodeInfoMap);
                    Set<String> set = new HashSet<>();
                    set.add(vertex);
                    dfs2(g1, vertex, set, vertexSet);
                    String join = String.join("->", set);
                    System.out.println("Strongly connected:" + join);
                }
            }

        }
    }

    private static void dfs(Graph<String, CustomGraphEdge> g1, String vertex, Set<String> connectedVerticesSet) {
        for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(vertex)) {
            String dNode = customGraphEdge.getTargetVertex();
            if(graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                GraphUtils.changeColor(dNode, GraphNodeColor.GREY, graphNodeInfoMap);
                connectedVerticesSet.add(dNode);
                dfs(g1, dNode, connectedVerticesSet);
            }
        }
    }

    private static void dfs2(Graph<String, CustomGraphEdge> g1, String vertex, Set<String> stronglyConnectedSet, Set<String> connectedSet) {
        for (CustomGraphEdge customGraphEdge : g1.incomingEdgesOf(vertex)) {
            String dNode = customGraphEdge.getSourceVertex();
            if(connectedSet.contains(dNode) &&
                    graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                GraphUtils.changeColor(dNode, GraphNodeColor.GREY, graphNodeInfoMap);
                stronglyConnectedSet.add(dNode);
                dfs2(g1, dNode, stronglyConnectedSet,connectedSet);
            }
        }
    }
}
