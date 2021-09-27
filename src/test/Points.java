package test;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;

/**
 * @author kart
 *
 */
@Retention(value=RUNTIME)
@Target(value=METHOD)
public @interface Points {
	int value();
}
