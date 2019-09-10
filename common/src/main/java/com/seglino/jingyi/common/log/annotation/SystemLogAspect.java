package com.seglino.jingyi.common.log.annotation;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.net.IPv6Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.seglino.jingyi.common.utils.IpUtils;

//@Aspect
//@Component
public class SystemLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.ServiceLog)")
	public void serviceAspect() {
	}

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.ControllerLog)")
	public void controllerAspect() {
	}

	@Before("controllerAspect")
	public void doBefore(JoinPoint point) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userid = getUserid();
		String ip = IpUtils.getClientIpAddress(request);

		String targetName = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();
		Object[] arguments = point.getArgs();
		try {
			Class targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String description = "";
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						description = method.getAnnotation(ControllerLog.class).desc();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
