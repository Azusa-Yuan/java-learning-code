package Asuza.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;

import java.net.URI;
import java.net.URISyntaxException;

public class NettyWebCrawler {

    public static void main(String[] args) throws URISyntaxException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        String url = "http://www.princessconnect.so-net.tw/news/newsDetail/2398";
        URI uri = new URI(url);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch){
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpContentDecompressor());
                        ch.pipeline().addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                // 要发送的 GET 请求的 URL
                                HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
                                HttpHeaders headers = request.headers();
                                headers.add(HttpHeaderNames.HOST, uri.getHost());
                                headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
                                // 设置浏览器 User-Agent
                                headers.add(HttpHeaderNames.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
                                // 发送 GET 请求
                                ctx.writeAndFlush(request);
                            }
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpResponse msg) {
                                // 响应头
                                System.out.println(msg.toString());
                                // 响应体
                                int length = msg.content().readableBytes();
                                byte[] bytes = new byte[length];
                                msg.content().readBytes(bytes);
                                System.out.println(new String(bytes));
                            }
                        });
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect(uri.getHost(), 80).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
