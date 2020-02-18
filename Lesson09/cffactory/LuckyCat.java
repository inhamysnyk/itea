package cffactory;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;

@Target({ FIELD })
public @interface LuckyCat {
  boolean lc() default false;
}
