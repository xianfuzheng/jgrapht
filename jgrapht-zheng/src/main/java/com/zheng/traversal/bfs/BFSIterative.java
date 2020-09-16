package com.zheng.traversal.bfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class BFSIterative {
    private static Map<String, GraphNodeInfo> nodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");

        // init
        for (String vertex : g1.vertexSet()) {
            nodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        for (String vertex : g1.vertexSet()) {
            if(nodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                changeColor(vertex, GraphNodeColor.GREY);
                bfsIterative(g1, vertex);
            }
        }

        printResult();
    }

    public static void bfsIterative(Graph<String, CustomGraphEdge> g, String vertex) {

        Queue<String> queue = new ArrayDeque<>();
        queue.offer(vertex);

        while(!queue.isEmpty()) {
            String elem = queue.poll();
            System.out.println( "visiting..." + elem);

            for (CustomGraphEdge edge : g.outgoingEdgesOf(elem)) {
                String targetVertex = edge.getTargetVertex();
                nodeInfoMap.get(elem).degree ++;

                if(nodeInfoMap.get(targetVertex).color == GraphNodeColor.WHITE) {
                    changeColor(targetVertex, GraphNodeColor.GREY);
                    queue.offer(targetVertex);
                    nodeInfoMap.get(targetVertex).prevNode  = elem;
                }
            }
            changeColor(elem, GraphNodeColor.BLACK);
        }
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
