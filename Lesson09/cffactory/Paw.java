package cffactory;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;

@Target({ METHOD })
public @interface Paw {
    int pc() default 2;

}


