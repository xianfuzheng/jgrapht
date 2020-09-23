package com.zheng.shortestpath;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class Dijkstra {
    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g = GraphUtils.getGraph("book_p384");

        solve(g);
    }

    private static void solve(Graph<String, CustomGraphEdge> g) {

        String startVertex = "1";
        Queue<GraphNodeInfo> pq = new PriorityQueue<>(new Comparator<GraphNodeInfo>() {
            @Override
            public int compare(GraphNodeInfo o1, GraphNodeInfo o2) {
                return (int)(o1.dist - o2.dist);
            }
        });

        for (String u : g.vertexSet()) {
            graphNodeInfoMap.put(u, new GraphNodeInfo(u));
            if( u.equals(startVertex)) {
                graphNodeInfoMap.get(startVertex).dist = 0;
            }
            pq.offer(graphNodeInfoMap.get(u));
        }

        while(!pq.isEmpty()) {
            GraphNodeInfo graphNodeInfo = pq.poll();
            graphNodeInfo.color = GraphNodeColor.GREY;

            for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(graphNodeInfo.node)) {
                String dNode = customGraphEdge.getTargetVertex();
                double w = customGraphEdge.getWeightValue();
                relax(graphNodeInfo.node, dNode, w);

                // re insert v to re-heapify
                GraphNodeInfo graphNodeInfo1 = graphNodeInfoMap.get(dNode);
                if(graphNodeInfo1.color == GraphNodeColor.WHITE){
                    pq.remove(graphNodeInfo1);
                    pq.add(graphNodeInfo1);
                }
            }
        }

        GraphUtils.printResult(graphNodeInfoMap);

    }

    private static void relax(String u, String v, double w) {
        double du = graphNodeInfoMap.get(u).dist;
        double dv = graphNodeInfoMap.get(v).dist;

        if( du + w < dv) {
            System.out.println("Relax " + v + " by edge "+ u + "->" + v+" w=" + w);
            graphNodeInfoMap.get(v).dist = du + w;
        }
    }
}
