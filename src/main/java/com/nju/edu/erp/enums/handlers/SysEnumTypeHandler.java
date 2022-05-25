package com.nju.edu.erp.enums.handlers;

import com.nju.edu.erp.enums.BaseEnum;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.sheetState.*;
import org.apache.ibatis.type.MappedTypes;

/**
 * 枚举转换的公共模块
 *
 */
@MappedTypes(value = {PurchaseSheetState.class, WarehouseInputSheetState.class, WarehouseOutputSheetState.class, CustomerType.class, SaleSheetState.class, PurchaseReturnsSheetState.class})
public class SysEnumTypeHandler<E extends Enum<E> & BaseEnum> extends BaseEnumTypeHandler<E> {
    /**
     * 设置配置文件设置的转换类以及枚举类内容，供其他方法更便捷高效的实现
     *
     * @param type 配置文件中设置的转换类
     */
    public SysEnumTypeHandler(Class<E> type) {
        super(type);
    }
}