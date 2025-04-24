package src;

import java.util.*;

public class FlightGraph {
    Map<String, List<Flight>> adjacencyList = new HashMap<>();

    public void addFlight(String origin, String destination, int cost, int time) {
        adjacencyList.computeIfAbsent(origin, k -> new ArrayList<>())
                .add(new Flight(destination, cost, time));
        adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>())
                .add(new Flight(origin, cost, time));
    }

    public List<PathNode> findAllPaths(String start, String end) {
        List<PathNode> validPaths = new ArrayList<>();
        Stack<PathNode> stack = new Stack<>();
        stack.push(new PathNode(start, List.of(start), 0, 0));

        while (!stack.isEmpty()) {
            PathNode current = stack.pop();

            if (current.currCity.equals(end)) {
                validPaths.add(current);
                continue;
            }

            for (Flight flight : adjacencyList.getOrDefault(current.currCity, new ArrayList<>())) {
                if (!current.path.contains(flight.destination)) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(flight.destination);
                    stack.push(new PathNode(
                            flight.destination, newPath,
                            current.totalCost + flight.cost,
                            current.totalTime + flight.time
                    ));
                }
            }
        }

        return validPaths;
    }
}
