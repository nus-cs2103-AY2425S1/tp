package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfirmationWindowTest {
    @Test
    public void getInstance_singletonInstance() {
        ConfirmationWindow instance1 = ConfirmationWindow.getInstance();
        ConfirmationWindow instance2 = ConfirmationWindow.getInstance();
        assertTrue(instance1 == instance2);
    }
}
