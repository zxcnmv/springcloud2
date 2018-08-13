package com.xangqun.springcloud.component.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 用于获取spring的applicationContext
 * @author liangxianyong
 * @date 2018年4月24日
 * @time 上午10:44:49
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		SpringContextUtil.applicationContext = arg0;
	}
	
    public static ApplicationContext getContext() {
        return applicationContext;
    }

	/**
	 * 查询Spring受管Bean。
	 * 
	 * @param beanId
	 *            String
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		if (beanId.equalsIgnoreCase("null")) {
			return null;
		} else {
			return getBeanByBeanIdOrClass(beanId, null);
		}
	}

	/**
	 * 查询受管Bean。
	 * 
	 * @param beanId
	 *            String
	 * @param clazz
	 *            Class
	 * 
	 * @return Object
	 */
	public static Object getBeanByBeanIdOrClass(String beanId, Class<?> clazz) {
		check();
		if (applicationContext == null) {
			return null;
		}
		if ("null".equalsIgnoreCase(beanId)) {
			return null;
		}
		if (beanId != null && applicationContext.containsBean(beanId)) {
			return applicationContext.getBean(beanId);
		}
		List<?> l = getBeansByClass(clazz);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询Spring受管Bean。
	 * 
	 * @param targetClass
	 *            Class
	 * 
	 * @return List
	 */
	public static List<?> getBeansByClass(Class<?> targetClass) {
		check();
		List<?> ret = new ArrayList<Object>();
		if (applicationContext == null || targetClass == null) {
			return ret;
		}
		Map<?, ?> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, targetClass, true, true);
		if (beans.size() == 0) {
		}
		return new ArrayList<Object>(beans.values());
	}
	
	/**
	 * 注册bean
	 * @param beanId
	 * @param clazz
	 * @param properties
	 */
	public static Object registerBean(String beanId,
			Class<? extends Object> clazz, Map<String, Object> properties,ConstructorArgumentValues constructorArgumentValues){
		AbstractBeanDefinition beanDefinition = null;
		try {
			beanDefinition = BeanDefinitionReaderUtils.createBeanDefinition(
					null, clazz.getName(), clazz.getClassLoader());
		} catch (ClassNotFoundException e1) {
			throw new RuntimeException("注册Bean时出现异常,beanId="+beanId, e1);
		}
		if(properties!=null){
			MutablePropertyValues propertyValues = new MutablePropertyValues();
			for (Entry<String, Object> prop : properties.entrySet()) {
				propertyValues.addPropertyValue(prop.getKey(), prop.getValue());
			}
			beanDefinition.setPropertyValues(propertyValues);
		}
		if(constructorArgumentValues!=null){
			beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
		}

		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		factory.registerBeanDefinition(beanId, beanDefinition);
		return factory.getBean(beanId);
	}
	
	/**
	 * 销毁指定bean
	 * @param beanName
	 */
	public static void destoryBean(String beanName){
		DefaultListableBeanFactory factory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		factory.removeBeanDefinition(beanName);
	}
	
	private static void check(){
		Assert.notNull(applicationContext, "applicationContext不能为空");
	}

}
