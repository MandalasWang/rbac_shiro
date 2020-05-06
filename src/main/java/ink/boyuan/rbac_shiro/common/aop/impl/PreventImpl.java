package ink.boyuan.rbac_shiro.common.aop.impl;

import ink.boyuan.rbac_shiro.common.aop.Prevent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wyy
 * @version 1.0
 * @Classname PreventImpl
 * @date 2020/4/30 9:27
 * @description  实现校验接口contraintValidator，泛型参数为注解器，校验的字段类型为int型
 **/
public class PreventImpl  implements ConstraintValidator<Prevent,Integer> {

    int min =0;

    int max = 0;

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer > min && integer <max;
    }

    @Override
    public void initialize(Prevent constraintAnnotation) {

        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();

    }
}
