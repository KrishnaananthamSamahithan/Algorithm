import java.io.*;
import java.util.*;

public class SlidingPuzzle {
    private Node[][] grid;
    private Node startNode;
    private Node finishNode;

    public SlidingPuzzle(String filename) throws FileNotFoundException {
        parseFile(filename);
    }

    private void parseFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();

        int height = lines.size();
        int width = lines.get(0).length();
        grid = new Node[height][width];

        for (int i = 0; i < height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < width; j++) {
                char c = line.charAt(j);
                grid[i][j] = new Node(i, j, c);
                if (c == 'S') {
                    startNode = grid[i][j];
                    startNode.costFromStart = 0; // g-cost
                    startNode.totalCost = calculateHeuristic(startNode, finishNode); // f-cost initially only h-cost
                } else if (c == 'F') {
                    finishNode = grid[i][j];
                }
            }
        }
    }

    private int calculateHeuristic(Node node1, Node node2) {
        // Manhattan distance heuristic
        if (node1 == null || node2 == null) return 0;
        return Math.abs(node1.x - node2.x) + Math.abs(node1.y - node2.y);
    }

    public void findPath() {
        long startTime = System.nanoTime();

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.totalCost));
        HashSet<Node> closedSet = new HashSet<>();
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(finishNode)) {
                long endTime = System.nanoTime();
                printPath(current);
                System.out.println("Total runtime: " + (endTime - startTime) / 1_000_000.0 + " ms");
                return;
            }

            closedSet.add(current);
            for (Node neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor)) continue;
                int tentativeGCost = current.costFromStart + 1;

                if (tentativeGCost < neighbor.costFromStart) {
                    neighbor.parent = current;
                    neighbor.costFromStart = tentativeGCost;
                    neighbor.heuristicCost = calculateHeuristic(neighbor, finishNode);
                    neighbor.totalCost = neighbor.costFromStart + neighbor.heuristicCost;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        System.out.println("No path found.");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];
            if (canSlide(newX, newY)) {
                neighbors.add(grid[newX][newY]);
            }
        }
        return neighbors;
    }

    private boolean canSlide(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y].type != '0';
    }

    private void printPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);

        if (!path.isEmpty()) {
            System.out.println("1. Start at (" + (path.get(0).x + 1) + "," + (path.get(0).y + 1) + ")");
            for (int i = 1; i < path.size(); i++) {
                Node from = path.get(i - 1);
                Node to = path.get(i);
                String direction = getDirection(from, to);
                System.out.println((i + 1) + ". Move " + direction + " to (" + (to.x + 1) + "," + (to.y + 1) + ")");
            }
            System.out.println((path.size() + 1) + ". Done!");
            System.out.println("");
            System.out.println("Number of steps in the path: " + (path.size() - 1));
        }
    }

    private String getDirection(Node from, Node to) {
        if (to.x == from.x) {
            if (to.y > from.y) return "right";
            if (to.y < from.y) return "left";
        } else if (to.y == from.y) {
            if (to.x > from.x) return "down";
            if (to.x < from.x) return "up";
        }
        return "unknown";
    }


}
