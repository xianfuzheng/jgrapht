package com.zheng.traversal.bfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import org.jgrapht.Graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class BFSRecursive2 {

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");

        Queue<String> queue = new ArrayDeque<String>();
        Set<String> visited = new HashSet();

        for (String vertex : g1.vertexSet()) {
            if(!visited.contains(vertex)) {
                visited.add(vertex);
                queue.offer(vertex);
                bfsRecursive(g1, queue, visited);
            }
        }
    }
    public static void bfsRecursive(Graph g, Queue<String> queue, Set<String> visited) {

        System.out.println("bfsRecursive2-----");
        if(queue.isEmpty()) {
            return;
        }

        int size = queue.size();
        while(size>0) {
            size --;
            String elem = queue.poll();
            System.out.println(elem);

            Set<CustomGraphEdge> edges = g.edgesOf(elem);
            for(CustomGraphEdge edge : edges) {
                String sNode = edge.getSourceVertex();
                String dNode = edge.getTargetVertex();
                String node = sNode.equals(elem)? dNode: sNode;

                if(!visited.contains(node)) {
                    queue.add(node);
                    visited.add(node);
                }
            }
        }

        bfsRecursive(g, queue, visited);
    }
}
