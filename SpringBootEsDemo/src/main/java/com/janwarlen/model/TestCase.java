package com.janwarlen.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @ClassName: TestCase
 * @author: janwarlen
 * @Date: 2019/4/21 14:14
 * @Description:
 */
@Data
@Document(indexName = "blog", type = "item")
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
}
