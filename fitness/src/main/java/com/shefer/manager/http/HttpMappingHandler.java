package com.shefer.manager.http;

import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.Get;
import com.github.vanbv.num.annotation.PathParam;
import com.github.vanbv.num.annotation.Post;
import com.github.vanbv.num.annotation.RequestBody;
import com.github.vanbv.num.json.JsonParser;
import com.shefer.common.command.CommandProcessor;
import com.shefer.common.db.ConnectionProvider;
import com.shefer.common.query.QueryProcessor;
import com.shefer.manager.command.CommandDaoImpl;
import com.shefer.manager.command.commands.AddNewUserCommand;
import com.shefer.manager.command.commands.ExtendMembershipCommand;
import com.shefer.manager.query.QueryDaoImpl;
import com.shefer.manager.query.queries.GetMembershipInfoQuery;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

@ChannelHandler.Sharable
public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private final CommandProcessor commandProcessor;
    private final QueryProcessor queryProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.commandProcessor = new CommandProcessor(new CommandDaoImpl(ConnectionProvider.connect()));
        this.queryProcessor = new QueryProcessor(new QueryDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }

    @Post("/user/new")
    public DefaultFullHttpResponse userNew() {
        return wrapResult(commandProcessor.process(new AddNewUserCommand()));
    }

    public static class ExtendBody {
        private Long userId;
        private String expiryDate;

        public Long getUserId() {
            return userId;
        }

        public String getExpiryDate() {
            return expiryDate;
        }
    }

    @Post("/membership/extend")
    public DefaultFullHttpResponse membershipExtend(@RequestBody ExtendBody extendBody) {
        return wrapResult(commandProcessor.process(new ExtendMembershipCommand(extendBody.getUserId(),
                Date.valueOf(extendBody.getExpiryDate()))));
    }

    @Get("/user/{id}")
    public DefaultFullHttpResponse userGet(@PathParam(value = "id") Long userId) {
        return wrapResult(queryProcessor.process(new GetMembershipInfoQuery(userId)));
    }
}
