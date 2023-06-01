package dodam.b1nd.dgit.global.annotation;

import dodam.b1nd.dgit.domain.user.domain.enums.Role;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    Role[] roles() default {Role.STUDENT, Role.ADMIN};
}