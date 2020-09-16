package com.zheng.topologicsorting;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class ByInComingDegree {
 public static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("book_p355");

        List<String> sortedList = new ArrayList<>();
        int size = g1.vertexSet().size();

        // in degree number
        for (String vertex : g1.vertexSet()) {
            graphNodeInfoMap.put(vertex, new GraphNodeInfo(vertex));
            graphNodeInfoMap.get(vertex).degree = g1.inDegreeOf(vertex);
        }

        while( size >0) {

            Queue<String> queue = new ArrayDeque<>();

            for (String vertex : g1.vertexSet()) {
                if(graphNodeInfoMap.get(vertex).color == GraphNodeColor.WHITE &&
                        graphNodeInfoMap.get(vertex).degree == 0) {
                    sortedList.add(vertex);
                    size --;
                    queue.add(vertex);
                    graphNodeInfoMap.get(vertex).color = GraphNodeColor.GREY;
                }
            }

            while(!queue.isEmpty()) {
                String poll = queue.poll();
                for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(poll)) {
                    String dNode = customGraphEdge.getTargetVertex();
                    graphNodeInfoMap.get(dNode).degree --;
                }
            }

        }

        System.out.println(sortedList.stream().collect(Collectors.joining("->")));
    }
}
