package com.zheng.representation;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyList {
    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");
        String[] nodeArray = g1.vertexSet().toArray(new String[0]);

        for(int i=0;i<nodeArray.length;i++) {
            String node = nodeArray[i];
            System.out.print(node );
            for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(node)) {
                System.out.print( " - ("+ customGraphEdge.getWeightValue() +")-> ");
                String targetNode = customGraphEdge.getTargetVertex();
                System.out.print(targetNode);
            }
            System.out.println("");
        }

    }
}
