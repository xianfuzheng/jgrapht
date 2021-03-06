package com.zheng;

import com.zheng.traversal.GraphNodeColor;
import com.zheng.traversal.GraphNodeInfo;
import org.jgrapht.Graph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.nio.json.JSONImporter;
import org.jgrapht.util.SupplierUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Load json file as directed weighted graph
 */
public class GraphUtils {
    public static Map<String, Graph<String, CustomGraphEdge>> graphMap = new HashMap<>();

    static {
        loadGraphs();
    }

    public static void loadGraphs() {
        for (String graphName : Arrays.asList("g1", "g2",
                "book_p351", "book_p355", "book_p357","book_p382","book_p384",
                "book_p402","book_p402_negative_cycle",
                "book_p367","book_p379","book_p425",
                "geeksforgeeks_strongly-connected-components",
                "wiki_bi_connected_graph",
                "bi_connected_graph_circle")) {
            graphMap.put(graphName, loadGraph(graphName));
        }
    }

    public static  Graph<String, CustomGraphEdge>  getGraph(String graphName) {
        return graphMap.get(graphName);
    }

    private static Graph<String, CustomGraphEdge>  loadGraph(String graphName) {

        String input = readFromResourceFile("/graph/" + graphName+".json");

        Graph<String, CustomGraphEdge> g =
                GraphTypeBuilder
                        .directed().weighted(true)
                        .allowingSelfLoops(true)
                .vertexSupplier(SupplierUtil.createStringSupplier(1))
                .edgeSupplier(SupplierUtil.createSupplier(CustomGraphEdge.class)).buildGraph();

        JSONImporter<String, CustomGraphEdge> importer = new JSONImporter<>();
        importer.importGraph(g, new StringReader(input));

        return g;
    }

    private static String readFromResourceFile(String fileName) {
        InputStream resourceAsStream = GraphUtils.class.getResourceAsStream(fileName);
        try {
            return new String(resourceAsStream.readAllBytes(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void changeColor(String node, GraphNodeColor color, Map<String, GraphNodeInfo> nodeInfoMap) {
        System.out.println("Change " +node+ " from " + nodeInfoMap.get(node).color + " to " + color);
        nodeInfoMap.get(node).color = color;
    }

    public static void printResult( Map<String, GraphNodeInfo> nodeInfoMap) {
        System.out.println("----------------- nodeInfoMap --------------------");
        for (String vertex : nodeInfoMap.keySet()) {
            GraphNodeInfo graphNodeInfo = nodeInfoMap.get(vertex);
            System.out.println(vertex + ":");
            System.out.println(graphNodeInfo);
        }
    }

}
