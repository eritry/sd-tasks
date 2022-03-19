package com.shefer;

import com.shefer.dao.CatalogDao;
import com.shefer.handler.HttpHandler;
import com.shefer.handler.HttpHandlerImpl;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import io.reactivex.netty.protocol.http.server.HttpServer;

public class App {
    private static final String DB = "catalog";
    private static final MongoClient client = createMongoClient();
    private static final HttpHandler handler = new HttpHandlerImpl(new CatalogDao(client.getDatabase(DB)));

    public static void main(String[] args) {
        HttpServer
            .newServer(8080)
            .start((req, resp) -> {
                System.out.println(req.getDecodedPath());
                return resp.writeString(handler.makeRequest(req));
            })
            .awaitShutdown();
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
