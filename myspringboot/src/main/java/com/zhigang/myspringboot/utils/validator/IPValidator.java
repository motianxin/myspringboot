/**
 * FileName: IPValidator
 * Author:   admin
 * Date:     2019/4/28 17:05
 * Description: 自定义数据校验器
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.utils.validator;

import javax.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈自定义数据校验器〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/4/28 17:05
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = IPValidatorClass.class)
public @interface IPValidator {
    /**
     * IP 类型：IPV4或者IPV6
     *
     * @return
     */
    String type() default "IPV4";

    String message() default "the ip is illegitimate";

}
