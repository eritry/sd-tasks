package com.shefer.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.shefer.search.SearchEngineResult;
import com.shefer.search.StubSearchClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MasterActor extends AbstractActor {
    private final List<SearchEngineResult> results;
    private ActorRef mainSender;
    private final Duration timeout;
    private final List<String> SEARCH_ENGINES = List.of("google", "yandex", "bing");
    private static final TimeoutMessage TIMOUT_MSG = new TimeoutMessage();

    MasterActor(Duration timeout) {
        results = new ArrayList<>();
        this.timeout = timeout;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    mainSender = getSender();

                    SEARCH_ENGINES.forEach(searchEngine ->
                            createChildActor(searchEngine, msg));

                    context().system()
                            .scheduler()
                            .scheduleOnce(
                                    timeout,
                                    () -> self().tell(TIMOUT_MSG, ActorRef.noSender()),
                                    context().system().dispatcher());
                })
                .match(TimeoutMessage.class, msg -> sendResult())
                .match(SearchEngineResult.class, msg -> {
                    results.add(msg);

                    if (results.size() == SEARCH_ENGINES.size()) {
                        sendResult();
                    }
                })
                .build();
    }

    private void createChildActor(String searchEngine, String request) {
        getContext().actorOf(
                Props.create(ChildActor.class, new StubSearchClient(searchEngine)),
                searchEngine
        ).tell(request, self());
    }

    private void sendResult() {
        mainSender.tell(new SearchResults(results), self());
        getContext().stop(self());
    }

    public static class SearchResults {
        List<SearchEngineResult> results;

        public SearchResults(List<SearchEngineResult> results) {
            this.results = results;
        }

        public List<SearchEngineResult> getResults() {
            return results;
        }
    }

    private static final class TimeoutMessage {
    }
}
