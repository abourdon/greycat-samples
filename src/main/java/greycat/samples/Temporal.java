package greycat.samples;

import greycat.Graph;
import greycat.GraphBuilder;
import greycat.Node;
import greycat.Type;

public class Temporal {

    public static void main(String[] args) {

        Graph g = new GraphBuilder().build();
        g.connect(isConnected -> {

            Node sensor0 = g.newNode(0, 0);
            sensor0.set("id", Type.STRING, "4494F");
            sensor0.set("name", Type.STRING, "sensor0");
            sensor0.set("value", Type.DOUBLE, 26.2); //set the value of the sensor

            Node room0 = g.newNode(0, 0);
            room0.set("name", Type.STRING, "room0");
            room0.addToRelation("sensors", sensor0);

            sensor0.travelInTime(System.currentTimeMillis(), (Node sensor0now) -> {
                sensor0now.set("value", Type.DOUBLE, 27.5); //update the value to time: now
                System.out.println("T0:" + sensor0.toString());
                System.out.println("Now:" + sensor0now.toString());

                //now jump over the room
                room0.travelInTime(System.currentTimeMillis(), room0now -> {
                    System.out.println("RoomNow:");
                    room0now.relation("sensors", sensorsNow -> {
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
