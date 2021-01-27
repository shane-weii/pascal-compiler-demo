package org.example.compiler.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Shane Wei
 * @date 2021/1/27 13:51
 * Description: 标识方法提权至protected级别，方便测试
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface VisibleForTesting {
}
