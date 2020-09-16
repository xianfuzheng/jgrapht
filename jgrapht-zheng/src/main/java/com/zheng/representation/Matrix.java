package com.zheng.representation;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import org.jgrapht.Graph;

import java.util.HashMap;
import java.util.Map;

/**
 * directed graph with weight
 */
public class Matrix {
    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g1");

        String[] nodeArray = g1.vertexSet().toArray(new String[0]);
        int n = nodeArray.length;
        double [][] matrix = new double[n][n];

        Map<String, Integer> nodeIndexMap = new HashMap<>();
        for(int i=0; i<n; i++) {
            String node = nodeArray[i];
            nodeIndexMap.put(node, i);
        }


        for(int i=0; i<n; i++) {
            String node = nodeArray[i];
            for (CustomGraphEdge customGraphEdge : g1.outgoingEdgesOf(node)) {
                String targetNode = customGraphEdge.getTargetVertex();

                // find index
                int idx = nodeIndexMap.get(targetNode);
                matrix[i][idx] = g1.getEdgeWeight(customGraphEdge);
            }
        }

        //print out
        for (int i=0; i<n; i++) {
            System.out.print("[");
            for (int j=0; j<n; j++) {
                System.out.print(matrix[i][j]);
                if(j<n-1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
    }

}
