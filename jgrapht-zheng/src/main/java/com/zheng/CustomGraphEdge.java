package com.zheng;

import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomGraphEdge
        extends
        DefaultWeightedEdge
{
    private static final long serialVersionUID = 1L;

    private double flow;

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

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }
}
