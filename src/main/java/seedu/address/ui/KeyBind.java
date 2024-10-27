package seedu.address.ui;

import java.util.function.Predicate;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents a key binding with an associated key code and action to execute.
 * Allows optional conditions for triggering the action.
 */
public class KeyBind {
    private final KeyCode code;
    private final Runnable action;
    private final Predicate<KeyEvent> condition;

    /**
     * Constructs a key binding with a specified key code and action.
     */
    public KeyBind(KeyCode code, Runnable action) {
        this.code = code;
        this.action = action;
        this.condition = x -> true;
    }

    /**
     * Constructs a key binding with a specified key code, action, and condition.
     * If the condition is met and the key code is triggered, the action is executed.
     */
    public KeyBind(KeyCode code, Runnable action, Predicate<KeyEvent> condition) {
        this.code = code;
        this.action = action;
        this.condition = condition;
    }

    /**
     * Checks if the provided key event matches this key binding, and fulfils any extra conditions.
     */
    public boolean matches(KeyEvent event) {
        if (condition == null) {
            return event.getCode() == code;
        }
        return event.getCode() == code
                && condition.test(event);
    }

    public void execute() {
        action.run();
    }
}
