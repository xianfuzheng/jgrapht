package com.zheng.mst;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;

public class Kruskal {
    private static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p367");

        solve(g1);
        solveUnionFind(g1);
    }

    private static void solve(Graph<String, CustomGraphEdge> g) {

        // make set
        int setNo = 1;
        for (String vertex : g.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
            graphNodeInfoMap.get(vertex).set = setNo ++;
        }

        // sort edges - increasing order according the weight value
        List<CustomGraphEdge> edgeList = new ArrayList<>( g.edgeSet());
        edgeList.sort((o1, o2) -> (int)(o1.getWeightValue() - o2.getWeightValue()));

        List<CustomGraphEdge> edgeUsedList = new ArrayList<>();

        // loop edge
        for (CustomGraphEdge customGraphEdge : edgeList) {

            String sourceVertex = customGraphEdge.getSourceVertex();
            String targetVertex = customGraphEdge.getTargetVertex();

            int setNoSource = graphNodeInfoMap.get(sourceVertex).set;
            int setNoDst = graphNodeInfoMap.get(targetVertex).set;

            if(setNoDst == setNoSource) {
                // no need to merge
                continue;
            } else {
                // merge list by setting all elems with set value of setNoDst to setNoSource
                // worst performance
                for (String vertex : g.vertexSet()) {
                    if(graphNodeInfoMap.get(vertex).set == setNoDst) {
                        graphNodeInfoMap.get(vertex).set = setNoSource;
                        System.out.println("Use " + customGraphEdge+" to add " + targetVertex);
                    }
                }

                edgeUsedList.add(customGraphEdge);

            }
        }
        System.out.println("Result:");
        GraphUtils.printResult(graphNodeInfoMap);

    }


    private static void solveUnionFind(Graph<String, CustomGraphEdge> g) {

        for (String vertex : g.vertexSet()) {
            // prevNode is null
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
        }

        // elge
        ArrayList<CustomGraphEdge> edgeList = new ArrayList<>(g.edgeSet());
        edgeList.sort((o1, o2) -> (int)(o1.getWeightValue() - o2.getWeightValue()));

        // e
        for (CustomGraphEdge customGraphEdge : edgeList) {

            System.out.println("Check " + customGraphEdge);

            String sourceVertex = customGraphEdge.getSourceVertex();
            String targetVertex = customGraphEdge.getTargetVertex();

            // lgv
            if(unionFind(targetVertex, sourceVertex)) {
                System.out.println("Use " + customGraphEdge + " to add " + targetVertex);
            }
        }

    }

    private static boolean unionFind(String targetVertex, String sourceVertex) {
        String parent = sourceVertex;
        while( graphNodeInfoMap.get(parent).prevNode != null) {
            parent = graphNodeInfoMap.get(parent).prevNode;
        }

        String node = targetVertex;

        while( graphNodeInfoMap.get(node).prevNode != null) {
            node = graphNodeInfoMap.get(node).prevNode;
        }

        // node == parent
        // node is parent's parent
        if(!node.equals(parent) && !parent.equals(graphNodeInfoMap.get(node).prevNode)) {
            graphNodeInfoMap.get(node).prevNode = parent;
            return true;
        }

        return false;
    }


}
