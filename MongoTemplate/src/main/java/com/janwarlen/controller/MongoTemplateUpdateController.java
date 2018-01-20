package com.janwarlen.controller;

import com.alibaba.fastjson.JSONObject;
import com.janwarlen.common.Common;
import com.janwarlen.entity.Student;
import com.mongodb.WriteResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "mongoTemplate修改验证")
public class MongoTemplateUpdateController {

    /**
     * 注入mongo操作类
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * mongo 修改1
     *
     * @return 返回数据
     */
    @RequestMapping(value = "/updateMongoFunc1", method = RequestMethod.GET)
    @ApiOperation(notes = "updateFirst(Query query, Update update, Class<?> entityClass)", value = "mongo修改1",
            produces = "application/json")
    public String updateMongoFunc1() {
        Update update = new Update();
        update.set("grade", 1);
        WriteResult writeResult = mongoTemplate.updateFirst(Common.getQueryNormal(2, "小明.号"), update, Student.class);
        return Common.successMsg(writeResult.getN());
    }

    @RequestMapping(value = "/updateMongoFunc2", method = RequestMethod.GET)
    @ApiOperation(notes = "updateFirst(final Query query, final Update update, final String collectionName)", value =
            "mongo修改2", produces = "application/json")
    public String updateMongoFunc2() {
        Update update = new Update();
        update.set("grade", 2);
        WriteResult student = mongoTemplate.updateFirst(Common.getQueryNormal(1, "小明.号"), update, "student");
        return Common.successMsg(student.getN());
    }

    @RequestMapping(value = "/updateMongoFunc3", method = RequestMethod.GET)
    @ApiOperation(notes = "updateFirst(Query query, Update update, Class<?> entityClass, String collectionName)",
            value = "mongo修改3", produces = "application/json")
    public String updateMongoFunc3() {
        Update update = new Update();
        update.set("grade", 1);
        WriteResult student = mongoTemplate.updateFirst(Common.getQueryNormal(2, "小明2号"), update, Student.class,
                "student");
        return Common.successMsg(student.getN());
    }

    @RequestMapping(value = "/updateMongoFunc4", method = RequestMethod.GET)
    @ApiOperation(notes = "updateMulti(Query query, Update update, Class<?> entityClass)", value = "mongo修改4",
            produces = "application/json")
    public String updateMongoFunc4() {
        Update update = new Update();
        update.set("grade", 3);
        WriteResult writeResult = mongoTemplate.updateMulti(Common.getQueryNormal(1, "小明.号"), update, Student.class);
        return Common.successMsg(writeResult.getN());
    }

    @RequestMapping(value = "/updateMongoFunc5", method = RequestMethod.GET)
    @ApiOperation(notes = "updateMulti(final Query query, final Update update, String collectionName)", value =
            "mongo修改5", produces = "application/json")
    public String updateMongoFunc5() {
        Update update = new Update();
        update.set("grade", 4);
        WriteResult writeResult = mongoTemplate.updateMulti(Common.getQueryNormal(2, "小明.号"), update, "student");
        return Common.successMsg(writeResult.getN());
    }

    @RequestMapping(value = "/updateMongoFunc6", method = RequestMethod.GET)
    @ApiOperation(notes = "updateMulti(final Query query, final Update update, Class<?> entityClass, String " +
            "collectionName)", value = "mongo修改6", produces = "application/json")
    public String updateMongoFunc6() {
        Update update = new Update();
        update.set("grade", 1);
        WriteResult writeResult = mongoTemplate.updateMulti(Common.getQueryNormal(3, "小明.号"), update, Student.class,
                "student");
        return Common.successMsg(writeResult.getN());
    }

    @RequestMapping(value = "/updateMongoFunc7", method = RequestMethod.GET)
    @ApiOperation(notes = "upsert(Query query, Update update, Class<?> entityClass)", value = "mongo修改7", produces =
            "application/json")
    public String updateMongoFunc7() {
        Update update = new Update();
        update.set("grade", 3);
        update.set("name", "upsert1");
        WriteResult upsert = mongoTemplate.upsert(Common.getQueryNormal(3, "小明2号"), update, Student.class);
        return Common.successMsg(upsert.getN());
    }

