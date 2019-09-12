package com.seglino.jingyi.common.log.aspect;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.seglino.jingyi.common.log.annotation.OperationLog;
import com.seglino.jingyi.common.log.annotation.ThrowableLog;
import com.seglino.jingyi.common.utils.ApplicationUtils;

@Aspect
@Component
public class OperationLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.OperationLog)")
	public void operationLog() {
	}

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.ThrowableLog)")
	public void throwableLog() {
	}

	/**
	 * controller层访问前拦截
	 * 
	 * @param point
	 */
	@Before("operationLog()")
	public void doBefore(JoinPoint point) {
		HttpServletRequest request = ApplicationUtils.getHttpRequest();
		String userid = ApplicationUtils.getUserId();
		String ip = ApplicationUtils.getClientIP(request);
		try {
			String methodName = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
			OperationLog log = getMethod(point).getAnnotation(OperationLog.class);

			System.out.println("=====日志通知开始=====");
			System.out.println("用户ID：" + userid);
			System.out.println("客户端IP：" + ip);
			System.out.println("请求方法：" + methodName);
			System.out.println("方法描述：" + log.value());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * service层异常拦截
	 * 
	 * @param point
	 * @param throwable
	 */
	@AfterThrowing(pointcut = "throwableLog()", throwing = "ex")
	public void doAafterThrowing(JoinPoint point, Throwable ex) {
		try {
			HttpServletRequest request = ApplicationUtils.getHttpRequest();
			String userid = ApplicationUtils.getUserId();
			String ip = ApplicationUtils.getClientIP(request);
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
			ThrowableLog log = getMethod(point).getAnnotation(ThrowableLog.class);

			System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + ex.toString());
			System.out.println("异常信息:" + ex.getMessage());
			System.out.println("用户ID：" + userid);
			System.out.println("客户端IP：" + ip);
			System.out.println("请求方法：" + methodName);
			System.out.println("方法描述：" + log.value());
			System.out.println("请求参数:" + params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 获取切面方法
	 * 
	 * @param point
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Method getMethod(JoinPoint point) throws ClassNotFoundException {
		Signature signature = point.getSignature();
		Class<?> clazz = point.getTarget().getClass();
		String targetName = clazz.getName();
		String methodName = signature.getName();
		Method[] methods = Class.forName(targetName).getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}
}
