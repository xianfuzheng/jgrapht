package com.zheng.traversal.dfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Post Order
 */
public class DFSIterativePostOrder {

    private static Map<String, GraphNodeInfo> nodeInfoMap = new HashMap<>();

    public static void main(String [] args) {

        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");

        for (String vertex : g1.vertexSet()) {
            nodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        // starting node
        for (String vertex : g1.vertexSet()) {
            if(nodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                System.out.println("---------------DFS starts from " + vertex+"----------------");
                dfs(g1, vertex);
            }
        }

        printResult();
    }

    private static void dfs(Graph<String, CustomGraphEdge> g1, String vertex) {

        Stack<String> stack = new Stack<>();
        stack.push(vertex);
        changeColor(vertex, GraphNodeColor.GREY);

        boolean hasNeighbour;

        while(!stack.empty()) {
            String current = stack.peek();
            hasNeighbour = false;

            for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(current)) {
                String dNode = customGraphEdge.getTargetVertex();
                if(nodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                    hasNeighbour = true;
                    nodeInfoMap.get(dNode).prevNode = current;
                    current = dNode;
                    stack.push(dNode);
                    changeColor(dNode, GraphNodeColor.GREY);
                    break;
                }
            }

            if(!hasNeighbour) {
                changeColor(current, GraphNodeColor.BLACK);
                stack.pop();
                System.out.println("----visit" + current);
            }

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
