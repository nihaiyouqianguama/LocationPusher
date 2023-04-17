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

@Component
@Slf4j
public class PusherLocationInfoTask implements Runnable {

    @Resource
    RequestUtil requestUtil;

    @Resource
    MqttConnection mqttConnection;

    @Value("${topic.mqtt.pusher:/tagLocation}")
    private  String pushTopic;


    @Override
    public void run() {
        log.info("开始执行定位数据推送");
        List<YhTagInfoDao> list = requestUtil.getTagsInfo();
        mqttConnection.step(ParseUtils.getGson().toJson(list), pushTopic);

    }
}
