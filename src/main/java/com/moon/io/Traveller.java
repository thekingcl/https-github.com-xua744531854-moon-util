package com.moon.io;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author benshaoye
 * @date 2018/9/11
 */
public interface Traveller<T> extends Supplier<List<T>> {

    /**
     * 遍历指定目录下的文件
     *
     * @param path
     * @return
     */
    Traveller traverse(String path);

    /**
     * 遍历指定目录下的文件
     *
     * @param path
     * @return
     */
    Traveller traverse(File path);

    /**
     * 初始化或重置
     *
     * @return
     */
    void clear();

    /**
     * 获取所有内容
     *
     * @return
     */
    @Override
    List<T> get();
}
