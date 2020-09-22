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
                graphNodeInfoMap.get(vertex).dist = 0;
            } else {
                graphNodeInfoMap.get(vertex).dist = Integer.MAX_VALUE;
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

        // check cycle
        for (CustomGraphEdge customGraphEdge : g.edgeSet()) {
            String u = customGraphEdge.getSourceVertex();
            String v = customGraphEdge.getTargetVertex();
            double w = customGraphEdge.getWeightValue();

            double du = graphNodeInfoMap.get(u).dist;
            double dv = graphNodeInfoMap.get(v).dist;

            if( dv > (du + w + 1e-2)) {
                System.out.println("Has negative weighted cycle from " + u +" to " + v +" w = " + w);
            }
        }

         GraphUtils.printResult(graphNodeInfoMap);
    }

    private static void relax(String u, String v, double w) {
        double d1 = graphNodeInfoMap.get(u).dist;
        double d2 = graphNodeInfoMap.get(v).dist;

        if( (d1 + w) < d2) {
            // relax
            System.out.println("Use edge from " + u +" to " + v + " w=" + w);
            graphNodeInfoMap.get(v).dist = d1 + w;
            graphNodeInfoMap.get(v).prevNode = u;
        }
    }
}
