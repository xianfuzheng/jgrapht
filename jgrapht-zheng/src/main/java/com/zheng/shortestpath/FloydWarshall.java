package com.zheng.shortestpath;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import org.jgrapht.Graph;

public class FloydWarshall {

    public static void main(String [] args) {

        Graph<String, CustomGraphEdge> g = GraphUtils.getGraph("book_p402");

        int size = g.vertexSet().size();

        // init dist matrix
        double [][] dist = new double[size][size];
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if(i==j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (String node : g.vertexSet()) {
            int idx = Integer.parseInt(node) -1;
            for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(node)) {
                String dst = customGraphEdge.getTargetVertex();
                double w = customGraphEdge.getWeightValue();
                int idx2 = Integer.parseInt(dst) -1;
                dist[idx][idx2] = w;
            }
        }

        printMatrix(dist);

        // main function
        for(int k=0; k<size;k ++) {
            for (int i=0;i<size; i++) {
                for (int j=0; j<size; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        printMatrix(dist);

    }

    private static void printMatrix(double [][] dist) {

        int n = dist.length;
        //print out
        for (int i=0; i<n; i++) {
            System.out.print("[");
            for (int j=0; j<n; j++) {
                System.out.print(dist[i][j]);
                if(j<n-1) {
                    System.out.print(",");
                }
            }
            System.out.println("]");
        }
    }
}
