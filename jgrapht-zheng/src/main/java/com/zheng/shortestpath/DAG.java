package com.zheng.shortestpath;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class DAG {

    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    private static int timer = 1;
    public static void main(String [] args) {

        Graph<String, CustomGraphEdge> g = GraphUtils.getGraph("book_p382");

        for (String vertex : g.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        // topologic sorting
        for (String vertex : g.vertexSet()) {
            if (graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
               GraphUtils.changeColor(vertex, GraphNodeColor.GREY, graphNodeInfoMap);
               dfs(g, vertex);
            }
        }
        //sort based on f
        List<GraphNodeInfo> graphNodeInfoList = new ArrayList<>(graphNodeInfoMap.values());
        graphNodeInfoList.sort((o1, o2) -> o2.f - o1.f);

                // dist calculation
                graphNodeInfoMap.get("2").dist = 0;

        for(int i=0; i< graphNodeInfoList.size(); i++) {
            String u = graphNodeInfoList.get(i).node;

            for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(u)) {
                String v = customGraphEdge.getTargetVertex();
                double w = customGraphEdge.getWeightValue();
                relax(u,v, w);
            }
        }

        GraphUtils.printResult(graphNodeInfoMap);
    }

    private static void relax(String u, String v, double w) {
        double du = graphNodeInfoMap.get(u).dist;
        double dv = graphNodeInfoMap.get(v).dist;

        if ( du + w < dv) {
            graphNodeInfoMap.get(v).dist = du +w;
        }
    }

    private static void dfs(Graph<String, CustomGraphEdge> g, String vertex) {
        graphNodeInfoMap.get(vertex).d = timer ++;

        for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(vertex)) {
            String dNode = customGraphEdge.getTargetVertex();
            if (graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                GraphUtils.changeColor(dNode, GraphNodeColor.GREY, graphNodeInfoMap);
                graphNodeInfoMap.get(dNode).prevNode = vertex;
                dfs(g, dNode);
            }
        }

        GraphUtils.changeColor(vertex, GraphNodeColor.BLACK, graphNodeInfoMap);
        graphNodeInfoMap.get(vertex).f = timer ++;
    }
}
