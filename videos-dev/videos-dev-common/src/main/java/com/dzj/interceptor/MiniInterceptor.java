package com.dzj.interceptor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.dzj.redis.RedisService;
import com.dzj.redis.key.UserKey;
import com.dzj.utils.JSONResult;
import com.dzj.utils.JsonUtils;

public class MiniInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisService redisService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userId = request.getHeader("userId");
		String userToken = request.getHeader("userToken");
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userId)) {
			String uniqueToken = redisService.get(UserKey.token, userId, String.class);
			if(StringUtils.isEmpty(uniqueToken) || StringUtils.isBlank(uniqueToken)) {
				returnErrorResponse(response,JSONResult.errorTokenMsg("请登录"));
				return false;
			}else {
				if(!uniqueToken.equals(userToken)) {
					returnErrorResponse(response,JSONResult.errorTokenMsg("账户被挤出"));
					return false;
				}
			}
			
		} else {
			returnErrorResponse(response,JSONResult.errorTokenMsg("请登录"));
			return false;
		}

		return true;
	}

	public void returnErrorResponse(HttpServletResponse response, JSONResult jsonResult) throws IOException {
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			response.setContentType("text/json");
			response.setCharacterEncoding("utf-8");
			outputStream.write(JsonUtils.objectToJson(jsonResult).getBytes("utf-8"));
			outputStream.flush();
		} finally {
				if(outputStream !=null) {
					outputStream.close();
				}
		}
	}
}
