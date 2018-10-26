package com.janwarlen.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Common {

    public static String successMsg() {
        JSONObject res = new JSONObject();
        res.put("res", "success");
        return res.toJSONString();
    }

    public static String successMsg(Object obj) {
        JSONObject res = new JSONObject();
        res.put("data", obj);
        res.put("res", "success");
        return res.toJSONString();
    }


    /**
     * 相当于
     * db.getCollection('student').find({"grade":1,"name":/小明.号/,"birth":{$lt:ISODate("2018-02-10T12:04:51.367Z"),
     * $gt:ISODate("2018-01-09T12:04:51.367Z")}})
     * <p>
     * "$gt" 、"$gte"、 "$lt"、 "$lte"(分别对应">"、 ">=" 、"<" 、"<=")
     *
     * @return 1
     */
    public static Query getQueryNormal(int grade, String nameRegex) {
        List<Criteria> criteriaList = new ArrayList<>();

        Criteria gradeC = Criteria.where("grade").is(grade);
        Criteria name = Criteria.where("name").regex(nameRegex);
        Date start = new Date();
        start.setTime(1505556091000L);
        Criteria birthg = Criteria.where("birth").gt(start);
        Date end = new Date();
        end.setTime(1525558091000L);
        Criteria birthl = Criteria.where("birth").lt(end);

        //此处切记不可使用以下写法：
        /*Date date = new Date();
        date.setTime(1505556091000L);
        Criteria birthg = Criteria.where("birth").gt(date);
        end.setTime(1525558091000L);
        Criteria birthl = Criteria.where("birth").lt(date);*/
        //以上写法date仅仅是引用了地址，具体问题可以参照浅拷贝和深拷贝问题

        criteriaList.add(gradeC);
        criteriaList.add(name);
        criteriaList.add(birthg);
        criteriaList.add(birthl);

        Criteria[] criterias = new Criteria[criteriaList.size()];
        criteriaList.toArray(criterias);
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(criterias);
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 限制查询
     *
     * @param nameRegex 姓名正则
     * @param limit     limit数量
     * @return query
     */
    public static Query getQueryWithLimit(String nameRegex, int limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(nameRegex));
        query.limit(limit);
        return query;
    }
}
