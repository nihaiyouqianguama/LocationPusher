package com.yitong.locationpusher.common.mqtt.connection;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class MqttConnectionPool {

    private static List<MqttClient> container = new CopyOnWriteArrayList<>();

    /**
     * 获取连接
     *
     * @return
     */
    public static MqttClient getConnection() {
        if (container.isEmpty()) {
            log.info("当前连接池为空 连接繁忙");
            /*  throw new NullPointerException();*/
            //   initPool();
        }
        return container.remove(0);
    }

    /**
     * 初始化连接池
     */
    @PostConstruct
    public static void initPool() {
        Integer size = MqttConfig.getPoolSize();
        String broker = MqttConfig.getBroker();
        String username = MqttConfig.getUsername();
        String password = MqttConfig.getPassword();
        try {
            for (int i = 0; i < size; i++) {
                MqttClient mqttClient = new MqttClient(broker, UUID.randomUUID().toString(),new MemoryPersistence());
                MqttConnectOptions options = new MqttConnectOptions();
                options.setUserName(username);
                options.setPassword(password.toCharArray());
                mqttClient.connect(options);
                container.add(mqttClient);
            }
        } catch (MqttException e) {
            log.error("mq连接池初始化创建失败 异常信息 [{}]", e.getMessage());
        } catch (Exception e) {
            log.error("mq连接池初始化创建失败 异常信息 [{}]", e.getMessage());
        }

    }

    /**
     * 归还mq连接池
     */
    public static void backConnection(MqttClient mqttClient) {
        if (container.size() >= MqttConfig.getPoolSize()) {
            log.info("容器已满");
            throw new IllegalArgumentException();
        }
        container.add(mqttClient);
        // log.info("添加完成 当前池mq条数[{}]",container.size());
    }


}
