package com.nju.edu.erp.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 如果需要在model和controller层面使用类似 AAA("哈哈哈") 这样的枚举, 可以让枚举继承该接口.
 * AAA("哈哈哈"), 在程序中用AAA来调用, 存入数据库的数据/在网络上传输的数据均为 "哈哈哈"
 * 并且可以从数据库或者网络传输中将"哈哈哈"反序列化为对应的枚举对象AAA
 * 对应的转换逻辑写在了 handlers/convertors中, 并在WebMvcConfig和 application.yml->mybatis->typeHandlersPackage进行了配置
 */
public interface BaseEnum<E extends Enum<?>, T> {
    /**
     * 获取枚举的值
     * @return 枚举的值
     */
    @JsonValue
    T getValue();
}