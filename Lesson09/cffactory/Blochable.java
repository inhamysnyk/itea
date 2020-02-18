package cffactory;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(value=CLASS)
public @interface Blochable {
    boolean bc() default false;
}
