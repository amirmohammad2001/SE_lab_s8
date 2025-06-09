package org.example.graphTravelers;


import edu.uci.ics.jung.graph.SparseMultigraph;
import org.example.adapter.JungGraphAdapter;

import java.util.*;

public class BfsGraphTraverser implements Traverser {
    private final JungGraphAdapter graph;

    public BfsGraphTraverser(JungGraphAdapter graph) {
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

            // Get neighbors and sort them for deterministic output
            List<Integer> neighbors = new ArrayList<>(graph.getNeighbors(vertex));
            neighbors.sort(Comparator.naturalOrder());

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