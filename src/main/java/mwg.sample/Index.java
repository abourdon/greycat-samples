package mwg.sample;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.Node;

public class Index {

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


            g.index("rooms", room0, "name", processResult -> { //index the node room0
                g.index("sensors", sensor0, "id", processResult2 -> { //index the node sensor0

                    g.indexes(0, System.currentTimeMillis(), (String[] indexNames) -> {
                        System.out.println("Index names in Graph:");
                        for (String indexName : indexNames) {
                            System.out.println("\t" + indexName);
                        }
                    });

                    g.find(0, System.currentTimeMillis(), "rooms", "name=room0", (Node[] rooms) -> {
                        System.out.println("Rooms found by string query:");
                        for (Node roomNow : rooms) {
                            System.out.println("\t" + roomNow.toString());
                        }
                    });

                    g.find(0, System.currentTimeMillis(), "sensors", "id=4494F", (Node[] sensors) -> {
                        System.out.println("Sensors found by string query:");
                        for (Node sensorNow : sensors) {
                            System.out.println("\t" + sensorNow.toString());
                        }
                    });

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
