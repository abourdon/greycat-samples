package greycat.samples.serverclient;


import greycat.Graph;
import greycat.GraphBuilder;
import greycat.leveldb.LevelDBStorage;
import greycat.websocket.WSServer;
import greycat.websocket.WSSharedServer;

public class Server {

    public static void main(String[] args) {
        Graph g = new GraphBuilder()
                .withMemorySize(10000) //cache size before sync to disk
                .withStorage(new LevelDBStorage("greycat_db"))
                .build();
        g.connect(isConnected -> {
            new WSSharedServer(g, 8050).start();
            System.out.println("MWG Server listener  :8050");
        });
    }

}
