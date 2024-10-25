package seedu.address.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.function.Predicate;

public class KeyBind {
    private final KeyCode code;
    private final Runnable action;
    private final Predicate<KeyEvent> condition;

    public KeyBind(KeyCode code, Runnable action) {
        this.code = code;
        this.action = action;
        this.condition = x -> true;
    }

    public KeyBind(KeyCode code, Runnable action, Predicate<KeyEvent> condition) {
        this.code = code;
        this.action = action;
        this.condition = condition;
    }


    public boolean matches(KeyEvent event) {
        return event.getCode() == code
                && condition.test(event);
    }

    public void execute() {
        action.run();
    }
}