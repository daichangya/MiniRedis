/*
 * Copyright (c) 2015-2020, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.daicy.redis.command.key;


import com.daicy.redis.Request;
import com.daicy.redis.command.DBCommand;
import com.daicy.redis.protocal.IntegerRedisMessage;
import com.daicy.redis.protocal.RedisMessage;
import com.daicy.redis.storage.DictKey;
import com.daicy.redis.storage.DictValue;
import com.daicy.redis.storage.RedisDb;

import java.time.Instant;

import static com.daicy.redis.storage.DictKey.safeKey;

public abstract class TimeToLiveCommand implements DBCommand {

    @Override
    public RedisMessage execute(RedisDb db, Request request) {
        DictKey dictKey = safeKey(request.getParamStr(0));
        DictValue value = db.getExpires().get(dictKey);
        if (value != null) {
            return hasExpiredAt(db, value, dictKey);
        } else {
            return notExists();
        }
    }

    protected abstract int timeToLive(DictValue value, Instant now);

    private RedisMessage hasExpiredAt(RedisDb db, DictValue value, DictKey dictKey) {
        Instant now = Instant.now();
        if (!value.isExpired(now)) {
            return new IntegerRedisMessage(timeToLive(value, now));
        } else {
            db.getDict().remove(dictKey);
            db.getExpires().remove(dictKey);
            return notExists();
        }
    }

    private RedisMessage notExists() {
        return new IntegerRedisMessage(-2);
    }
}
