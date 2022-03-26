package model;

import java.time.Duration;

public class CallCounter {
    private Duration duration;
    private int count;

    public CallCounter() {
        this.duration = Duration.ofMillis(0);
        this.count = 0;
    }

    private int getCount() {
        return count;
    }

    private Duration getDuration() {
        return duration;
    }

    public void call(Duration duration) {
        count++;
        this.duration = this.duration.plus(duration);
    }

    @Override
    public String toString() {
        return String.format("Calls count : %d\n", getCount()) +
                String.format("Calls duration : %d mills\n", getDuration().toMillis()) +
                String.format("Average calls duration : %d mills", (getDuration().dividedBy(getCount())).toMillis());
    }

}
