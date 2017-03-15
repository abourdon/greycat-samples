package mwg.sample;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.Node;
import org.mwg.Type;

public class Minimal {

    public static void main(String[] args) {

        Graph g = new GraphBuilder().build();
        g.connect(isConnected -> {
            System.out.println("Connected : " + isConnected);

            Node sensor0 = g.newNode(0, 0); //create new node for world 0 and time 0
            sensor0.set("id", "4494F"); //set the id attribute
            sensor0.set("name", "sensor0"); //set the name attribute

            System.out.println(sensor0); //print {"world":0,"time":0,"id":1,"id":"4494F","name":"sensor0"}

            Node room0 = g.newNode(0, 0); //create new node for world 0 and time 0
            room0.set("name", "room0"); //set the name attribute
            room0.add("sensors", sensor0); //add the sensor0 to the relation sensors of room0

            System.out.println(room0); //print {"world":0,"time":0,"id":2,"name":"room0","sensors":[1]}

            room0.rel("sensors", (Node[] sensors) -> { //iterate over saved sensors
                System.out.println("Relationship Sensors:");
                for (int i = 0; i < sensors.length; i++) {
                    System.out.println("\t" + sensors[i].toString());
                }
                g.disconnect(result -> {
                    System.out.println("GoodBye!");
                });

            });

        });
    }

}
