package com.yitong.locationpusher.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Service
@SuppressWarnings("all")
public class InitServiceImpl {

    @Resource
    RestTemplate restTemplate;

    @Value("${spring.data.base_url}")
    String url;

    private double metersPerPixelX;

    private double metersPerPixelY;

    public double getMetersPerPixelX() {
        return metersPerPixelX;
    }

    public double getMetersPerPixelY() {
        return metersPerPixelY;
    }


    @PostConstruct
    public void init() {
        log.info("stater init.....");
        parseQPEProjectInfo();
    }

    void parseQPEProjectInfo() {
        try {
            String json = restTemplate.getForObject(url + "/data/getBuilding", String.class);
            JsonObject object = JsonParser.parseString(json).getAsJsonObject().get("object").getAsJsonObject();
            pareCoordinateSystem(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void pareCoordinateSystem(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.get("coordinateSystems").getAsJsonArray();
        jsonArray.forEach(array -> {
            JsonObject object = array.getAsJsonObject();
            JsonObject jsonObject1 = object.get("backgroundImages").getAsJsonArray().get(0).getAsJsonObject();
            parseBackgroundImages(jsonObject1);
            log.info("初始化中间层工程文件数据完成!!!");
        });
    }


    /**
     * 解析谈一敏背景图片信息 缺少图片base64信息
     *
     * @param object json对象
     */
    void parseBackgroundImages(JsonObject object) {
        this.metersPerPixelX = object.get("metersPerPixelX").getAsDouble();
        this.metersPerPixelY = object.get("metersPerPixelY").getAsDouble();
    }


}
