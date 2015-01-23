package org.izumo.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {

    public static final String DATE_FORMATE_YMD = "yyyy-MM-dd";

    public static final String DATE_FORMATE_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    String action() default "";

    String name() default "";

    String statevalue() default "";

    String datetype() default DATE_FORMATE_YMDHMS;
}
