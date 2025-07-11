package org.example.graphTravelers;


import org.example.adapter.Adapter;

import java.util.*;

public class BfsGraphTraverser implements Traverser {
    private final Adapter graph;

    public BfsGraphTraverser(Adapter graph) {
        this.graph = graph;
    }

    @Override
    public List<Integer> traverse(Integer startVertex) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(startVertex);
        visited.add(startVertex);

        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            result.add(vertex);

            List<Integer> neighbors = new ArrayList<>(graph.getNeighbors(vertex));
            neighbors.sort(Integer::compareTo);

            for (Integer neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }
}