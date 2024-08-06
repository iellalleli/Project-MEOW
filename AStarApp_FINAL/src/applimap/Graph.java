package applimap;

import java.util.*;

public class Graph<T extends Node> {

    private Map<Node, List<Edge<Node>>> edges;

    public Graph() {
        edges = new HashMap<>();
    }

    public List<Node> findPath(Node start, Node target) {
        PriorityQueue<NodeWrapper> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Set<Node> closedList = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Double> gCosts = new HashMap<>();
        Map<Node, Double> fCosts = new HashMap<>();

        gCosts.put(start, 0.0);
        fCosts.put(start, start.getEuclideanDistance(target));

        openList.add(new NodeWrapper(start, fCosts.get(start)));

        while (!openList.isEmpty()) {
            Node current = openList.poll().node;

            if (current.equals(target)) {
                return reconstructPath(cameFrom, current);
            }

            closedList.add(current);

            for (Edge<Node> edge : edges.getOrDefault(current, Collections.emptyList())) {
                Node neighbor = edge.target;
                if (closedList.contains(neighbor)) continue;

                double tentativeGCost = gCosts.get(current) + edge.weight;

                if (tentativeGCost < gCosts.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gCosts.put(neighbor, tentativeGCost);
                    fCosts.put(neighbor, tentativeGCost + neighbor.getEuclideanDistance(target));

                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = neighbor.getEuclideanDistance(target);
                    neighbor.fCost = tentativeGCost + neighbor.hCost;

                    if (!openList.contains(new NodeWrapper(neighbor, fCosts.get(neighbor)))) {
                        openList.add(new NodeWrapper(neighbor, fCosts.get(neighbor)));
                        neighbor.setAsOpen();
                    }
                }
            }

            current.setAsChecked();
        }

        return Collections.emptyList(); // No path found
    }

    private List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current.setAsPath();
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(edges.keySet());
    }
}

class NodeWrapper {
    Node node;
    double fCost;

    NodeWrapper(Node node, double fCost) {
        this.node = node;
        this.fCost = fCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeWrapper that = (NodeWrapper) o;
        return Double.compare(that.fCost, fCost) == 0 && Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, fCost);
    }
}

class Edge<T> {
    T target;
    double weight;

    Edge(T target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}