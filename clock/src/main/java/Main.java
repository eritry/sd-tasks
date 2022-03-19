import clock.Clock;
import clock.SetableClock;
import statistic.EventsStatistic;
import statistic.EventsStatisticImpl;

import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Clock clock = new SetableClock(Instant.now());
        EventsStatistic eventsStatistic = new EventsStatisticImpl(clock);

        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("a");
        eventsStatistic.incEvent("b");
        eventsStatistic.incEvent("b");
        eventsStatistic.incEvent("b");

        eventsStatistic.printStatistic();
    }
}
