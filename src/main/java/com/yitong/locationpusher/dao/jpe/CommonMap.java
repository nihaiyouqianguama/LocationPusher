package com.yitong.locationpusher.dao.jpe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonMap<T, P> {

    private T k;

    private P v;

    /*拼接json格式*/
    public String valueString() {
        return "{\"k\":" + k + ",\"v\":" + v + "}";
    }

    /**
     * 重写hashcode  toString 以及equals 方法 按照自定义的key来做唯一值判断
     *
     * @return 返回boolean string int
     */
    @Override
    public String toString() {
        return k.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.getK().hashCode();
    }
}

