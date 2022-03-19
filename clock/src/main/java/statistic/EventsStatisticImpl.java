package statistic;

import clock.Clock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EventsStatisticImpl implements EventsStatistic {
    private static final int MILLIS_IN_HOUR = 60 * 60 * 1000;
    private Map<String, Queue<Long>> events = new HashMap<>();
    private Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        if (!events.containsKey(name)) {
            events.put(name, new LinkedList<>());
        }
        events.get(name).add(clock.getNowInMillis());
    }

    @Override
    public double getEventStatisticByName(String name) {
        if (!events.containsKey(name)) {
            return 0.;
        }
        Queue<Long> queue = events.get(name);
        long currentTime = clock.getNowInMillis();
        while (!queue.isEmpty() && currentTime - queue.peek() > MILLIS_IN_HOUR) {
            queue.poll();
        }
        return queue.size() / 60.;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> result = new HashMap<>();
        for (String name : events.keySet()) {
            result.put(name, getEventStatisticByName(name));
        }
        return result;
    }

    @Override
    public void printStatistic() {
        System.out.println("Statistic:");
        for (Map.Entry<String, Double> entry : getAllEventStatistic().entrySet()) {
            System.out.printf("%.5f rpm for event %s%n", entry.getValue(), entry.getKey());
        }
    }
}
