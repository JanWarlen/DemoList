package com.janwarlen.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.janwarlen.common.Common;
import com.janwarlen.entity.Student;
import com.janwarlen.entity.Teacher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * mongo插入验证
 */
@RestController
@Api(tags = "mongoTemplate插入验证")
public class MongoTemplateInsertController {

    /**
     * 注入mongo操作类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入mongo方式1
     *
     * @param req 请求参数
     * @return 返回请求结果
     */
    @RequestMapping(value = "insertIntoMongoFunc1", method = RequestMethod.POST)
    @ApiOperation(notes = "insert(Object objectToSave)", value = "插入mongo方式1", produces =
            "application/json")
    public String insertIntoMongoFunc1(@RequestBody JSONObject req) {
        //此处未指定collection名称，将会插入对象className的collection中，若此collection不存在，则创建
        //此处插入的collection名称为jSONObject
//        mongoTemplate.insert(req);
        Student student = new Student();
        student.setGrade(1);
        student.setId("201801102003");
        student.setName("小明2号");
        student.setBirth(new Date());
        mongoTemplate.insert(student);
        return Common.successMsg();
    }

    /**
     * 插入mongo方式2
     *
     * @param req 请求参数
     * @return 返回请求结果
     */
    @RequestMapping(value = "insertIntoMongoFunc2", method = RequestMethod.POST)
    @ApiOperation(notes = "insert(Object objectToSave, String collectionName)", value = "插入mongo方式2", produces =
            "application/json")
    public String insertIntoMongoFunc2(@RequestBody JSONObject req) {
        //将对象插入指定的collection中
        mongoTemplate.insert(req, "test");
        return Common.successMsg();
    }

    /**
     * 插入mongo方式3
     *
     * @param req 请求数组
     * @return 返回成功
     */
    @RequestMapping(value = "insertIntoMongoFunc3", method = RequestMethod.POST)
    @ApiOperation(notes = "insert(Collection<? extends Object> batchToSave, Class<?> entityClass)", value =
            "插入mongo方式3", produces = "application/json")
    public String insertIntoMongoFunc3(@RequestBody JSONArray req) {
        //此处未指定collection名称，将会插入对象className的collection中，若此collection不存在，则创建.此处为集合批量插入
        //此处插入的collection名称为jSONArray，插入数据条数为JSONArray数组大小
        mongoTemplate.insert(req, JSONArray.class);
        return Common.successMsg();
    }

    /**
     * 插入mongo方式4
     *
     * @param req 请求数组
     * @return 返回成功
     */
    @RequestMapping(value = "insertIntoMongoFunc4", method = RequestMethod.POST)
    @ApiOperation(notes = "insert(Collection<? extends Object> batchToSave, String collectionName)", value =
            "插入mongo方式4", produces = "application/json")
    public String insertIntoMongoFunc4(@RequestBody JSONArray req) {
        //将对象插入指定的collection中，此处为集合批量插入
        mongoTemplate.insert(req, "test2");
        return Common.successMsg();
    }

    @RequestMapping(value = "insertIntoMongoFunc5", method = RequestMethod.GET)
    @ApiOperation(notes = "insertAll(Collection<? extends Object> objectsToSave)", value =
            "插入mongo方式5", produces = "application/json")
    public String insertIntoMongoFunc5() {
        List<Object> list = new ArrayList<>();
        Student student = new Student();
        student.setGrade(1);
        student.setId("1234567890");
        student.setName("小明");
        Teacher teacher = new Teacher();
        teacher.setId("0987654321");
        teacher.setName("老王");
        teacher.setSubject("physics");
        list.add(student);
        list.add(teacher);
        //在list中的每个对象分别插入对象class类名的collection中，逐条
        mongoTemplate.insertAll(list);
        return Common.successMsg();
    }

    /**
     * 此方法为异常方法，因使用错误变量类型
     *
     * @param req 请求数组
     * @return 返回成功
     */
    @RequestMapping(value = "insertIntoMongoFunc6", method = RequestMethod.POST)
    @ApiOperation(notes = "insertAll(Collection<? extends Object> objectsToSave)", value =
            "插入mongo方式5(异常)", produces = "application/json")
    public String insertIntoMongoFunc6(@RequestBody JSONArray req) {
        //此处直接使用JSONArray会抛出异常，org.springframework.dao.InvalidDataAccessApiUsageException: No PersistentEntity
        // information found for class java.util
        // .LinkedHashMap，猜测应该是JSONArray中的JSONObject在@RequestBody映射（？不太确定用词是否准确）时并没有转换为JSONObject，
        // 而是转换为了LinkedHashMap，另关于其他Map类型是否会出现同样错误未曾实验，源码阅读出现未能理解代码，希望有同学能够解惑
        mongoTemplate.insertAll(req);
        return Common.successMsg();
    }

    /**
     * 测试insertAll方法使用jsonArray中包含jsonObj效果
     *
     * @return 返回成功
     */
    @RequestMapping(value = "insertIntoMongoFunc7", method = RequestMethod.GET)
    @ApiOperation(notes = "insertAll(Collection<? extends Object> objectsToSave)", value =
            "插入mongo方式5（测试JSONObject）", produces = "application/json")
    public String insertIntoMongoFunc7() {
        JSONArray list = new JSONArray();
        JSONObject first = new JSONObject();
        first.put("testJsonobj", "testValue1");
        JSONObject second = new JSONObject();
        second.put("testJsonobj", "testValue2");
        list.add(first);
        list.add(second);
        mongoTemplate.insertAll(list);
        return Common.successMsg();
    }

    /**
     * 使用save
     * @param req 请求
     * @return 返回成功
     */
    @RequestMapping(value = "saveIntoMongoFunc1", method = RequestMethod.POST)
    @ApiOperation(notes = "save(Object objectToSave)", value = "插入mongo方式6", produces = "application/json")
    public String saveIntoMongoFunc1(@RequestBody JSONObject req) {
        //save 方法同样使用mappingContext.getPersistentEntity(type),但与insertAll不同的是，此处不针对对象内部对象
        //预计使用jsonArray将会插入一条数据在jSONArray的collection中
        mongoTemplate.save(req);
        return Common.successMsg();
    }

    /**
     * 测试save保存JSONArray
     * @param req 请求
     * @return 返回成功
     */
    @RequestMapping(value = "saveIntoMongoFunc2", method = RequestMethod.POST)
    @ApiOperation(notes = "save(Object objectToSave)", value = "测试保存JSONArray", produces = "application/json")
    public String saveIntoMongoFunc2(@RequestBody JSONArray req) {
        //java.lang.ClassCastException: com.mongodb.BasicDBObject cannot be cast to com.mongodb.BasicDBList
//        mongoTemplate.save(req);


        List<Object> list = new ArrayList<>();
        Student student = new Student();
        student.setGrade(1);
        student.setId("1234567890");
        student.setName("小明");
        Teacher teacher = new Teacher();
        teacher.setId("0987654321");
        teacher.setName("老王");
        teacher.setSubject("physics");
        list.add(student);
        list.add(teacher);
        mongoTemplate.save(list);
        return Common.successMsg();
    }

    /**
     * 指定collection存储对象
     * @param req 请求
     * @return 返回成功
     */
    @RequestMapping(value = "saveIntoMongoFunc3", method = RequestMethod.POST)
    @ApiOperation(notes = "save(Object objectToSave, String collectionName)", value = "插入mongo方式7", produces = "application/json")
    public String saveIntoMongoFunc3(@RequestBody JSONObject req) {
        mongoTemplate.save(req,"test2");
        return Common.successMsg();
    }
}
