package org.example.adapter;

import edu.uci.ics.jung.graph.SparseMultigraph;

import java.util.ArrayList;
import java.util.List;

public class JungGraphAdapter implements GraphAdapter {
        private final SparseMultigraph<Integer, String> graph = new SparseMultigraph<>();

        @Override
        public void addVertex(Integer v) {
            graph.addVertex(v);
        }

        @Override
        public void addEdge(String e, Integer v1, Integer v2) {
            graph.addEdge(e, v1, v2);
        }

        @Override
        public List<Integer> getNeighbors(Integer v) {
            return new ArrayList<>(graph.getNeighbors(v));
        }

        // اگر نیاز دارید، متد getRawGraph برای دسترسی مستقیم به گراف
        public SparseMultigraph<Integer, String> getRawGraph() {
            return graph;
        }
    }

