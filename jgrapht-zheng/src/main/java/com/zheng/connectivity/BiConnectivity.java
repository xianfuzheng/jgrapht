package com.zheng.connectivity;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;

import java.util.*;

public class BiConnectivity {

    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("wiki_bi_connected_graph");
        solve(g1);

        Graph<String, CustomGraphEdge> g2 = GraphUtils.getGraph("bi_connected_graph_circle");
        solve(g2);

    }

    private static void solve(Graph<String, CustomGraphEdge> g) {
        // use it as undirected graph
        // AP: 4 ,5, 6, 12, 13
        // bridege:
        // 0 = {CustomGraphEdge@1283} "13 -> 14(1.0)"
        // 1 = {CustomGraphEdge@1284} "10 -> 12(1.0)"
        //2 = {CustomGraphEdge@1285} "6 -> 10(1.0)"
        //3 = {CustomGraphEdge@1286} "5 -> 6(1.0)"
        //4 = {CustomGraphEdge@1287} "4 -> 5(1.0)"



        BiconnectivityInspector biconnectivityInspector = new BiconnectivityInspector(g);

        for (Object cutpoint : biconnectivityInspector.getCutpoints()) {
            System.out.println(cutpoint);
        }

        graphNodeInfoMap.clear();
        for (String vertex : g.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        Set<String> articulationPoints = new HashSet<>();
        for(String vertex : g.vertexSet()) {
            int depth = 0;

            if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE) {
                GraphUtils.changeColor(vertex, GraphNodeColor.GREY, graphNodeInfoMap);
                dfs(g, vertex, depth, articulationPoints);
            }
        }

        //result
        for (String articulationPoint : articulationPoints) {
            System.out.println("Found Articulation Point:" + articulationPoint);
        }

        GraphUtils.printResult(graphNodeInfoMap);
    }

    private static void dfs(Graph<String, CustomGraphEdge> g, String vertex, int depth, Set<String> articulationPoints ) {

        graphNodeInfoMap.get(vertex).depth = depth;
        graphNodeInfoMap.get(vertex).lower = depth;

        for (CustomGraphEdge customGraphEdge : g.edgesOf(vertex)) {
            String dNode = Graphs.getOppositeVertex(g, customGraphEdge, vertex);

            if(graphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {

                GraphUtils.changeColor(dNode, GraphNodeColor.GREY, graphNodeInfoMap);

                graphNodeInfoMap.get(vertex).childrenCount ++;
                graphNodeInfoMap.get(dNode).prevNode = vertex;

                dfs(g, dNode, depth+1, articulationPoints);

                if (graphNodeInfoMap.get(vertex).lower > graphNodeInfoMap.get(dNode).lower) {
                    graphNodeInfoMap.get(vertex).lower = graphNodeInfoMap.get(dNode).lower;
                }

                // parent node & more than one children
                if(graphNodeInfoMap.get(vertex).prevNode == null && graphNodeInfoMap.get(vertex).childrenCount >1) {
                    articulationPoints.add(vertex);
                }

                if(graphNodeInfoMap.get(vertex).prevNode != null) {
                    if(graphNodeInfoMap.get(vertex).depth < graphNodeInfoMap.get(dNode).lower) {
                        articulationPoints.add(vertex);
                        System.out.println("Found a bridge:" + vertex + " -> " + dNode + "  " + customGraphEdge);
                    }
                }
            } else {
                String prevNode = graphNodeInfoMap.get(vertex).prevNode;
                if( prevNode!= null && !prevNode.equals(dNode)) {
                    graphNodeInfoMap.get(vertex).lower = graphNodeInfoMap.get(dNode).depth;
                }
            }
        }

    }
}
