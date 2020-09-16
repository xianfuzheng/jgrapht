package com.zheng.traversal.dfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.HashMap;
import java.util.Map;

public class DFSRecursive {

    private static Map<String, GraphNodeInfo> nodeInfoMap = new HashMap<>();
    private static int time = 1;

    public static void main(String[] args) {

        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p351");

        for (String vertex : g1.vertexSet()) {
            nodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        for (String vertex : g1.vertexSet()) {
            if(nodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                changeColor(vertex, GraphNodeColor.GREY);
                nodeInfoMap.get(vertex).d = time ++;
                dfs(g1, vertex);
            }
        }

        printResult();
    }

    private static void dfs(Graph<String, CustomGraphEdge> g1, String vertex) {

        System.out.println("visiting " + vertex);

        for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(vertex)) {
            nodeInfoMap.get(vertex).degree ++;
            String targetVertex = customGraphEdge.getTargetVertex();
            if(nodeInfoMap.get(targetVertex).color == GraphNodeColor.WHITE) {
                changeColor(targetVertex, GraphNodeColor.GREY);
                nodeInfoMap.get(targetVertex).d = time ++;
                nodeInfoMap.get(targetVertex).prevNode = vertex;
                dfs(g1, targetVertex);
            }
        }

        changeColor(vertex, GraphNodeColor.BLACK);
        nodeInfoMap.get(vertex).f = time ++;
    }

    public static void changeColor(String node, GraphNodeColor color) {
        System.out.println("Change " +node+ " from " + nodeInfoMap.get(node).color + " to " + color);
        nodeInfoMap.get(node).color = color;
    }

    public static void printResult() {
        for (String vertex : nodeInfoMap.keySet()) {
            GraphNodeInfo graphNodeInfo = nodeInfoMap.get(vertex);
            System.out.println(vertex + ":");
            System.out.println(graphNodeInfo);
        }
    }

}
