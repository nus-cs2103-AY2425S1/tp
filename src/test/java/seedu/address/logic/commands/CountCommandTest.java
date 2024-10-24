package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class CountCommandTest {
    @Test
    public void toStringMethod() {
        Optional<String> tag = Optional.of("friends");
        CountCommand countCommand = new CountCommand(null, tag);
        String expected = CountCommand.class.getCanonicalName() + "{tag=" + tag + "}";
        assertEquals(expected, countCommand.toString());
    }
}
