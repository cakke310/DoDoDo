package com.doruemi.bean;

import java.util.List;

/**
 * Created by seekingL on 2016/7/25.
 *
 */
public class CategoryBean {

    /*{
        "data": [
        {
            "cid": "1",
                "cname": "手机摄影",
                "ptotal": "0",
                "cflag": "1",
                "bgcover": "635959",
                "snumber": "1"
        },
        {
            "cid": "2",
                "cname": "风光",
                "ptotal": "0",
                "cflag": "1",
                "bgcover": "0",
                "snumber": "0"
        }
        ],
        "code": "S001"
    }*/

    public String code;

    public List<CategoryInfo> data;

    public static class CategoryInfo {
        public int cid;

        public String cname;

        public int ptotal;

        public int cflag;

        public String bgcover;

        public int snumber;

        @Override
        public String toString() {
            return "CategoryInfo{" +
                    "cid=" + cid +
                    ", cname='" + cname + '\'' +
                    ", ptotal=" + ptotal +
                    ", cflag=" + cflag +
                    ", bgcover=" + bgcover +
                    ", snumber=" + snumber +
                    '}';
        }
    }
}
