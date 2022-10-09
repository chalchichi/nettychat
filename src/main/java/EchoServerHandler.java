import io.netty.channel.ChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String message = (String)msg;

        Channel channel = ctx.channel();
        if(!EchoServer.channels.contains(channel))
        {
            EchoServer.channels.forEach(channel1 ->
                    {
                        channel1.writeAndFlush("<<"+message+">> come in \n");
                    });

            EchoServer.channels.add(channel);
            EchoServer.nicnames.put(channel,message);
        }
        else {
            EchoServer.channels.forEach(channel1 ->
                    {
                        channel1.writeAndFlush(EchoServer.nicnames.get(channel) + " : '" + message + "' \n");
                        System.out.println(message);
                    }
            );
            if ("quit".equals(message)) {
                ctx.close();
            }
        }
    }
}
