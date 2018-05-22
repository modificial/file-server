package com.codingapi.fileserver.db.fastdfs.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description 服务工厂类
 */
public class TrackerServerFactory extends BasePooledObjectFactory<TrackerServer> {
    /**
     * 创建服务端和客户端
     *
     * @return
     * @throws Exception
     */
    @Override
    public TrackerServer create() throws Exception {
        // TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        // TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        return trackerServer;
    }

    /**
     * 构建连接池
     *
     * @param trackerServer
     * @return
     */
    @Override
    public PooledObject<TrackerServer> wrap(TrackerServer trackerServer) {
        return new DefaultPooledObject<TrackerServer>(trackerServer);
    }
}

