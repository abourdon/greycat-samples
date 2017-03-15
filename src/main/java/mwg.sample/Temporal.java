package mwg.sample;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.Node;
import org.mwg.Type;

public class Temporal {

    public static void main(String[] args) {

        Graph g = new GraphBuilder().build();
        g.connect(isConnected -> {

            Node sensor0 = g.newNode(0, 0);
            sensor0.set("id", "4494F");
            sensor0.set("name", "sensor0");
            sensor0.set("value", 26.2); //set the value of the sensor

            Node room0 = g.newNode(0, 0);
            room0.set("name", "room0");
            room0.add("sensors", sensor0);

            sensor0.jump(System.currentTimeMillis(), (Node sensor0now) -> {
                sensor0now.set("value", 27.5); //update the value to time: now
                System.out.println("T0:" + sensor0.toString());
                System.out.println("Now:" + sensor0now.toString());

                //now jump over the room
                room0.jump(System.currentTimeMillis(), room0now -> {
                    System.out.println("RoomNow:");
                    room0now.rel("sensors", sensorsNow -> {
                        for (Node sensorNow : sensorsNow) {
                            System.out.println("\t" + sensorNow.toString());
                        }
                        //free !!!
                        g.freeNodes(sensorsNow);
                    });
                });
            });

        });
    }

}
