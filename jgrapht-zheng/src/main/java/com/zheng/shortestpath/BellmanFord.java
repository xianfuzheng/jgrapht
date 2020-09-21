package com.zheng.shortestpath;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.HashMap;
import java.util.Map;

public class BellmanFord {

    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    // directed graph
    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g = GraphUtils.getGraph("book_p379");
        solve(g, "1");

    }

    private static void solve(Graph<String, CustomGraphEdge> g, String source) {
        // init
        for (String vertex : g.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
            if(vertex.equals(source)) {
                graphNodeInfoMap.get(vertex).d = 0;
            } else {
                graphNodeInfoMap.get(vertex).d = Integer.MAX_VALUE;
            }
        }

        int size = g.vertexSet().size();
        for (int i=0; i<size; i++) {

            for (CustomGraphEdge customGraphEdge : g.edgeSet()) {
                String u = customGraphEdge.getSourceVertex();
                String v = customGraphEdge.getTargetVertex();
                double w = customGraphEdge.getWeightValue();
                relax(u, v, w);
            }
        }

        GraphUtils.printResult(graphNodeInfoMap);
    }

    private static void relax(String u, String v, double w) {
        double d1 = graphNodeInfoMap.get(u).d;
        double d2 = graphNodeInfoMap.get(v).d;

        if( (d1 + w) < d2) {
            // relax
            System.out.println("Use edge from " + u +" to " + v + " w=" + w);
            graphNodeInfoMap.get(v).d = (int)(d1 + w);
            graphNodeInfoMap.get(v).prevNode = u;
        }
    }
}
