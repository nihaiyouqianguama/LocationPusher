package com.yitong.locationpusher.dao;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class YhTagInfoDao {

    private List<Double> smoothedPosition = new ArrayList<>();
    private List<Double> smoothedPositionNew = new ArrayList<>();
    private String color;
    private int positionAccuracy;
    private int smoothedPositionAccuracy;
    private List<Zone> zones = new ArrayList<>();
    private String coordinateSystemId;
    private String areaId;
    private String coordinateSystemName;
    private List<Double> covarianceMatrix = new ArrayList<>();
    private String areaName;
    private String name;
    private long positionTS;
    private String id;
    private List<Double> position = new ArrayList<>();
    private long lastZoneTimeStamp;
    private boolean isKey = false;
    private double px;

    private double py;
    private String table_name;
    private boolean key = false;

}
