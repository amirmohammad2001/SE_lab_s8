package org.example.graphTravelers;

import org.example.adapter.Adapter;

import java.util.*;

public class DfsGraphTraverser implements Traverser {
    private final Adapter graph;

    public DfsGraphTraverser(Adapter graph) {
        this.graph = graph;
    }

    @Override
    public List<Integer> traverse(Integer startVertex) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Integer vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                result.add(vertex);

                List<Integer> neighbors = new ArrayList<>(graph.getNeighbors(vertex));
                neighbors.sort(Integer::compareTo);

                for (Integer neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return result;
    }
}