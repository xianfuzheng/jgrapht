package com.zheng.topologicsorting;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class DFSIterative {

    public static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static int timer = 1;

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p355");

        for (String vertex : g1.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        Stack<String> sortedStack = new Stack<>();

        Stack<String> stack = new Stack<>();

        for (String vertex : g1.vertexSet()) {
            if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                changeColor(vertex, GraphNodeColor.GREY);
                stack.push(vertex);

                while(!stack.empty()) {
                    String elem = stack.peek();

                    boolean hasAllNeighbourVisited = true;

                    for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(elem)) {
                        String dNode = customGraphEdge.getTargetVertex();
                        if(graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                            hasAllNeighbourVisited = false;
                            changeColor(dNode, GraphNodeColor.GREY);
                            stack.push(dNode);
                        }
                    }
                    if(hasAllNeighbourVisited) {
                        changeColor(elem, GraphNodeColor.BLACK);
                        stack.pop();
                        sortedStack.push(elem);
                    }
                }
            }
        }

        List<GraphNodeInfo> graphNodeInfoList = new ArrayList<>(graphNodeInfoMap.values());
        graphNodeInfoList.sort((o1, o2) -> o2.f - o1.f);

        System.out.println(graphNodeInfoList);
    }


    public static void changeColor(String vertex, GraphNodeColor color) {
        System.out.println("change color for " + vertex +" to " + color);
        graphNodeInfoMap.get(vertex).color = color;
        if(color == GraphNodeColor.BLACK) {
            graphNodeInfoMap.get(vertex).f = timer ++;
        }

        if(color == GraphNodeColor.GREY) {
            graphNodeInfoMap.get(vertex).d = timer ++;
        }
    }
}
