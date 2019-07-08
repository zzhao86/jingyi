package com.seglino.jingyi.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AutoMapper {
	/**
	 * 对象映射
	 * 
	 * @param source 源对象
	 * @param destination 目标对象
	 * @param sourceFieldNames 要转换的字段名称
	 */
	public static <TSource, TDestination> TDestination mapper(TSource source, Class<TDestination> targetClass) {
		TDestination destination = null;
		try {
			destination = targetClass.newInstance();
			Method[] srcMethods = getMethods(source);
			Method[] destMethods = getMethods(destination);

			for (Method dm : destMethods) {
				String destMethodName = dm.getName();
				if (destMethodName.startsWith("set")) {
					try {
						for (Method sm : srcMethods) {
							String srcMethodName = sm.getName();
							if (srcMethodName.startsWith("get") && destMethodName.substring(3, destMethodName.length()).equalsIgnoreCase(srcMethodName.substring(3, srcMethodName.length()))) {
								Object sv = sm.invoke(source);
								dm.invoke(destination, sv);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return destination;
	}

	/**
	 * 集合映射
	 * 
	 * @param src 源集合
	 * @param target 目标集合
	 * @param targetClass 目标集合的泛型类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <S, T> List<T> mapperList(List<S> src, Class<?> targetClass) {
		List<T> target = new ArrayList<T>();
		for (int i = 0; i < src.size(); i++) {
			try {
				Object object = mapper(src.get(i), targetClass);
				target.add((T) object);
			} catch (Exception e) {
				continue;
			}
		}
		return target;
	}

	private static Method[] getMethods(Object obj, String... mNames) {
		Method[] methods = obj.getClass().getMethods();
		if (mNames != null && mNames.length > 0) {
			List<Method> result = new ArrayList<Method>();
			for (int i = 0; i < methods.length; i++) {
				for (int j = 0; j < mNames.length; j++) {
					if (methods[i].getName().equals(mNames[j])) {
						result.add(methods[i]);
					}
				}
			}
			methods = new Method[result.size()];
			result.toArray(methods);
		}

		return methods;
	}
}
