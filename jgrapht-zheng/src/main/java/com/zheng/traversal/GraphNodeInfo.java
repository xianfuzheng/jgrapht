package com.zheng.traversal;

public class GraphNodeInfo {

    public String node;
    public int degree = 0;
    public String prevNode = null;
    public GraphNodeColor color = GraphNodeColor.WHITE;

    public int d = 0;
    public int f = 0;

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
                '}';
    }
}

