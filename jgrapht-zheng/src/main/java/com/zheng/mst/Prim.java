package com.zheng.mst;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// vlgv + elgv
public class Prim {

    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p367");

        solve(g1);
    }

    private static void solve(Graph<String, CustomGraphEdge> g) {

        // init
        for (String v : g.vertexSet()) {
            graphNodeInfoMap.put(v, new GraphNodeInfo(v));
        }

        String startVertex = "1";

        Set<String> set = new HashSet<>(g.vertexSet());
        graphNodeInfoMap.get(startVertex).dist = 0;

        while ( !set.isEmpty()) {

            // get min dist
            double min = Integer.MAX_VALUE;
            String minV = null;
            for (String v : set) {
                if(graphNodeInfoMap.get(v).dist < min) {
                    min = graphNodeInfoMap.get(v).dist;
                    minV = v;
                }
            }

            System.out.println("Adding " + minV);
            set.remove(minV);

            for (CustomGraphEdge customGraphEdge : g.edgesOf(minV)) {
                String dNode = Graphs.getOppositeVertex(g, customGraphEdge, minV);
                if(set.contains(dNode)) {
                    double currentDist = graphNodeInfoMap.get(dNode).dist;
                    if(currentDist > customGraphEdge.getWeightValue()) {
                        System.out.println("Use edge " + customGraphEdge +" to update dist");
                        graphNodeInfoMap.get(dNode).prevNode = minV;
                        graphNodeInfoMap.get(dNode).dist = customGraphEdge.getWeightValue();
                    }
                }
            }
        }

        //result
        System.out.println("Result ... ");
        GraphUtils.printResult(graphNodeInfoMap);
    }

}
