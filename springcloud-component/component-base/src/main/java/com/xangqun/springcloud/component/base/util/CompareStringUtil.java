package com.xangqun.springcloud.component.base.util;

/**
 * 字符串比较
 * @author Administrator
 *
 */
public class CompareStringUtil {

    /**
     * 
     * or关系比较
     * @param base
     * @param list
     * @return boolean
     */
    public static boolean isEqualOr(String base,String ...list){
        for(String str:list){
            if(str.equals(base)){
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * add 关系比较
     * @param base
     * @param list
     * @return boolean
     */
    public static boolean isEqualAdd(String base,String ...list){
        for(String str:list){
            if(!str.equals(base)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * not or 关系比较
     * @param base
     * @param list
     * @return boolean
     */
    public static boolean isNotEqualOr(String base,String ...list){
        return !isEqualOr(base,list);
    }
    
    /**
     * 
     * not add 关系比较
     * @param base
     * @param list
     * @return boolean
     */
    public static boolean isNotEqualAdd(String base,String ...list){
        return !isEqualAdd(base,list);
    }
}
