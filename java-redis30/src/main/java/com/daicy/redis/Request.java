/*
 * Copyright (c) 2015-2020, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.daicy.redis;


import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;

public interface Request {
    String getCommand();

    ArrayRedisMessage getParams();

    RedisMessage getParam(int i);

    String getParamStr(int i);

    int getLength();

    boolean isEmpty();

    boolean isExit();
}
