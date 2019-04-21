package com.janwarlen;

import com.alibaba.fastjson.JSONObject;
import com.janwarlen.dao.TestCaseRepository;
import com.janwarlen.model.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @ClassName: Test
 * @author: janwarlen
 * @Date: 2019/4/21 14:19
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @org.junit.Test
    public void testEsSave() {
        TestCase item = new TestCase();
        item.setId(1L);
        item.setName("test");
        item.setDescription("for test");
        TestCase save = testCaseRepository.save(item);
        System.out.println(JSONObject.toJSONString(save));
    }

    @org.junit.Test
    public void testEsGet() {
        Optional<TestCase> byId = testCaseRepository.findById(1L);
        TestCase testCase = byId.get();
        System.out.println(JSONObject.toJSONString(testCase));
    }
}
