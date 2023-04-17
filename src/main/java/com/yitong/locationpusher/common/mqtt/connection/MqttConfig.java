package com.yitong.locationpusher.common.mqtt.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @program: location_service_v2
 * @description: mqtt的配置文件
 * @author: Mr.itYang
 * @create: 2021-11-06 20:28
 **/
@Configuration
public class MqttConfig {

    private static Integer poolSize;

    private static String broker;

    private static String username;

    private static String password;

    @Value("${mqtt.config.poolSize}")
    public  void setPoolSize(Integer poolSize) {
        MqttConfig.poolSize = poolSize;
    }

    @Value("${mqtt.config.broker}")
    public  void setBroker(String broker) {
        MqttConfig.broker = broker;
    }

    @Value("${mqtt.config.username}")
    public  void setUsername(String username) {
        MqttConfig.username = username;
    }

    @Value("${mqtt.config.password}")
    public  void setPassword(String password) {
        MqttConfig.password = password;
    }

    public static Integer getPoolSize() {
        return poolSize;
    }

    public static String getBroker() {
        return broker;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
