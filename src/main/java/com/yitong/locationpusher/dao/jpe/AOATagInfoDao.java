package com.yitong.locationpusher.dao.jpe;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"tagId"})
public class AOATagInfoDao {

    private String coorSysId;

    private String coorSysName;

    private String areaId;

    private String areaName;

    private String tagId;

    private String tagName;

    private List<Double> tagPos;

    private Long posTS;

    private String mainStationId;

    private Double rssiValue;

    private CommonMap<String, String> room;

    private String mapId;

    private Double battery;

    private String floorName;
}
