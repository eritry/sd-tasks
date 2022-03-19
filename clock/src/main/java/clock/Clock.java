package clock;

import java.time.Instant;

public interface Clock {
    Instant now();

    long getNowInMillis();
}
