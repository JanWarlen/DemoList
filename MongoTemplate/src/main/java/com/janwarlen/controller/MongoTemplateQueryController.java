package com.janwarlen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.janwarlen.common.Common;
import com.janwarlen.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "mongoTemplate查询验证")
public class MongoTemplateQueryController {

    /**
     * 注入mongo操作类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * find(Query query, Class<T> entityClass)
     *
     * @return String
     */
    @RequestMapping(value = "/queryMongoFunc1", method = RequestMethod.GET)
    @ApiOperation(notes = "find(Query query, Class<T> entityClass)", value = "mongo查询1", produces = "application/json")
    public String queryMongoFunc1() {
        Query query = Common.getQueryNormal(1, "小明.号");
        List<Student> jsonObjects = mongoTemplate.find(query, Student.class);
        JSONArray array = new JSONArray();
        array.addAll(jsonObjects);
        return array.toJSONString();
    }

    /**
     * find(final Query query, Class<T> entityClass, String collectionName)
     *
     * @return res
     */
    @RequestMapping(value = "/queryMongoFunc2", method = RequestMethod.GET)
    @ApiOperation(notes = "find(final Query query, Class<T> entityClass, String collectionName)", value = "mongo查询2",
            produces = "application/json")
    public String queryMongoFunc2() {
        Query query = Common.getQueryNormal(2, "小明.号");
        List<JSONObject> jsonObjects = mongoTemplate.find(query, JSONObject.class, "student");
        JSONArray array = new JSONArray();
        array.addAll(jsonObjects);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc3", method = RequestMethod.GET)
    @ApiOperation(notes = "findById(Object id, Class<T> entityClass)", value = "mongo查询3", produces =
            "application/json")
    public String queryMongoFunc3() {
        Student res = mongoTemplate.findById("201801102003", Student.class);
        JSONArray array = new JSONArray();
        array.add(res);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc4", method = RequestMethod.GET)
    @ApiOperation(notes = "findById(Object id, Class<T> entityClass, String collectionName)", value = "mongo查询4",
            produces = "application/json")
    public String queryMongoFunc4() {
        JSONObject res = mongoTemplate.findById("1234567890", JSONObject.class, "student");
        JSONArray array = new JSONArray();
        array.add(res);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc5", method = RequestMethod.GET)
    @ApiOperation(notes = "findOne(Query query, Class<T> entityClass)", value = "mongo查询5",
            produces = "application/json")
    public String queryMongoFunc5() {
        Student res = mongoTemplate.findOne(Common.getQueryNormal(2, "小明.号"), Student.class);
        JSONArray array = new JSONArray();
        array.add(res);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc6", method = RequestMethod.GET)
    @ApiOperation(notes = "findOne(Query query, Class<T> entityClass, String collectionName)", value = "mongo查询6",
            produces = "application/json")
    public String queryMongoFunc6() {
        JSONObject res = mongoTemplate.findOne(Common.getQueryNormal(1, "小明.号"), JSONObject.class, "student");
        JSONArray array = new JSONArray();
        array.add(res);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc7", method = RequestMethod.GET)
    @ApiOperation(notes = "findAll(Class<T> entityClass)", value = "mongo查询7", produces = "application/json")
    public String queryMongoFunc7() {
        List<Student> res = mongoTemplate.findAll(Student.class);
        JSONArray array = new JSONArray();
        array.addAll(res);
        return array.toJSONString();
    }

    @RequestMapping(value = "/queryMongoFunc8", method = RequestMethod.GET)
    @ApiOperation(notes = "findAll(Class<T> entityClass, String collectionName)", value = "mongo查询8", produces =
            "application/json")
    public String queryMongoFunc8() {
        List<JSONObject> res = mongoTemplate.findAll(JSONObject.class, "student");
        JSONArray array = new JSONArray();
        array.addAll(res);
        return array.toJSONString();
    }

}
