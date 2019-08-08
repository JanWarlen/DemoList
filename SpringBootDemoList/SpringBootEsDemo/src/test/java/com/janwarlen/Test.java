package com.janwarlen;

import com.alibaba.fastjson.JSONObject;
import com.janwarlen.dao.TestCaseRepository;
import com.janwarlen.model.TestCase;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
        for (int i = 3; i < 20; i++) {
            TestCase item = new TestCase();
            item.setId(Long.valueOf(i));
            item.setName("test"+i);
            item.setDescription("for test");
            TestCase save = testCaseRepository.save(item);
            System.out.println(JSONObject.toJSONString(save));
        }
    }

    @org.junit.Test
    public void testEsGet() {
        Optional<TestCase> byId = testCaseRepository.findById(1L);
        TestCase testCase = byId.get();
        System.out.println(JSONObject.toJSONString(testCase));
    }

    @org.junit.Test
    public void testEsByPage() {
        int pageNumber = 0;
        int pageSize = 10;
        // 构建搜索查询
//        TermQueryBuilder nameQueryBuilder = QueryBuilders.termQuery("name", "test");
        // 字段，值 匹配
        MatchQueryBuilder descriptionQueryBuilder = QueryBuilders.matchQuery("description", "for test");
//        TermQueryBuilder descriptionQueryBuilder = QueryBuilders.termQuery("description", "for test");
        BoolQueryBuilder must = QueryBuilders.boolQuery().must(descriptionQueryBuilder);

        // 分页参数
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(must).build();
        Page<TestCase> testCasePage = testCaseRepository.search(searchQuery);
        List<TestCase> content = testCasePage.getContent();
        System.out.println(JSONObject.toJSONString(testCasePage));
        System.out.println(JSONObject.toJSONString(content));
    }
}
