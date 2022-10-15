import io.netty.channel.ChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        String message = (String)msg;
        System.out.println(msg);
        Channel channel = ctx.channel();
        channel.writeAndFlush(message.concat("\n"));
        if(!EchoServer.channels.contains(channel))
        {
            EchoServer.channels.forEach(channel1 ->
                    {
                        channel1.writeAndFlush(message.concat("\n"));
                    });

            EchoServer.channels.add(channel);
            EchoServer.nicnames.put(channel,message);
        }
        else {
            EchoServer.channels.forEach(channel1 ->
                    {
                        //channel1.writeAndFlush(EchoServer.nicnames.get(channel) + " : '" + message + "' \n");
                        channel1.writeAndFlush(message.concat("\n"));
                        System.out.println(message.concat("\n"));
                    }
            );
            if ("quit".equals(message)) {
                ctx.close();
            }
        }
    }
}
