package com.zhu.base.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * @Author: zhuhao
 * @Date: 2018/10/26 下午5:23
 * @Description:
 */
public class RedisSubPubListener extends JedisPubSub {

    public void onMessage(String channel, String message) {
        System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
    }

    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d", channel, subscribedChannels));
    }

    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d", channel, subscribedChannels));

    }



}
