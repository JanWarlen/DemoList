package com.janwarlen.model;

/**
 * @Auther: janwarlen
 * @Date: 2018/11/25 17:34
 * @Description:
 */
public class MqMessage {

    private String topic;

    private String tags;

    private String message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
