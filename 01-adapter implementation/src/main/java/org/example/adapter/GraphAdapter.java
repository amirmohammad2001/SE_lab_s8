package org.example.adapter;

import java.util.List;

public interface GraphAdapter {
    void addVertex(Integer v);
    void addEdge(String e, Integer v1, Integer v2);
    List<Integer> getNeighbors(Integer v);
}

