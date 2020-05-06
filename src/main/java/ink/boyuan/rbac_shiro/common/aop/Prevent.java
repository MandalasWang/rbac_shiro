package ink.boyuan.rbac_shiro.common.aop;

import ink.boyuan.rbac_shiro.common.aop.impl.PreventImpl;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author wyy
 * @version 1.0
 * @Classname Prevent
 * @date 2020/4/30 8:57
 * @description
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PreventImpl.class)
public @interface Prevent {


    /**
     * 最小值
     *
     * @return
     */
    int min() default 0;

    /**
     * 最小值
     *
     * @return
     */
    int max() default 0;

    /**
     * 提示
     */
    String message() default "属性不在范围内";


}
