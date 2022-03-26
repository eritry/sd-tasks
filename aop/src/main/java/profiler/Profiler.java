package profiler;
import model.CallCounter;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Profiler {
    private final static Map<String, CallCounter> statistics = new HashMap<>();
    private final static Map<String, Instant> callsInProgress = new HashMap<>();

    private final static Clock clock = Clock.systemDefaultZone();

    static void start(String func) {
        callsInProgress.put(func, clock.instant());
    }

    static void end(String func) {
        statistics.putIfAbsent(func, new CallCounter());
        statistics.get(func).call(Duration.between(callsInProgress.get(func), clock.instant()));
    }

    public static void printStatistics() {
        for (String func : statistics.keySet()) {
            System.out.println("Function : " + func);
            System.out.println(statistics.get(func));
            System.out.println();
        }
    }

}
