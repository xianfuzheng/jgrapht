package com.zheng.topologicsorting;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * DFS
 * Sort on finish time decreasing order
 *
 */
public class DFSRecursive {

    public static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();
    public static Stack<String> stack = new Stack<>();
    public static int timer = 1;

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p355");

        for (String vertex : g1.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        for (String vertex : g1.vertexSet()) {
            if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                changeColor(vertex,GraphNodeColor.GREY);
                dfs(g1, vertex);
            }
        }

        printResult();

    }

    private static void printResult() {
        System.out.println("Stack content:");
        while(!stack.empty()) {
            String pop = stack.pop();
            System.out.println(pop + "->" + graphNodeInfoMap.get(pop));
        }
    }

    public static void dfs(Graph<String, CustomGraphEdge> g, String startVertex) {

        for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(startVertex)) {
            String dNode = customGraphEdge.getTargetVertex();

            if(graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                changeColor(dNode, GraphNodeColor.GREY);
                dfs(g, dNode);
            }
        }

        changeColor(startVertex, GraphNodeColor.BLACK);

    }

    public static void changeColor(String vertex, GraphNodeColor color) {
        System.out.println("change color for " + vertex +" to " + color);
        graphNodeInfoMap.get(vertex).color = color;

        if(color == GraphNodeColor.GREY) {
            graphNodeInfoMap.get(vertex).d = timer ++;
        }

        if(color == GraphNodeColor.BLACK) {
            graphNodeInfoMap.get(vertex).f = timer ++;
            stack.push(vertex);
        }
    }
}
