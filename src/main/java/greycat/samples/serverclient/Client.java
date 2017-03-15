package greycat.samples.serverclient;


import greycat.*;
import greycat.websocket.WSClient;

import java.util.Random;

public class Client {

    private static Random rand = new Random();

    public static void main(String[] args) {
        Graph g = new GraphBuilder()
                .withMemorySize(10000) //cache size before sync to disk
                .withStorage(new WSClient("ws://localhost:8050/ws")) //to save in websocket
                .build();

        g.connect((Boolean isConnected) -> {


            Node sensor = g.newNode(0, System.currentTimeMillis()); //create new node for world 0 and time 0
            sensor.set("snesorId", Type.INT, Math.abs(rand.nextInt()));
            sensor.set("value", Type.INT, rand.nextInt());

            g.index(0,0,"sensors", nodeIndex->{
                g.save(saveResult->{
                    g.index(0,System.currentTimeMillis(),"sensors", nodeIndexSaved->{
                        for (int i=0;i<nodeIndexSaved.size();i++) {
                            System.out.println("\t" + nodeIndexSaved.getAt(i).toString());
                        }
                        g.disconnect(result -> System.out.println("GoodBye!"));
                    });
                });
            });

        });
    }

}
