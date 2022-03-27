package com.shefer.visit.http;

import java.nio.charset.StandardCharsets;

import com.github.vanbv.num.annotation.RequestBody;
import com.shefer.common.db.ConnectionProvider;
import com.shefer.visit.command.CommandDaoImpl;
import com.shefer.common.command.CommandProcessor;
import com.shefer.visit.command.commands.EnterCommand;
import com.shefer.visit.command.commands.ExitCommand;
import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.Post;
import com.github.vanbv.num.json.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

@ChannelHandler.Sharable
public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private final CommandProcessor commandProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.commandProcessor = new CommandProcessor(new CommandDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }

    public static class EnterBody {
        private Long userId;

        public Long getUserId() {
            return userId;
        }
    }

    @Post("/enter")
    public DefaultFullHttpResponse enter(@RequestBody EnterBody body) {
        return wrapResult(commandProcessor.process(new EnterCommand(body.getUserId(), System.currentTimeMillis())));
    }

    public static class ExitBody {
        private Long userId;

        public Long getUserId() {
            return userId;
        }
    }

    @Post("/exit")
    public DefaultFullHttpResponse exit(@RequestBody ExitBody body) {
        return wrapResult(commandProcessor.process(new ExitCommand(body.getUserId(), System.currentTimeMillis())));
    }
}
