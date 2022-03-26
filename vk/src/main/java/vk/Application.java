package vk;

import vk.counter.Counter;
import vk.counter.CounterImpl;
import vk.vk.VkRequester;
import vk.vk.VkRequesterImpl;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.util.Arrays;
import java.util.function.Function;

public class Application {
    private static final int USER_ID = 7966809;
    private static final String ACCESS_TOKEN = "e80d8a02e80d8a02e80d8a027ce8741a5bee80de80d8a028957b3f78c4cc6f2a842cf14";

    public static void main(String[] args) {
        final String tag = parseArgument(args, 0, Function.identity());
        final int hours = parseArgument(args, 1, Integer::parseInt);

        final VkApiClient vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
        final UserActor userActor = new UserActor(USER_ID, ACCESS_TOKEN);

        final VkRequester vkRequester = new VkRequesterImpl(vkApiClient, userActor);
        final Counter counter = new CounterImpl(vkRequester);

        final int[] frequencies = counter.count(tag, hours);
        System.out.println(Arrays.toString(frequencies));
    }

    private static <T> T parseArgument(String[] args, int index, Function<String, T> transformer) {
        if (args.length <= index) {
            throw new IllegalArgumentException("Missed argument on position " + (index + 1) + ".");
        }
        try {
            return transformer.apply(args[index]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid argument on position" + (index + 1) + ".", e);
        }
    }
}
