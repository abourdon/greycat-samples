/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycat.samples;

import greycat.*;
import greycat.internal.custom.NDTree;
import greycat.ml.profiling.GaussianENode;
import greycat.ml.profiling.GmmManager;
import greycat.struct.EGraph;
import greycat.struct.ProfileResult;
import greycat.utility.distance.Distances;

import java.util.Random;

/**
 * Created by assaad on 21/06/2017.
 */
public class GmmNdTree {
    public static void main(String[] args) {

        Graph graph = GraphBuilder
                .newBuilder()
                .build();

        graph.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {

                //Create a host node
                Node host = graph.newNode(0, 0);

                //Create an egraph behind
                EGraph ndTree = (EGraph) host.getOrCreate("graphNDTree", Type.EGRAPH);

                //Potentially create another Egraph for the gaussian nodes
                //EGraph gmmTree = (EGraph) host.getOrCreate("graphgmm", Type.EGRAPH);

                //Create a gaussian manager
                GmmManager manager = new GmmManager(ndTree);

                //Create a NDTree with a gaussian manager
                NDTree tree = new NDTree(ndTree, manager);

                //set the multidimensional bounds and resolution of the ND Tree, and euclidean distance
                tree.setMinBound(new double[]{0, 0, 0, 0});
                tree.setMaxBound(new double[]{1, 1, 1, 1});
                tree.setResolution(new double[]{0.1, 0.1, 0.1, 0.1});
                tree.setDistance(Distances.EUCLIDEAN);

                //generate 1000 keys with 4 dimensions each
                int len=1000;
                double[][] keys = new double[len][4];

                Random rand = new Random();
                for (int i = 0; i < len; i++) {
                    for(int j=0;j<4;j++){
                        keys[i][j]=rand.nextDouble();
                    }
                }

                //Insert the keys with an occurence of 1
                for (int i = 0; i < len; i++) {
                    tree.insert(keys[i],1);
                }

                //Query the tree with the best 4 results 
                ProfileResult res= tree.queryAround(new double[]{0.4,0.5,0.6,0.7},4);

                //For all the result, get the gaussian from the manager, and show the distance to the querried
                System.out.println("result: "+res.size());
                for(int i=0;i<res.size();i++){
                    int ind= (int)res.value(i);
                    //System.out.println(ind);
                    GaussianENode gn= new GaussianENode(ndTree.node(ind));
                    double[] av1=gn.getAvg();
                    double[] k1=res.keys(i);
                    System.out.println("Id: "+ind+" distance: "+res.distance(i)+" keys: ["+k1[0]+" "+k1[1]+" "+k1[2]+" "+k1[3]+" "+"] gmm avg:["+av1[0]+" "+av1[1]+" "+av1[2]+" "+av1[3]+" "+"] gmm Total: "+gn.getTotal());
                    //gn.print();
                }
            }
        });

    }
}
