package com.zheng.traversal;

public class GraphNodeInfo {

    public String node;
    public int degree = 0;
    public String prevNode = null;
    public GraphNodeColor color = GraphNodeColor.WHITE;

    // dfs
    public int d = 0;
    public int f = 0;

    // bi connected and articulation point
    public int childrenCount = 0;
    public int depth = 0;
    public int lower = 0;

    // kruskal
    public int set = -1;

    // Prim
    public double dist = Integer.MAX_VALUE;

    public GraphNodeInfo(String node) {
        this.node = node;
    }
    @Override
    public String toString() {
        return "GraphNodeInfo{" +
                "node=" + node +
                ", degree=" + degree +
                ", prevNode='" + prevNode + '\'' +
                ", color=" + color +
                ", d=" + d +
                ", f=" + f +
                ", depth=" + depth +
                ", lower=" + lower +
                ", childrenCount=" + childrenCount +
                ", set = " + set +
                '}';
    }
}

