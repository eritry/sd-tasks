package vk.counter;

import vk.vk.VkRequester;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CounterImpl implements Counter {
    private final VkRequester vkRequester;

    public CounterImpl(VkRequester requester) {
        vkRequester = requester;
    }

    @Override
    public int[] count(String hashTag, int hours) {

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= hours; i++) {
            final Date endDate = java.sql.Timestamp.valueOf(LocalDateTime.now().minusHours(i - 1));
            final Date startDate = java.sql.Timestamp.valueOf(LocalDateTime.now().minusHours(i));
            result.add(vkRequester.count(hashTag, startDate, endDate));
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
