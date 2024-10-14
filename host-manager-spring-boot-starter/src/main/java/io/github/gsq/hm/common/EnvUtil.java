package io.github.gsq.hm.common;

import cn.hutool.extra.spring.SpringUtil;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.common.EnvUtil
 *
 * @author : gsq
 * @date : 2024-10-12 16:08
 * @note : It's not technology, it's art !
 **/
public class EnvUtil {

    public static <T> T getBean(Class<T> clazz) {
        try {
            return SpringUtil.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

}
