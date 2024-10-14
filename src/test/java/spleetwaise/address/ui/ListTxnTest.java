package spleetwaise.address.ui;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.matcher.control.TextInputControlMatchers;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListTxnTest extends TestFxJUnitAppRunner {

    private static final String TEXTAREA_FOR_RESULTDISPLAY = "#resultDisplay";
    private static final String PLACEHOLDER_FOR_COMMANDBOX = "#commandBoxPlaceholder";
    private static final String TEXT_AREA_BEFORE_COMMAND = "";
    private static final String TEXT_AREA_AFTER_COMMAND = "Listed all transactions";

    @Test
    @Order(1)
    @DisplayName("should verify resultDisplay empty before list command")
    public void verify_resultIsEmpty_beforeList() {
        TextArea textArea = lookup(TEXTAREA_FOR_RESULTDISPLAY).query();

        verifyThat(textArea, TextInputControlMatchers.hasText(TEXT_AREA_BEFORE_COMMAND));
    }

    @Test
    @Order(2)
    @DisplayName("should verify resultDisplay is success msg after list command")
    public void verify_resultIsSuccess_afterList() {
        clickOn(PLACEHOLDER_FOR_COMMANDBOX, MouseButton.PRIMARY).write("listTxn");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        TextArea textArea = lookup(TEXTAREA_FOR_RESULTDISPLAY).query();

        verifyThat(textArea, TextInputControlMatchers.hasText(TEXT_AREA_AFTER_COMMAND));
    }
}
