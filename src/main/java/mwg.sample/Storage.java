package mwg.sample;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.LevelDBStorage;
import org.mwg.Node;

import java.util.Random;

/**
 * Run this class several time and you will see nodes reused across execution. Delete the directory and mwg_db and see the effect
 */
public class Storage {

    private static Random rand = new Random();

    public static void main(String[] args) {

        Graph g = new GraphBuilder()
                .withMemorySize(10000) //cache size before sync to disk
                .withStorage(new LevelDBStorage("mwg_db"))
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

                    });
                });
            });

        });

    }

}
