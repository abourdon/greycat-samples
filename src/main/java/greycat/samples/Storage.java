package greycat.samples;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Node;
import greycat.Type;
import greycat.leveldb.LevelDBStorage;

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
            sensor.set("id", Type.INT, Math.abs(rand.nextInt()));
            sensor.set("value", Type.INT, rand.nextInt());
            g.index(0,0,"sensors", res -> {
                g.save(saveResult -> {
                    g.index(0, System.currentTimeMillis(), "sensors", nodeIndex -> {
                        System.out.println("All sensors indexed:");
                        for(int i=0;i<nodeIndex.size();i++){
                            System.out.println("\t" + nodeIndex.getAt(i).toString());
                        }

                    });
                });
            });

        });

    }

}
