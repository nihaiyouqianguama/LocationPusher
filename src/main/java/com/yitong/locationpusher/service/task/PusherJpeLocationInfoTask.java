package com.yitong.locationpusher.service.task;

import com.yitong.locationpusher.common.mqtt.connection.MqttConnection;
import com.yitong.locationpusher.common.util.ParseUtils;
import com.yitong.locationpusher.common.util.RequestUtil;
import com.yitong.locationpusher.dao.YhTagInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: LocationPusher
 * @description: jpe数据推送
 * @create: 2023-04-10 14:21
 **/
@Slf4j
@Component
public class PusherJpeLocationInfoTask implements Runnable{

    @Resource
    RequestUtil requestUtil;

    @Resource
    MqttConnection mqttConnection;

    @Value("${topic.mqtt.jpePusher}")
    private  String jpePusher;


    @Override
    public void run() {
        log.info("开始执行JPE定位数据推送");
        List<YhTagInfoDao> jpeTagInfo = requestUtil.getJpeTagInfo();
        mqttConnection.step(ParseUtils.getGson().toJson(jpeTagInfo),jpePusher);
    }
}