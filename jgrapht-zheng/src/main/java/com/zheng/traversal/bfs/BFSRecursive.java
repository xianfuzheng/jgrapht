package com.zheng.traversal.bfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import org.jgrapht.Graph;

import java.util.*;

/**
 * Recursive:
 *  each call only visit one node
 */
public class BFSRecursive {

    private static Map<String, GraphNodeColor> nodeColorMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");

        Queue<String> queue = new ArrayDeque<String>();

        for (String vertex : g1.vertexSet()) {
            if (nodeColorMap.get(vertex) == null) {
                nodeColorMap.put(vertex, GraphNodeColor.GREY);
                queue.offer(vertex);
                bfsRecursive(g1, queue);
            }
        }
    }

    public static void bfsRecursive(Graph g, Queue<String> queue) {

        System.out.println("bfsRecursive-----");
        if(queue.isEmpty()) {
            return;
        }

        String elem = queue.poll();
        System.out.println(elem);

        Set<CustomGraphEdge> edges = g.outgoingEdgesOf(elem);
        for(CustomGraphEdge edge : edges) {
            String dNode = edge.getTargetVertex();

            if(nodeColorMap.get(dNode) == null) {
                queue.add(dNode);
                nodeColorMap.put(dNode, GraphNodeColor.GREY);
            } else {
                //cycle
            }
        }

        bfsRecursive(g, queue);
    }
}
