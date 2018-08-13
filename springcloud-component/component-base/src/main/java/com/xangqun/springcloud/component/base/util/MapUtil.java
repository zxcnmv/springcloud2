package com.xangqun.springcloud.component.base.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;


/**
 * MapUtil
*/
public class MapUtil extends MapUtils {

	/**
	 * Map转换层Bean，使用泛型免去了类型转换的麻烦。
	 * @param mapData
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T mapToBean(Map<String, ? extends Object> mapData, Class<T> cls) {
		T bean = null;
		try {
			bean = cls.newInstance();
			BeanUtils.populate(bean, mapData);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 将一个ListMap转换成listBean
	 * @param params
	 * @param cls
	 * @param <T>
	 * @param <V>
	 * @return
	 */
	public static <T,V> List<T> listMapToList(List<Map<String,V>> params,Class<T> cls)
	{
		List<T> result=new ArrayList<T>();
		if(params==null||params.size()==0)
		{
			return result;
		}
		for (Map<String,V> mapData : params) {
			result.add(mapToBean(mapData, cls));
		}
		return result;
	}

	/**
	 * 将对象装换为map
	 * @param obj
	 * @return
	 */
	//bean 转化为map
    public static Map<String,Object> tranferBean2Map(Object obj) {
        //obj为空，结束方法
        if(obj==null){
			return null;
		}
        Map<String, Object> map=new HashMap<String, Object>();
        /*Introspector 类为通过工具学习有关受目标 Java Bean 支持的属性、事件和方法的知识提供了一个标准方法。
         * java的自省机制
         * */
        BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
			 PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();
		        for (PropertyDescriptor propertyDescriptor : ps) {
		            String key = propertyDescriptor.getName();
		            
		            if(!"class".equals(key)){
		                Method getter = propertyDescriptor.getReadMethod();
		                Object value = getter.invoke(obj);
		                map.put(key, value);
		            }
		        }
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return map;
        
    }
}
