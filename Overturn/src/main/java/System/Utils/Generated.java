package System.Utils;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation used to mark generated code that should be ignored for GUI methods.
 * It can be applied to classes, methods, and constructors.
 */
@Documented
@Retention(RUNTIME)
// Specifies where this annotation can be applied (to classes, methods, and constructors)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface Generated {
}
