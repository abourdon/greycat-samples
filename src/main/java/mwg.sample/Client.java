package mwg.sample;

import org.mwg.*;

import java.util.Random;

public class Client {

    private static Random rand = new Random();

    public static void main(String[] args) {
        Graph g = new GraphBuilder()
                .withMemorySize(10000) //cache size before sync to disk
                .withStorage(new WSClient("ws://127.0.0.1:8050"))
                .build();
        g.connect(isConnected -> {

            Node sensor = g.newNode(0, System.currentTimeMillis()); //create new node for world 0 and time 0
            sensor.set("id", Math.abs(rand.nextInt()));
            sensor.set("value", rand.nextInt());
            g.index("sensors", sensor, "id", res -> {
                g.save(saveResult -> {
                    g.findAll(0, System.currentTimeMillis(), "sensors", allSensorsNow -> {
                        System.out.println("All sensors indexed:");
                        for (Node sensorNow : allSensorsNow) {
                            System.out.println("\t" + sensorNow.toString());
                        }
                        g.disconnect(result -> System.out.println("GoodBye!"));
                    });
                });
            });



        });
    }

}
