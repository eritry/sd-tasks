package com.shefer.manager;

import com.shefer.common.http.ServerRunner;
import com.shefer.manager.http.HttpMappingHandler;
import com.github.vanbv.num.json.JsonParserDefault;

public class Main {
    private static final int PORT = 2222;

    public static void main(String[] args) throws Exception {
        ServerRunner.runServer(PORT, new HttpMappingHandler(new JsonParserDefault()));
    }
}
