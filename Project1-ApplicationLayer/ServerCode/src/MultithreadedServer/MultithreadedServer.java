package MultithreadedServer;

import java.io.IOException;

public class MultithreadedServer {
    public static void main(String[] args) throws IOException {
        NFTNetServer Server = new NFTNetServer(NFTNetServer.DEFAULT_SERVER_PORT);
    }

}
