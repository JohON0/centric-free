package pa.centric.client.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String name();
    String description();
}
