package vk.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import java.util.Date;

public class VkRequesterImpl implements VkRequester {

    private final VkApiClient vkApiClient;
    private final UserActor userActor;

    public VkRequesterImpl(VkApiClient client, UserActor user) {
        vkApiClient = client;
        userActor = user;
    }

    @Override
    public int count(String tag, Date start, Date end) {
        int startDate = (int)start.toInstant().getEpochSecond();
        int endDate = (int)end.toInstant().getEpochSecond();
        String page = "";
        int result = 0;
        while (page != null) {
            try {
                SearchResponse response = vkApiClient.newsfeed().
                        search(userActor).q(tag)
                        .count(200)
                        .startTime(startDate).endTime(endDate)
                        .startFrom(page)
                        .execute();
                page = response.getNextFrom();
                result += response.getItems().size();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
