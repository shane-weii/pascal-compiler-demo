package org.example.compiler.demo;

/**
 * @author Shane Wei
 * @date 2021/1/28 11:13
 * Description:
 */
public interface Symbol {
    /***
     * 获取符号名称
     * @return
     */
    String getName();

    /***
     * 获取符号类型
     * @return
     */
    String getType();
}
