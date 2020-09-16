package com.zheng.traversal.bfs;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class BFSIterator {

    private final Graph<String, CustomGraphEdge> g;
    private final Queue<String> queue = new ArrayDeque<String>();
    private final Map<String, GraphNodeInfo> graphNodeMap = new HashMap<>();

    public BFSIterator(Graph<String, CustomGraphEdge> g) {
        this.g = g;
        for (String s : g.vertexSet()) {
            graphNodeMap.put(s, new GraphNodeInfo(s));
        }

        String startVertex = getUnVisitedVertex();
        graphNodeMap.get(startVertex).color = GraphNodeColor.GREY;

        queue.offer(startVertex);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public String next() {
        String elem = queue.poll();

        for (CustomGraphEdge customGraphEdge : g.edgesOf(elem)) {
            String targetVertex = customGraphEdge.getTargetVertex();
            if(targetVertex.equals(elem)) {
                targetVertex = customGraphEdge.getSourceVertex();
            }
            if(graphNodeMap.get(targetVertex).color == GraphNodeColor.WHITE) {
                graphNodeMap.get(targetVertex).color = GraphNodeColor.GREY;
                queue.offer(targetVertex);
            }
        }

        graphNodeMap.get(elem).color = GraphNodeColor.BLACK;

        // unvisited only queue is empty
        if(queue.isEmpty()) {
            String unVisitedVertex = getUnVisitedVertex();
            if(unVisitedVertex!=null) {
                graphNodeMap.get(unVisitedVertex).color = GraphNodeColor.GREY;
                queue.offer(unVisitedVertex);
            }
        }
        return elem;
    }

    private String getUnVisitedVertex() {
        for (String vertex : g.vertexSet()) {
            if(graphNodeMap.get(vertex).color == GraphNodeColor.WHITE)  {
                return vertex;
            }
        }
        return null;
    }

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");
        BFSIterator bfsIterator = new BFSIterator(g1);
        System.out.println("------");
        while (bfsIterator.hasNext()) {
            System.out.println(bfsIterator.next());
        }
        System.out.println("------");
    }
}
