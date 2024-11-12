package seedu.eventfulnus.model.person.role.committee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PositionStringTest {
    @Test
    void getPositionString_validPosition_returnsCorrectString() {
        assertEquals("Project Director", PositionString.getPositionString(Position.PROJECT_DIRECTOR));
        assertEquals("Vice Project Director", PositionString.getPositionString(Position.VICE_PROJECT_DIRECTOR));
        assertEquals("Sports Director", PositionString.getPositionString(Position.SPORTS_DIRECTOR));
        assertEquals("Vice Sports Director", PositionString.getPositionString(Position.VICE_SPORTS_DIRECTOR));
        assertEquals("Member", PositionString.getPositionString(Position.MEMBER));
    }

    @Test
    void getPositionString_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PositionString.getPositionString(null));
    }
}
