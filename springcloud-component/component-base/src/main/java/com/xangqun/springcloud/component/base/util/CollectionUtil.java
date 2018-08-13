/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author guofeng
 * @since 2018年1月18日
 */
public class CollectionUtil {
  private CollectionUtil() {

  }

  /**
   * 
   * @param objArray
   * @return boolean
   */
  public static boolean isBlank(Object[] objArray) {
    return (objArray == null) || (objArray.length <= 0);
  }

  /**
   * 
   * @param objArray
   * @return boolean
   */
  public static boolean isNotBlank(Object[] objArray) {
    return !isBlank(objArray);
  }

  /**
   * 
   * @param obj
   * @return boolean
   */
  @SuppressWarnings("unchecked")
  public static boolean isBlank(Object obj) {
    if (obj instanceof Set) {
      Set<Object> setObj = (Set<Object>) obj;
      return isBlank(setObj);
    }
    if (obj instanceof Collection) {
      Collection<Object> collection = (Collection<Object>) obj;
      return isBlank(collection);
    }
    if (obj instanceof Map) {
      Map<Object, Object> mapObj = (Map<Object, Object>) obj;
      return isBlank(mapObj);
    }
    return (obj == null) || ("".equals(obj));
  }

  /**
   * 
   * @param obj
   * @return boolean
   */
  @SuppressWarnings("unchecked")
  public static boolean isNotBlank(Object obj) {
    if (obj instanceof Set) {
      Set<Object> setObj = (Set<Object>) obj;
      return isNotBlank(setObj);
    }
    if (obj instanceof Collection) {
      Collection<Object> collection = (Collection<Object>) obj;
      return isNotBlank(collection);
    }
    if (obj instanceof Map) {
      Map<Object, Object> mapObj = (Map<Object, Object>) obj;
      return isNotBlank(mapObj);
    }
    return !isBlank(obj);
  }

  /**
   * 
   * @param collection
   * @return boolean
   */
  public static boolean isBlank(Collection<Object> collection) {
    return collection.isEmpty();
  }

  /**
   * 
   * @param collection
   * @return boolean
   */
  public static boolean isNotBlank(Collection<Object> collection) {
    return !isBlank(collection);
  }

  /**
   * 
   * @param setObj
   * @return boolean
   */
  public static boolean isBlank(Set<Object> setObj) {
    return setObj.isEmpty();
  }

  /**
   * 
   * @param setObj
   * @return boolean
   */
  public static boolean isNotBlank(Set<Object> setObj) {
    return !isBlank(setObj);
  }

  /**
   * 
   * @param obj
   * @return boolean
   */
  public static boolean isBlank(Serializable obj) {
    return obj == null;
  }

  /**
   * 
   * @param obj
   * @return boolean
   */
  public static boolean isNotBlank(Serializable obj) {
    return !isBlank(obj);
  }

  /**
   * 
   * @param mapObj
   * @return boolean
   */
  public static boolean isBlank(Map<Object, Object> mapObj) {
    return mapObj.size() <= 0;
  }

  /**
   * 
   * @param mapObj
   * @return boolean
   */
  public static boolean isNotBlank(Map<Object, Object> mapObj) {
    return !isBlank(mapObj);
  }
}
