package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightPlannerBackend {
    static FlightGraph graph = new FlightGraph();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/flight_data.txt"))) {
            int n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                String[] parts = reader.readLine().split("\\|");
                graph.addFlight(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> searchFlights(String origin, String destination, char sortBy) {
        List<PathNode> paths = graph.findAllPaths(origin, destination);
        List<String> output = new ArrayList<>();

        if (paths.isEmpty()) {
            output.add("No flight path available.");
            return output;
        }

        paths.sort((a, b) -> sortBy == 'T'
                ? Integer.compare(a.totalTime, b.totalTime)
                : Integer.compare(a.totalCost, b.totalCost));

        for (int i = 0; i < Math.min(3, paths.size()); i++) {
            PathNode p = paths.get(i);
            output.add(String.format("Path %d: %s. Time: %d Cost: %.2f",
                    i + 1, String.join(" -> ", p.path), p.totalTime, (double) p.totalCost));
        }
        return output;
    }
}
