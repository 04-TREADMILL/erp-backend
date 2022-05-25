package com.nju.edu.erp.auth;

import com.nju.edu.erp.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorized {
    Role[]  roles() default {
        Role.ADMIN, Role.SALE_STAFF, Role.INVENTORY_MANAGER, Role.FINANCIAL_STAFF, Role.HR, Role.GM};
    String message() default "";
}
