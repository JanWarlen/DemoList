1. StormSubmitter修改使用说明
修改代码：
```java
String mergePackPath = stormConf.get("mergePackLocation").toString();
if (StringUtils.isNotBlank(mergePackPath)) {
    jar = mergePackPath;
} else {
    jar = submitJarAs(conf, System.getProperty("storm.jar"), progressListener, asUser);
}
```
使用说明：
```java
Map<String, String> conf = new HashMap<>();
...
conf.put("mergePackLocation", "提交jar包在storm集群机器绝对路径");
StormSubmitter.submitTopology("taskName", conf, topology);
```