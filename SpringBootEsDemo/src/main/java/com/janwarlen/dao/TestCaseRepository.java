package com.janwarlen.dao;

import com.janwarlen.model.TestCase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TestCaseRepository
 * @author: janwarlen
 * @Date: 2019/4/21 14:16
 * @Description: CRUD 操作类
 */
@Component
public interface TestCaseRepository extends ElasticsearchRepository<TestCase, Long> {
}
