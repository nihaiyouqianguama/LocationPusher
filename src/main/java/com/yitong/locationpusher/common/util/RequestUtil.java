package com.yitong.locationpusher.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yitong.locationpusher.dao.YhTagInfoDao;
import com.yitong.locationpusher.service.impl.InitServiceImpl;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@ToString
@Data
@SuppressWarnings("ALL")
public class RequestUtil {

    @Value("${spring.data.base_url}")
    public String url;

    @Value("${custom_data.time_out}")
    public Long timeOuts;

    @Resource
    RestTemplate restTemplate;

    @Resource
    InitServiceImpl initService;


    public List<YhTagInfoDao> getTagsInfo() {
        List<YhTagInfoDao> ls = new LinkedList<>();
        try {
            String res = restTemplate.getForObject(url + "/data/getAllTagPosition", String.class);
            JsonArray jsonArray = JsonParser.parseString(res).getAsJsonObject().get("object").getAsJsonArray();
            long l = System.currentTimeMillis();
            jsonArray.forEach((jsonElement -> {
                JsonObject object = jsonElement.getAsJsonObject();
                JsonArray jsonArray1 = object.get("position").getAsJsonArray();
                YhTagInfoDao dao = ParseUtils.getGson().fromJson(jsonElement, YhTagInfoDao.class);
                //5秒延迟
                if (l - dao.getPositionTS() < timeOuts) {
                    dao.setPx(jsonArray1.get(0).getAsDouble() / initService.getMetersPerPixelX());
                    dao.setPy(jsonArray1.get(1).getAsDouble() / initService.getMetersPerPixelY());
                    dao.setColor("");
                    dao.setCoordinateSystemName("");
                    dao.setName("");
                    ls.add(dao);
                }
            }));
        } catch (Exception e) {
            log.info("获取数据异常 错误信息 [{}]", e.getMessage());
        }
        return ls;
    }


    public List<YhTagInfoDao> getJpeTagInfo() {
        String json = restTemplate.getForObject(url + "/data/getAllTagPosition", String.class);
        List<YhTagInfoDao> aoaTagInfoDaos = parseTagPos(json);
        return aoaTagInfoDaos;
    }


    /**
     * 解析aoa标签位置信息
     */
    public List<YhTagInfoDao> parseTagPos(String json) {
        List<YhTagInfoDao> list = new ArrayList<>();
        JsonArray array = JsonParser.parseString(json).getAsJsonObject().get("object").getAsJsonArray();
        array.forEach((jsonElement -> {
            JsonObject object = jsonElement.getAsJsonObject();
            JsonArray jsonArray1 = object.get("position").getAsJsonArray();
            YhTagInfoDao dao = ParseUtils.getGson().fromJson(jsonElement, YhTagInfoDao.class);
            //5秒延迟
            dao.setPx(jsonArray1.get(0).getAsDouble() / initService.getMetersPerPixelX());
            dao.setPy(jsonArray1.get(1).getAsDouble() / initService.getMetersPerPixelY());
            dao.setColor("");
            dao.setCoordinateSystemName("");
            dao.setName("");
            list.add(dao);
        }));
        return list;
    }
}

