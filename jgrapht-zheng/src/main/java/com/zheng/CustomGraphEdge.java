package com.zheng;

import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomGraphEdge
        extends
        DefaultWeightedEdge
{
    private static final long serialVersionUID = 1L;

    public String getSourceVertex() {
        return getSource().toString();
    }

    public String getTargetVertex() {
        return getTarget().toString();
    }

    public double getWeightValue() {
        return getWeight();
    }

    @Override
    public String toString()
    {
        return getSource() + " -> " + getTarget() + "(" + getWeightValue() + ")";
    }
}
