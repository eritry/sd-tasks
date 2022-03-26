package vk;

import vk.counter.Counter;
import vk.counter.CounterImpl;
import vk.vk.VkRequester;
import one.util.streamex.EntryStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CounterTest {
    private Random random;
    private VkRequester vkRequester;
    private Counter counter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.random = new Random();
        this.vkRequester = mock(VkRequester.class);
        this.counter = new CounterImpl(vkRequester);
    }

    @Test
    public void testCounter() {
        final Map<Date, Integer> startDateToCountMap = new HashMap<>();
        when(vkRequester.count(anyString(), anyObject(), anyObject())).thenAnswer(invocation -> {
            final Date startDate = invocation.getArgumentAt(1, Date.class);
            final int count = random.nextInt(100);
            startDateToCountMap.put(startDate, count);
            return count;
        });

        final int[] actualResult = counter.count("#коты", 12);
        final int[] expectedResult = EntryStream.of(startDateToCountMap)
                .reverseSorted(Map.Entry.comparingByKey())
                .mapToInt(Map.Entry::getValue)
                .toArray();
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
