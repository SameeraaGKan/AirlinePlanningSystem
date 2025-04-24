package src;

import java.util.*;

public class PathNode {
    String currCity;
    List <String> path;
    int totalCost;
    int totalTime;

    //Constructor with arguments
    public PathNode(String currCity, List <String> path, int totalCost, int totalTime) {
        this.currCity = currCity;
        this.path = new ArrayList<>(path);
        this.totalCost = totalCost;
        this.totalTime = totalTime;
    }
}
