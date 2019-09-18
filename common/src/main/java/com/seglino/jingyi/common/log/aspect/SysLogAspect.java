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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.seglino.jingyi.common.log.LogType;
import com.seglino.jingyi.common.log.annotation.ControllerLog;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.common.log.pojo.SysLog;
import com.seglino.jingyi.common.log.service.SysLogService;
import com.seglino.jingyi.common.utils.ApplicationUtils;
import com.seglino.jingyi.common.utils.DateUtils;

@Aspect
@Component
public class SysLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

	@Autowired
	private SysLogService syslogService;

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.ControllerLog)")
	public void controllerLog() {
	}

	@Pointcut("@annotation(com.seglino.jingyi.common.log.annotation.ServiceLog)")
	public void serviceLog() {
	}

	/**
	 * controller层访问前拦截
	 * 
	 * @param point
	 */
	@Before("controllerLog()")
	public void doBefore(JoinPoint point) {
		try {
			Class<?> clazz = point.getTarget().getClass();
			HttpServletRequest request = ApplicationUtils.getHttpRequest();
			String ip = ApplicationUtils.getClientIP(request);
			String methodFullName = clazz.getName() + "." + point.getSignature().getName();
			ControllerLog operationLog = getMethod(point).getAnnotation(ControllerLog.class);
			String parameter = getParameter(point);
			StringBuilder sb = new StringBuilder();
			sb.append("操作人ID：" + ApplicationUtils.getUserId() + "\n");
			sb.append("操作方法：" + methodFullName + "\n");
			sb.append("方法参数：" + parameter + "\n");
			sb.append("操作时间：" + DateUtils.getNowString("yyyy-MM-dd HH:mm:ss") + "\n");

			SysLog sysLog = new SysLog();
			sysLog.setType(LogType.OPERATION.getType());
			sysLog.setModule(clazz.getAnnotation(ControllerLog.class).value());
			sysLog.setMethod(operationLog.value());
			sysLog.setParameter(parameter);
			sysLog.setIp(ip);
			sysLog.setDetail(sb.toString());

			int count = syslogService.insert(sysLog);
			if (count <= 0) {
				logger.error("日志保存失败");
			}
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
	@AfterThrowing(pointcut = "serviceLog()", throwing = "ex")
	public void doAafterThrowing(JoinPoint point, Throwable ex) {
		try {
			Class<?> clazz = point.getTarget().getClass();
			HttpServletRequest request = ApplicationUtils.getHttpRequest();
			String ip = ApplicationUtils.getClientIP(request);
			String methodFullName = clazz.getName() + "." + point.getSignature().getName();
			ServiceLog serviceLog = getMethod(point).getAnnotation(ServiceLog.class);
			String parameter = getParameter(point);

			StringBuilder sb = new StringBuilder();
			sb.append("操作用户：" + ApplicationUtils.getUserId() + "\n");
			sb.append("操作方法：" + methodFullName + "\n");
			sb.append("方法参数：" + parameter + "\n");
			sb.append("操作时间：" + DateUtils.getNowString("yyyy-MM-dd HH:mm:ss") + "\n");
			sb.append("异常信息：" + ex.toString() + "\n");

			SysLog sysLog = new SysLog();
			sysLog.setType(LogType.THROWABLE.getType());
			sysLog.setModule(clazz.getAnnotation(ServiceLog.class).value());
			sysLog.setMethod(serviceLog.value());
			sysLog.setParameter(parameter);
			sysLog.setIp(ip);
			sysLog.setDetail(sb.toString());

			int count = syslogService.insert(sysLog);
			if (count <= 0) {
				logger.error("日志保存失败");
			}
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

	/**
	 * 获取方法参数
	 * 
	 * @param point
	 * @return
	 */
	private String getParameter(JoinPoint point) {
		StringBuilder sb = new StringBuilder();
		if (point.getArgs() != null && point.getArgs().length > 0) {
			for (int i = 0; i < point.getArgs().length; i++) {
				Object arg = point.getArgs()[i];
				if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile) {
					continue;
				}
				sb.append(JSON.toJSONString(arg) + ";");
			}
		}
		return sb.toString();
	}
}
