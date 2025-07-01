package org.example.adapter;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Adapter implements GraphAdapter {
    private final SimpleGraph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    @Override
    public void addVertex(Integer v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String e, Integer v1, Integer v2) {
        graph.addEdge(v1, v2);
    }

    @Override
    public List<Integer> getNeighbors(Integer v) {
        Set<DefaultEdge> edges = graph.edgesOf(v);
        List<Integer> neighbors = new ArrayList<>();
        for (DefaultEdge edge : edges) {
            Integer source = graph.getEdgeSource(edge);
            Integer target = graph.getEdgeTarget(edge);
            if (source.equals(v)) {
                neighbors.add(target);
            } else {
                neighbors.add(source);
            }
        }
        return neighbors;
    }

    // Optional: direct access to the raw graph
    public SimpleGraph<Integer, DefaultEdge> getRawGraph() {
        return graph;
    }
}

