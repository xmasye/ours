package com.xmasye.ours.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.cglib.beans.BeanCopier;

import java.util.*;

/**
 * VO、PO对象相互转换工具
 *
 * @author linyi 2016/7/15
 */
public class VOUtil {

    static {
        ConvertUtils.register(new DateConverter<Date>(), Date.class);
    }

    /**
     * 把 source 转换成指定对象 T，T必须有一个不带参数的构造函数。
     *
     * @param source - 源数据，不能为null
     * @param clazz  - 目标数据的class，不能为null
     * @throws RuntimeException 如果无法实例化 clazz、复制属性错误，抛出异常
     */
    public static <T> T from(Object source, Class<T> clazz) {
        if (null == source)
            return null;
        T result;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("newInstance ERROR, clz: " + clazz, e);
        }
        try {
            BeanUtils.copyProperties(result, source);
            // BeanCopier create = BeanCopier.create(source.getClass(), clazz,
            // false);
            // create.copy(source, result, null);
        } catch (Exception e) {
            throw new RuntimeException(
                    "copyProperties ERROR, obj: " + source.getClass() + " clz: " + clazz, e);
        }
        return result;
    }

    /**
     * 把 source 列表转换成指定对象 T 的列表，T必须有一个不带参数的构造函数。
     *
     * @param list  - 要转换的列表，不能为空
     * @param clazz - 目标数据的class，不能为null
     * @throws RuntimeException 如果无法实例化 clazz、复制属性错误，抛出异常
     */
    public static <T> List<T> fromList(Collection<? extends Object> list, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (null != list)
            for (Object source : list)
                result.add(from(source, clazz));
        return result;
    }

    /**
     * 拷贝属性。
     * （1）如果属性名相同，但类型不同，不会拷贝该属性，也不会报错；
     * （2）source的属性必须要有getter和setter
     *
     * @param source 源对象
     * @param target 拷贝到目标对象
     */
    public static void copy(Object source, Object target) {
        if (null == source || null == target)
            return;

        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    public static class DateConverter<T> implements Converter {

        public Object convert(Class type, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Date) {
                return (T) value;
            }
            throw new ConversionException(
                    "不能转换 " + value.getClass().getName() + " 为 " + type.getName());
        }
        /**
        @Override
        public <T> T convert(Class<T> type, Object value) {
            if (value == null) {
                return null;
            } else if (value instanceof Date) {
                return  (T) value;
            }
            throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
        }
        */
    }
}
