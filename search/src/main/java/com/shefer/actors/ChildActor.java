package com.shefer.actors;

import akka.actor.AbstractActor;
import com.google.gson.Gson;
import com.shefer.search.SearchClient;
import com.shefer.search.SearchEngineResult;

import java.util.Map;

public class ChildActor extends AbstractActor {

    private final Map<String, Long> sleepDuration = Map.of(
            "Google", 500L,
            "Yandex", 600L,
            "Bing", 700L);

    SearchClient client;

    ChildActor(SearchClient client) {
        this.client = client;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::getSearchResult).build();
    }

    private void getSearchResult(String query) {
        String resultJson = client.makeSearchRequest(query);
        Gson g = new Gson();
        SearchEngineResult engineResult = g.fromJson(resultJson, SearchEngineResult.class);

        try {
            Thread.sleep(sleepDuration.get(engineResult.getEngineName()));
            getSender().tell(engineResult, getSender());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
