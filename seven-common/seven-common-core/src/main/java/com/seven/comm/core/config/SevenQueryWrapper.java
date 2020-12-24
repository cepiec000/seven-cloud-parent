package com.seven.comm.core.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.comm.core.enums.BetweenEnum;
import lombok.val;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class SevenQueryWrapper<T> extends QueryWrapper<T> {

    public SevenQueryWrapper<T> seq(String column, Object val) {
        if (Objects.nonNull(val)) {
            this.eq(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> seq(String column, String val) {
        if (Objects.nonNull(val) && val.trim().length() > 0) {
            this.eq(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> seq(String column, Number val) {
        if (Objects.nonNull(val)) {
            this.eq(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> seqNumber(String column, Number val) {
        if (Objects.nonNull(val) && !val.equals(0)) {
            this.eq(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> slike(String column, String val) {
        if (Objects.nonNull(val) && val.trim().length() > 0) {
            this.like(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> slikeLeft(String column, String val) {
        if (Objects.nonNull(val) && val.trim().length() > 0) {
            this.likeLeft(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> slikeRight(String column, String val) {
        if (Objects.nonNull(val) && val.trim().length() > 0) {
            this.likeRight(column, val);
        }
        return this;
    }
    public SevenQueryWrapper<T> sin(String column, Collection val) {
        if (Objects.nonNull(val) && val.size() > 0) {
            this.in(column, val);
        }
        return this;
    }

    public SevenQueryWrapper<T> dateBetween(String column, Date start, Date end, BetweenEnum betweenEnum) {
        if (Objects.nonNull(column) && Objects.nonNull(betweenEnum)) {
            if (start != null) {
                switch (betweenEnum) {
                    case NO_CONTAIN:
                        this.gt(column, start);
                        this.lt(column, end);
                        break;
                    case ALL_CONTAIN:
                        this.ge(column, start);
                        this.le(column, end);
                        break;
                    case LEFT_CONTAIN:
                        this.ge(column, start);
                        this.lt(column, end);
                        break;
                    case RIGHT_CONTAIN:
                        this.gt(column, start);
                        this.le(column, end);
                        break;
                }
            }
        }
        return this;
    }

}
