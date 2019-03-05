package com.zalizniak.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

public class MainTopology {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("IntegerSpout", new IntegerSpout());
        builder.setBolt("MultiplierBolt", new MultiplierBolt()).shuffleGrouping("IntegerSpout");

        Config config = new Config();
        config.setDebug(true);
        config.setNumWorkers(2);

        //LocalCluster cluster = new LocalCluster();

        try {
            StormSubmitter.submitTopology("HelloTopology", config, builder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // cluster.shutdown();
        }
    }
}
