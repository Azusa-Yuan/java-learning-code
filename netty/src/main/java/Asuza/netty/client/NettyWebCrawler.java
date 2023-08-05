package Asuza.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.net.URI;
import java.net.InetSocketAddress;
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
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpContentDecompressor());
                        ch.pipeline().addLast(new HttpObjectAggregator(99999));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpObject>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 要发送的 GET 请求的 URL

//                                FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
//                                request.headers().add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
//                                request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
                                HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
                                HttpHeaders headers = request.headers();
                                headers.set(HttpHeaders.Names.HOST, uri.getHost());
                                headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
                                headers.set(HttpHeaders.Names.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36"); // 设置浏览器 User-Agent
                                System.out.println("发送了吗");
                                // 发送 GET 请求
                                ctx.writeAndFlush(request);
                            }
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
                                System.out.println("有数据吗");
                                System.out.println(msg.toString());
                                if (msg instanceof HttpContent) {
                                    HttpContent content = (HttpContent) msg;
                                    int length = content.content().readableBytes();
                                    byte[] bytes = new byte[length];
                                    content.content().readBytes(bytes);
                                    System.out.println(new String(bytes));
                                }
                            }
                        });
                        // Add other handlers as needed
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

    private static void sendGetRequest(Bootstrap bootstrap, String url) throws Exception {
        URI uri = new URI(url);

        ChannelFuture channelFuture = bootstrap.connect(uri.getHost(), 80).sync();
        channelFuture.channel().closeFuture().sync();

    }
}
