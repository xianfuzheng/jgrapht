package com.zheng.connectivity;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import org.jgrapht.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class PossiblePathBetweenTwoVertices {


    public static void main(String [] args) {
        Graph<String, CustomGraphEdge> g1 = GraphUtils.getGraph("g2");

        int count1 = solve("1", "4", g1);
        System.out.println("No step restriction :" + count1);


        int count2 = solve("1", "4", g1,2);
        System.out.println("Two steps only:" + count2);
    }

    public static int solve(String source, String dst, Graph<String, CustomGraphEdge> g, int steps) {
        List<String> path = new ArrayList<>();
        path.add(source);
        return dfs(source, dst, g, path, steps, true);
    }

    public static int solve(String source, String dst, Graph<String, CustomGraphEdge> g) {
        List<String> path = new ArrayList<>();
        path.add(source);
        return dfs(source, dst, g, path, 0, false);
    }

    public static int dfs(String node, String targetNode,  Graph<String, CustomGraphEdge> g, List<String> path, int steps, boolean exactSteps) {

        if(node.equals(targetNode) &&
                (!exactSteps || steps == 0)) {
            System.out.println(path.stream().collect(Collectors.joining("->")));
            return 1;
        }

        int c = 0;

        for (CustomGraphEdge customGraphEdge : g.outgoingEdgesOf(node)) {
            String dNode = customGraphEdge.getTargetVertex();
            path.add(dNode);
            c = c + dfs(dNode, targetNode, g, path, steps-1, exactSteps);
            path.remove(path.size()-1);
        }
        return c;
    }
}
