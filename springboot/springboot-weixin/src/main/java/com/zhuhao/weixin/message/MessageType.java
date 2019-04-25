package com.zhuhao.weixin.message;

/**
 *
 * 消息类型
 *
 * @author jinyu(foxinmy@gmail.com)
 *
 */
public enum MessageType {
    /**
     * 文字消息
     */
    text,
    /**
     * 图片消息
     */
    image,
    /**
     * 语音消息
     */
    voice,
    /**
     * 视频消息
     */
    video,
    /**
     * 小视频消息
     */
    shortvideo,
    /**
     * 位置消息
     */
    location,
    /**
     * 链接消息
     */
    link,
    /**
     * 事件消息
     */
    event;
}