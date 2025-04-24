package src;

import java.io.*;
import java.util.*;

public class FlightPlanner {
    public static void main(String[] args) throws IOException {
        FlightGraph graph = new FlightGraph();

        // Load flight data
        try (BufferedReader reader = new BufferedReader(new FileReader("src/flight_data.txt"))) {
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                String[] parts = reader.readLine().split("\\|");
                graph.addFlight(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]));
            }
        }

        // Load flight requests
        List<FlightRequest> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/requests.txt"))) {
            int r = Integer.parseInt(reader.readLine());
            for (int i = 0; i < r; i++) {
                String[] parts = reader.readLine().split("\\|");
                requests.add(new FlightRequest(parts[0], parts[1], parts[2].charAt(0)));
            }
        }

        // Handle requests
        try (PrintWriter writer = new PrintWriter("output.txt")) {
            int flightNum = 1;
            for (FlightRequest request : requests) {
                writer.printf("Flight %d: %s, %s (%s)%n", flightNum++, request.startCity, request.destinationCity,
                        request.sortBy == 'T' ? "Time" : "Cost");
                System.out.printf("Flight %d: %s, %s (%s)%n", flightNum, request.startCity, request.destinationCity,
                        request.sortBy == 'T' ? "Time" : "Cost");


                List<PathNode> paths = graph.findAllPaths(request.startCity, request.destinationCity);
                if (paths.isEmpty()) {
                    writer.println("No flight path available.");
                    continue;
                }

                // Sort by requested type
                paths.sort((a, b) -> request.sortBy == 'T' ?
                        Integer.compare(a.totalTime, b.totalTime) :
                        Integer.compare(a.totalCost, b.totalCost));

                // Print top 3 paths
                for (int i = 0; i < Math.min(3, paths.size()); i++) {
                    PathNode p = paths.get(i);
                    writer.printf("Path %d: %s. Time: %d Cost: %.2f%n",
                            i + 1,
                            String.join(" -> ", p.path),
                            p.totalTime,
                            (double)p.totalCost);
                    System.out.printf("Path %d: %s. Time: %d Cost: %.2f%n",
                            i + 1, String.join(" -> ", p.path),
                            p.totalTime, (double)p.totalCost);

                }
                writer.println();
                System.out.println();
            }
        }
    }
}
