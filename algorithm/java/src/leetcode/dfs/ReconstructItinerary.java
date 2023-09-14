package leetcode.dfs;

import java.util.*;
import java.util.stream.Collectors;

/*
Q.332. Reconstruct Itinerary
You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
 */
public class ReconstructItinerary {
    public List<String> findItinerary(List<List<String>> tickets) {
        Airline airline = new Airline();

        for (List<String> ticket : tickets) {
            airline.addAirline(ticket.get(0), ticket.get(1));
        }

        for (List<String> ticket : tickets) {
            List<String> result = airline.findPaths(ticket.get(0));

            if (result != null) return result;
        }

        return null;
    }

    static class Airline {

        private Map<String, FlightPath> airlineMap = new HashMap<>();
        private int lineCount = 1;

        void addAirline(String from, String to) {
            FlightPath fromPaths = airlineMap.computeIfAbsent(from, k -> new FlightPath(from));
            FlightPath toPaths = airlineMap.computeIfAbsent(to, k -> new FlightPath(to));

            fromPaths.addPath(toPaths);
            lineCount++;
        }

        List<String> findPaths(String key) {
            return airlineMap.get(key).getNextPath(new ArrayList<>(), lineCount);
        }


        static class FlightPath {
            private String name;
            private List<FlightPath> paths = new ArrayList<>();
            private List<FlightPath> sortedPaths = new ArrayList<>();
            private Map<FlightPath, Boolean> visited = new HashMap<>();
            private boolean beforeSorted = true;

            FlightPath(String name) {
                this.name = name;
            }

            void addPath(FlightPath target) {
                paths.add(target);
                visited.put(target, false);
                beforeSorted = true;
            }

            List<String> getNextPath(List<String> routing, int remainCount) {
                if (beforeSorted) sortedPaths = paths.stream()
                        .sorted(Comparator.comparing(a -> a.name))
                        .collect(Collectors.toList());

                beforeSorted = false;
                routing.add(this.name);
                int nextRemainCount = remainCount - 1;

                if (nextRemainCount == 0) return routing;

                for (FlightPath sortedPath : sortedPaths) {
                    if (!visited.get(sortedPath)) {
                        visited.put(sortedPath, true);
                        List<String> result = sortedPath.getNextPath(routing, nextRemainCount);

                        if (result != null) return result;

                        visited.put(sortedPath, false);
                    }
                }
                routing.remove(this.name);
                return null;
            }
        }
    }
}

/*
public List<String> findItinerary(String[][] tickets) {
    for (String[] ticket : tickets)
        targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
    visit("JFK");
    return route;
}

Map<String, PriorityQueue<String>> targets = new HashMap<>();
List<String> route = new LinkedList();

void visit(String airport) {
    while(targets.containsKey(airport) && !targets.get(airport).isEmpty())
        visit(targets.get(airport).poll());
    route.add(0, airport);
}
 */