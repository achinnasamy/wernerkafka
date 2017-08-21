package com.dmac.kafkaadmin;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * Created by dharshekthvel on 19/8/17.
 */
public class KafkaAdministratorManager {


    public static void main(String args[]) {

        ZkClient zkClient = new ZkClient(
                "127.0.0.1:2181",
                100000,
                100000,
                ZKStringSerializer$.MODULE$);

        //ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection("127.0.0.1:2181"), 0);


        //AdminUtils.createTopic(zkUtils, "MYTOPIC09", 1, 1, topicConfig, RackAwareMode.Enforced$.MODULE$);
        zkClient.close();



    }
}
