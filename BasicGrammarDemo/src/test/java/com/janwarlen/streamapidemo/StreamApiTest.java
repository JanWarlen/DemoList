package com.janwarlen.streamapidemo;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

/**
 * @auther: wangjian
 * @Date: 2018/12/21 10:22
 * @Description: 练习java8StreamApi
 * 原始网站：https://www.baeldung.com/java-8-streams
 */
public class StreamApiTest {

    @Test
    public void testEmptyStream() {
        Stream<Object> empty = Stream.empty();
        Stream<String> emptyString = Stream.empty();
//        Stream<int> empty = Stream.empty();
        Stream<Integer> emptyInteger = Stream.empty();
    }

    @Test
    public void testConvertToStream() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        Stream<Integer> streamList = integers.stream();

        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        int[] arr = new int[]{1, 2, 3};
        IntStream intStream = Arrays.stream(arr);
        IntStream intStream2 = Arrays.stream(arr, 1, 3);

    }

    @Test
    public void testStreamBuilder() {
        Stream<String> build = Stream.<String>builder().add("1").add("2").add("3").build();

    }

    @Test
    public void testStreamGenerate() {
        Stream<String> limit = Stream.generate(() -> "element").limit(5);
    }

    @Test
    public void testStreamIterate() {
        Stream<Integer> limit = Stream.iterate(20, n -> n + 2).limit(20);

    }

    @Test
    public void testPrimitiveStream() {
        IntStream intStream = IntStream.of(1, 2, 3);
        LongStream longStream = LongStream.of(1, 2, 3);
        DoubleStream doubleStream = DoubleStream.of(1, 2, 3);

        // since jdk 8
        Random random = new Random();
        random.doubles(3);
        random.ints(3);
        random.longs(3);
    }

    @Test
    public void testStringStream() {
        // 字符其实就是int，详见ASCII码表
        IntStream chars = "test".chars();


    }

    @Test
    public void testFileStream() {
        Path path = Paths.get("/usr/local/dev/tmp/test.log");
        try {
            Stream<String> lines = Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStreamReference() {
        // stream生成后只允许使用一次，使用过后不可再次使用，如果需要再次修改，分两种情况
        // 一：在修改过后的stream结果上继续修改
        // 二：使用原始数据再次生成stream进行修改
        Stream<String> stringStream = Stream.of("1", "2", "3", "22").filter(item -> item.contains("2"));
        Optional<String> any = stringStream.findAny();
        Assert.assertEquals(any.get(), "2");
        // Java 8 streams can’t be reused.
        // java.lang.IllegalStateException: stream has already been operated upon or closed
        stringStream = Stream.of("1", "2", "3", "22").filter(item -> item.contains("2"));
        Optional<String> first = stringStream.findFirst();
        Assert.assertEquals(first.get(), "2");
        stringStream = Stream.of("1", "2", "3", "22").filter(item -> item.contains("2"));
        List<String> collect = stringStream.collect(Collectors.toList());
        Assert.assertEquals(collect.size(), 2);
        Optional<String> any2 = collect.stream().findAny();
        Optional<String> first2 = collect.stream().findFirst();
        Assert.assertEquals(any2.get(), "2");
        Assert.assertEquals(first2.get(), "2");
    }

    @Test
    public void testStreamPipeLine() {
        Stream<String> skip = Stream.of("abcd", "bbcd", "cbcd").skip(1);
        long count = skip.map(element -> element.substring(0, 3)).count();
        Assert.assertEquals(2, count);
    }

    @Test
    public void testStreamLazyInvocation() {
        List<String> list = Arrays.asList("1", "2", "3");
        list.stream().filter(item -> {
            TestStreamModel.wasCalled();
            return item.contains("2");
        });
        // 当streamApi没有terminal operation，所有的代码其实并没有执行
        // 这类似于懒加载
        Assert.assertEquals(0, TestStreamModel.getCounter());
        Optional<String> find = list.stream().filter(item -> {
            TestStreamModel.wasCalled();
            System.out.println("filter:" + item);
            return item.contains("2");
        }).map(item -> {
            System.out.println("map:" + item);
            return item + "_";
        }).findFirst();
        Assert.assertEquals(2, TestStreamModel.getCounter());
        Assert.assertEquals("2_", find.get());
        System.out.println("----------------------------------------");
        // stream流应该是会自动优化
        // 当最终的操作不需要所有步骤全部执行时，中间步骤会不完全执行
        TestStreamModel.setCounter(0);
        long count = list.stream().filter(item -> {
            TestStreamModel.wasCalled();
            System.out.println("filter:" + item);
            return item.contains("2");
        }).map(item -> {
            System.out.println("map:" + item);
            return item + "_";
        }).count();
        Assert.assertEquals(3, TestStreamModel.getCounter());
        Assert.assertEquals(1, count);
    }

    @Test
    public void testStreamOrder() {
        List<String> list = Arrays.asList("1", "2", "3");
        long size = list.stream().map(element -> {
            TestStreamModel.wasCalled();
            return element;
        }).skip(2).count();

        Assert.assertEquals(3, TestStreamModel.getCounter());
        TestStreamModel.setCounter(0);
        size = list.stream().skip(2).map(element -> {
            TestStreamModel.wasCalled();
            return element;
        }).count();
        Assert.assertEquals(1, TestStreamModel.getCounter());
    }

    @Test
    public void testReduce() {
        OptionalInt reduce = IntStream.range(1, 4).reduce((a, b) -> {
            System.out.println("a=" + a + ",b=" + b);
            return a + b;
        });
        Assert.assertEquals(6, reduce.getAsInt());
        System.out.println("----------------------------------------------------");
        int reduceRes = IntStream.range(1, 4).reduce(6, (a, b) -> {
            System.out.println("a=" + a + ",b=" + b);
            return a + b;
        });
        Assert.assertEquals(12, reduceRes);
        System.out.println("----------------------------------------------------");
        /*IntStream.range(1, 4).reduce(6, (a, b) -> a + b, (a,b) -> {
            System.out.println("combiner");
            return a + b;
        });*/
        Integer combiner = Arrays.asList(1, 2, 3).parallelStream().reduce(6, (a, b) -> {
            System.out.println("a=" + a + ",b=" + b);
            return a + b;
        }, (a, b) -> {
            System.out.println("combiner：a=" + a + ",b=" + b);
            return a + b;
        });
        Assert.assertEquals(24, combiner.intValue());
    }

    @Test
    public void testCollect() {
        List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                new Product(14, "orange"), new Product(13, "lemon"),
                new Product(23, "bread"), new Product(13, "sugar"));
        System.out.println("-----------------------toList------------------------------");
        List<String> collect = productList.stream().map(Product::getName).collect(Collectors.toList());
        System.out.println(collect.toString());
        System.out.println("-----------------------toString-----------------------------");
        String collect1 = productList.stream().map(Product::getName).collect(Collectors.joining());
        String collect2 = productList.stream().map(Product::getName).collect(Collectors.joining("_"));
        String collect3 = productList.stream().map(Product::getName).collect(Collectors.joining("_", "(", ")"));
        System.out.println(collect1);
        System.out.println(collect2);
        System.out.println(collect3);
        System.out.println("-----------------------getAverage-----------------------------");
        Double average = productList.stream().collect(Collectors.averagingInt(Product::getPrice));
        Assert.assertEquals(17, average.intValue());
        System.out.println("-----------------------getStatistics----------------------------");
        IntSummaryStatistics sum = productList.stream().collect(Collectors.summarizingInt(Product::getPrice));
        Assert.assertEquals(86, sum.getSum());
        System.out.println("-----------------------groupByPrice-----------------------------");
        Map<Integer, List<Product>> groupByPrice = productList.stream().collect(Collectors.groupingBy(Product::getPrice));
        System.out.println(groupByPrice);
        System.out.println("-----------------------partitionByPrice-----------------------------");
        Map<Boolean, List<Product>> partition = productList.stream().collect(Collectors.partitioningBy(item -> item.getPrice() > 15));
        System.out.println(partition);
        System.out.println("-----------------------collectingAndThen-----------------------------");
        Set<Product> collectAndThen = productList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        System.out.println(collectAndThen);
        System.out.println("-----------------------selfCollector-----------------------------");
        Collector<Product, LinkedList<Product>, LinkedList<Product>> linkedListCollector = Collector
                .of(LinkedList::new, LinkedList::add, (first, second) -> {
            first.addAll(second);
            return first;
        });
        LinkedList<Product> linkedList = productList.stream().collect(linkedListCollector);
        System.out.println(linkedList);
    }

    @Test
    public void testParallel() {
        IntStream intStream = IntStream.range(1, 150).parallel();
        if (intStream.isParallel()) {
            boolean anyMatch = intStream.map(item -> item * 2).anyMatch(item -> item > 250);
            Assert.assertEquals(true, anyMatch);
        }
        Assert.assertEquals(true, intStream.isParallel());
    }

}
