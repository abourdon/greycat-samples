package mwg.sample;

import org.mwg.Constants;
import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.ml.MLPlugin;
import org.mwg.ml.RegressionNode;
import org.mwg.ml.algorithm.regression.PolynomialNode;

public class Polynomial {

    public static void main(String[] args) {
        Graph g = new GraphBuilder().withPlugin(new MLPlugin()).build();
        g.connect(isConnected -> {
            RegressionNode regressionNode = (RegressionNode) g.newTypedNode(0, 0, "PolynomialNode");

            for (int i = 0; i < 100; i++) {
                int finalI = i;
                regressionNode.jump(i, (RegressionNode nodeTi) -> {
                    nodeTi.set("value", finalI);
                    //nodeTi.learn(finalI,null);
                });
            }

            regressionNode.timepoints(Constants.BEGINNING_OF_TIME, Constants.END_OF_TIME, (long[] times) -> {
                System.out.println("Nb of times:" + times.length);
            });

            //print 50.0
            regressionNode.jump(50, n50 -> System.out.println(n50.get("value")));

            //print {"world":0,"time":0,"id":1"polynomial": "-0.0+(1.0*t)"}
            System.out.println(regressionNode.toString());

        });
    }

}
