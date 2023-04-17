package com.yitong.locationpusher.common.mqtt.connection;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @program: location_system_service
 * @description: Mqtt连接工具
 * @author: nickYang
 * @create: 2021-03-23 14:33
 **/
@Component
@Slf4j
public class MqttConnection {

    private static final int chanel = 0;

    MqttClient sampleClient;

    /**
     * 创建mqtt连接客户端
     * sampleClient = new MqttClient(Broker, ClientId, new MemoryPersistence());
     * MqttConnectOptions conSet = new MqttConnectOptions();
     * conSet.setCleanSession(false);
     * conSet.setUserName(UserName);
     * conSet.setPassword(Password.toCharArray());
     * sampleClient.connect(conSet);
     *
     * @return
     */
    public void createConnection() throws MqttException {
        log.info("准备创建mqtt链接 broker [{}],用户名 [{}]", MqttConfig.getBroker(), MqttConfig.getUsername());
        sampleClient = MqttConnectionPool.getConnection();
    }

    public void sendMessage(String content, String topic) throws MqttException {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(chanel);
        sampleClient.publish(topic, message);
    }

    public void closeMqttClient() throws MqttException {
        MqttConnectionPool.backConnection(sampleClient);
    }

    public void step(String json, String topic) {
        try {
            createConnection();
            sendMessage(json, topic);
            closeMqttClient();
            log.info("推送成功");
        } catch (MqttException mqttException) {
            log.error("mqtt消息推送失败 错误信息 [{}]", mqttException.getMessage());
        } catch (Exception e) {
            log.error("mqtt消息推送失败 存在未知错误 错误信息 [{}]", e, getClass());
        }

    }


}
