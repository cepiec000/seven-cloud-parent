package com.seven.code.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author v_chendongdong
 * @date 2020/12/24
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("code", 0);
	}
	
	public static Result error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static Result error(String msg) {
		return error(500, msg);
	}
	
	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result ok() {
		return new Result();
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
