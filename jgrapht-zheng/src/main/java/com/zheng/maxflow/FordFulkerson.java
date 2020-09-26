package com.zheng.maxflow;

import com.zheng.CustomGraphEdge;
import com.zheng.GraphUtils;
import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import java.util.*;

public class FordFulkerson {

    public static Map<String, GraphNodeInfo> graphNodeInfoMap = new HashMap<>();

    public static void main(String []args) {
        Graph<String, CustomGraphEdge> g = GraphUtils.getGraph("book_p425");
        solve(g);
    }

    private static void solve(Graph<String, CustomGraphEdge> g) {
        for (String v : g.vertexSet()) {
            graphNodeInfoMap.put(v, new GraphNodeInfo(v));
        }
        // init flow to 0
        for (CustomGraphEdge customGraphEdge : g.edgeSet()) {
            customGraphEdge.setFlow(0);
        }

        Graph<String, CustomGraphEdge> residualGraph = generateResidualGraph(g);

        // while loop to keep looking
        boolean hasAugmentingPath = true;
        while(hasAugmentingPath) {
            // update residue graph
            updateResidualGraph(g,  residualGraph);

            // find augment path - dfs
            List<CustomGraphEdge> edgeList = findAugmentingPath(residualGraph, "1", "6");
            if(edgeList == null) {
                hasAugmentingPath = false;
            } else {
                // update flow in original graph
                updateFlow(g, edgeList);
            }
        }
    }

    private static Graph<String, CustomGraphEdge> generateResidualGraph(Graph<String, CustomGraphEdge> g) {

        Graph<String, CustomGraphEdge> residualGraph =
                GraphTypeBuilder
                        .directed()
                        .weighted(true)
                        .vertexSupplier(SupplierUtil.createStringSupplier(1))
                        .edgeSupplier(SupplierUtil.createSupplier(CustomGraphEdge.class)).buildGraph();

        // add all vertices
        for (String v : g.vertexSet()) {
            residualGraph.addVertex(v);
        }

        for (CustomGraphEdge customGraphEdge : g.edgeSet()) {
            residualGraph.addEdge(customGraphEdge.getSourceVertex(),
                    customGraphEdge.getTargetVertex(),
                    new CustomGraphEdge());
            residualGraph.addEdge(customGraphEdge.getTargetVertex(),
                    customGraphEdge.getSourceVertex(),
                    new CustomGraphEdge());
        }

        return residualGraph;
    }

    private static void updateResidualGraph(Graph<String, CustomGraphEdge> g,
                                            Graph<String, CustomGraphEdge> residualGraph) {
        // reset the residual graph , not necessary because the below loop will re-calculate all edges

        // for each edge
        for (CustomGraphEdge customGraphEdge : g.edgeSet()) {
            double flow = customGraphEdge.getFlow();
            double capacity = customGraphEdge.getWeightValue();

            double residualCapacity = capacity - flow;

            String sourceNode = customGraphEdge.getSourceVertex();
            String targetNode = customGraphEdge.getTargetVertex();

            residualGraph.getEdge(sourceNode, targetNode).setFlow( residualCapacity);

            if(flow>0) {
                residualGraph.getEdge(targetNode, sourceNode).setFlow( flow );
            } else {
                residualGraph.getEdge(targetNode, sourceNode).setFlow( 0 );
            }
        }

    }

    private static List<CustomGraphEdge> findAugmentingPath(Graph<String, CustomGraphEdge> residualGraph,
                                                            String startVertex, String endVertex) {

        // maintain dfs status
        Map<String, GraphNodeInfo> residualGraphNodeInfoMap = new HashMap<>();
        for (String v : residualGraph.vertexSet()) {
            residualGraphNodeInfoMap.put(v, new GraphNodeInfo(v));
        }

        List<CustomGraphEdge> edgeList = new ArrayList<>();

        Stack<String> stack = new Stack<>();
        residualGraphNodeInfoMap.get(startVertex).color = GraphNodeColor.GREY;
        stack.push(startVertex);
        boolean hasPath = false;

        while(!stack.isEmpty()) {
            String peek = stack.peek();

            double max = 0;
            CustomGraphEdge maxEdge = null;
            for (CustomGraphEdge customGraphEdge : residualGraph.outgoingEdgesOf(peek)) {
                String dNode = customGraphEdge.getTargetVertex();
                if(residualGraphNodeInfoMap.get(dNode).color == GraphNodeColor.WHITE) {
                    double w = customGraphEdge.getFlow();
                    if( w > max) {
                        max = w;
                        maxEdge = customGraphEdge;
                    }
                }
            }

            if(maxEdge == null) {
                stack.pop();
                continue;
            }

            String nextVertex = maxEdge.getTargetVertex();
            stack.push(nextVertex);
            residualGraphNodeInfoMap.get(nextVertex).color = GraphNodeColor.GREY;

            if(nextVertex.equals(endVertex)) {
                hasPath = true;
               break;
            }
        }

        if( hasPath) {
            while(!stack.isEmpty()) {
                String v = stack.pop();
                if(v.equals(startVertex)) {
                    break;
                }
                String u = stack.peek();
                edgeList.add(residualGraph.getEdge(u, v));
            }
        }
        return hasPath? edgeList: null;
    }

    private static void updateFlow(Graph<String, CustomGraphEdge> g, List<CustomGraphEdge> edgeList) {

        double minFlowToAdd = Integer.MAX_VALUE;

        for (CustomGraphEdge customGraphEdge : edgeList) {

            double f = customGraphEdge.getFlow();

            if ( minFlowToAdd>f) {
                minFlowToAdd = f;
            }
        }

        for (CustomGraphEdge customGraphEdge : edgeList) {
            String sourceNode = customGraphEdge.getSourceVertex();
            String dNode = customGraphEdge.getTargetVertex();

            double f = g.getEdge(sourceNode, dNode).getFlow();
            g.getEdge(sourceNode, dNode).setFlow(f + minFlowToAdd);
        }
    }
}
