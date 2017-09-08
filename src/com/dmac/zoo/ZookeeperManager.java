package com.dmac.zoo;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by dharshekthvel on 18/8/17.
 */
public class ZookeeperManager {

    public static void main(String args[]) throws IOException, InterruptedException, KeeperException {

        ZooKeeper zookeeper = new ZooKeeper("localhost", 10, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                System.out.println(watchedEvent.getState());
            }
        });



        byte[] data = "My first zookeeper app".getBytes();

       zookeeper.create("/ARAVINDH-TOPIC12", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


//        while (true) {
//
//            System.out.print("\n"+zookeeper.getSessionId());
//        }

    }
}
