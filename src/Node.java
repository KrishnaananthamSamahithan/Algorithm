import java.util.Objects;

public class Node implements Comparable<Node> {
    public int x, y;
    public char type;
    public Node parent;
    public int costFromStart; // g cost
    public int heuristicCost; // h cost
    public int totalCost; // f cost

    public Node(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.parent = null;
        this.costFromStart = Integer.MAX_VALUE;
        this.heuristicCost = Integer.MAX_VALUE;
        this.totalCost = Integer.MAX_VALUE;
    }

    public int compareTo(Node other) {
        return Integer.compare(this.totalCost, other.totalCost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
