package com.seglino.jingyi.common.log.aspect;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.seglino.jingyi.common.log.annotation.OperationLog;
import com.seglino.jingyi.common.utils.IpUtils;

@Aspect
@Component
public class OperationLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.OperationLog)")
	public void operationLog() {
		System.out.println("执行了Service");
	}

	@Before("operationLog()")
	public void before(JoinPoint point) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userid = getUserid();
		String ip = IpUtils.getClientIpAddress(request);
		try {
			String methodName = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
			String desc = getAnnotationValue(point);

			System.out.println("=====日志通知开始=====");
			System.out.println("用户ID：" + userid);
			System.out.println("客户端IP：" + ip);
			System.out.println("请求方法：" + methodName);
			System.out.println("方法描述：" + desc);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @param point
	 * @param throwable
	 */
	@AfterThrowing(pointcut = "operationLog()", throwing = "ex")
	public void afterThrowing(JoinPoint point, Throwable ex) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String userid = getUserid();
			String ip = IpUtils.getClientIpAddress(request);
			// 获取用户请求方法的参数并序列化为JSON格式字符串
			String params = "";
			if (point.getArgs() != null && point.getArgs().length > 0) {
				for (int i = 0; i < point.getArgs().length; i++) {
					Object arg = point.getArgs()[i];
					if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
						continue;
					}
					params += JSON.toJSONString(arg) + ";";
				}
			}
			String methodName = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
			String desc = getAnnotationValue(point);

			System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + ex.getClass().getName());
			System.out.println("异常信息:" + ex.getMessage());
			System.out.println("用户ID：" + userid);
			System.out.println("客户端IP：" + ip);
			System.out.println("请求方法：" + methodName);
			System.out.println("方法描述：" + desc);
			System.out.println("请求参数:" + params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 获取日志描述
	 * 
	 * @param point
	 * @return
	 * @throws ClassNotFoundException
	 */
	private String getAnnotationValue(JoinPoint point) throws ClassNotFoundException {
		String value = "";
		String targetName = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();
		Method[] methods = Class.forName(targetName).getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				value = method.getAnnotation(OperationLog.class).value();
				break;
			}
		}
		return value;
	}

	/**
	 * 获取当前登录用户ID
	 * 
	 * @return
	 */
	private String getUserid() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return subject.getPrincipal().toString();
		}
		return null;
	}
}
