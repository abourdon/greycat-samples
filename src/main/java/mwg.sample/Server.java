package mwg.sample;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.LevelDBStorage;
import org.mwg.WSServer;

public class Server {

    public static void main(String[] args) {
        Graph g = new GraphBuilder()
                .withMemorySize(10000) //cache size before sync to disk
                .withStorage(new LevelDBStorage("mwg_db"))
                .build();
        g.connect(isConnected -> {
            new WSServer(g, 8050).start();
            System.out.println("MWG Server listener  :8050");
        });
    }

}