    @RequestMapping(value = "/updateMongoFunc8", method = RequestMethod.GET)
    @ApiOperation(notes = "upsert(Query query, Update update, String collectionName)", value = "mongo修改8", produces =
            "application/json")
    public String updateMongoFunc8() {
        Update update = new Update();
        update.set("grade", 3);
        update.set("name", "upsert2");
        WriteResult upsert = mongoTemplate.upsert(Common.getQueryNormal(3, "小明2号"), update, "student");
        return Common.successMsg(upsert.getN());
    }

    @RequestMapping(value = "/updateMongoFunc9", method = RequestMethod.GET)
    @ApiOperation(notes = "upsert(Query query, Update update, Class<?> entityClass, String collectionName)", value =
            "mongo修改9", produces = "application/json")
    public String updateMongoFunc9() {
        Update update = new Update();
        update.set("grade", 3);
        update.set("name", "upsert3");
        WriteResult upsert = mongoTemplate.upsert(Common.getQueryNormal(3, "小明2号"), update, Student.class, "student");
        return Common.successMsg(upsert.getN());
    }

    @RequestMapping(value = "/updateMongoFunc10", method = RequestMethod.GET)
    @ApiOperation(notes = "findAndModify(Query query, Update update, Class<T> entityClass)", value = "mongo修改10",
            produces = "application/json")
    public String updateMongoFunc10() {
        Update update = new Update();
        update.set("grade", 2);
        Student andModify = mongoTemplate.findAndModify(Common.getQueryNormal(1, "小明2号"), update, Student.class);
        return Common.successMsg(andModify);
    }

    @RequestMapping(value = "/updateMongoFunc11", method = RequestMethod.GET)
    @ApiOperation(notes = "findAndModify(Query query, Update update, Class<T> entityClass, String collectionName)",
            value = "mongo修改11",
            produces = "application/json")
    public String updateMongoFunc11() {
        Update update = new Update();
        update.set("grade", 1);
        Student andModify = mongoTemplate.findAndModify(Common.getQueryNormal(2, "小明2号"), update, Student.class,
                "student");
        return Common.successMsg(andModify);
    }

    @RequestMapping(value = "/updateMongoFunc12", method = RequestMethod.GET)
    @ApiOperation(notes = "findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> " +
            "entityClass)",
            value = "mongo修改12",
            produces = "application/json")
    public String updateMongoFunc12() {
        Update update = new Update();
        update.set("grade", 2);
        FindAndModifyOptions options = new FindAndModifyOptions();
        //默认false
//        options.returnNew(true); //是否返回更新后数据
        options.upsert(true);   //是否更新（不存在则插入）
//        options.remove(true); //是否删除，为true时不会更新操作，如果returnNew为true则返回旧数据,并且删除
        Student andModify = mongoTemplate.findAndModify(Common.getQueryNormal(1, "小明2号"), update, options, Student
                .class);
        return Common.successMsg(andModify);
    }

    @RequestMapping(value = "/updateMongoFunc13", method = RequestMethod.GET)
    @ApiOperation(notes = "findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> " +
            "entityClass, String collectionName)", value = "mongo修改13", produces = "application/json")
    public String updateMongoFunc13() {
        Update update = new Update();
        update.set("grade", 2);
        FindAndModifyOptions options = new FindAndModifyOptions();
        //默认false
//        options.returnNew(true); //是否返回更新后数据
        options.upsert(true);   //是否更新（不存在则插入）
//        options.remove(true); //是否删除，为true时不会更新操作，如果returnNew为true则返回旧数据,并且删除
        Student andModify = mongoTemplate.findAndModify(Common.getQueryNormal(1, "小明2号"), update, options, Student
                .class, "student");
        return Common.successMsg(andModify);
    }


}
