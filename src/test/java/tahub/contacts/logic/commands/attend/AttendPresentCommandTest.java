package tahub.contacts.logic.commands.attend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tahub.contacts.logic.commands.attend.AttendCommandTestUtil.ModelStubWithScaList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tahub.contacts.commons.util.ToStringBuilder;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.CommandResult;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.testutil.Assert;
import tahub.contacts.testutil.ScaBuilder;

public class AttendPresentCommandTest {
    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendPresentCommand(null));
    }

    // ====================== EXECUTE ==========================
    @Test
    public void execute_successfulScaMatch_successfulAttendanceAdd() throws CommandException {
        StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        ModelStubWithScaList modelStub =
                new ModelStubWithScaList(new AttendCommandTestUtil.ScaListWithMatch(targetSca));

        CommandResult commandResult = new AttendPresentCommand(targetSca).execute(modelStub);

        assertEquals(String.format(AttendPresentCommand.MESSAGE_SUCCESS, Messages.format(targetSca)),
                commandResult.getFeedbackToUser());
        assertEquals(targetSca.getAttendance().getAttendanceAttendedCount(), 1);
        assertEquals(targetSca.getAttendance().getAttendanceTotalCount(), 1);
    }

    @Test
    public void execute_unsuccessfulScaMatch_throwsCommandException() {
        StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        ModelStubWithScaList modelStub =
                new ModelStubWithScaList(new AttendCommandTestUtil.ScaListNoMatch());
        AttendPresentCommand attendPresentCommand = new AttendPresentCommand(targetSca);

        String expectedExceptionMessage = String.format(
                AttendPresentCommand.MESSAGE_NO_SCA_FOUND, Messages.format(targetSca));

        Assert.assertThrows(CommandException.class,
                expectedExceptionMessage, () -> attendPresentCommand.execute(modelStub));
    }

    // ====================== MISC ==========================

    @Nested
    @DisplayName("Equals")
    class Equals {
        private final StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        private final AttendPresentCommand a1 = new AttendPresentCommand(targetSca);
        @Nested
        @DisplayName("returns true for")
        class TrueEquals {
            @Test
            @DisplayName("same AttendPresentCommand object")
            public void sameAttendPresentCommandObject() {
                assertEquals(a1, a1);
            }

            @Test
            @DisplayName("same targetSca")
            public void sameTargetSca() {
                AttendPresentCommand a2 = new AttendPresentCommand(targetSca);
                assertEquals(a1, a2);
            }
        }

        @Nested
        @DisplayName("returns false for")
        class FalseEquals {
            @Test
            @DisplayName("different targetSca")
            public void differentTargetSca() {
                AttendPresentCommand a2 = new AttendPresentCommand(ScaBuilder.createSampleSecond());
                assertNotEquals(a1, a2);
            }

            @Test
            @DisplayName("null")
            public void compareNull() {
                assertNotEquals(a1, null);
            }

            @Test
            @DisplayName("different type")
            public void differentType() {
                String a2 = "different-type";
                assertNotEquals(a1, a2);
            }
        }
    }

    // toString tests
    @Nested
    @DisplayName("toString")
    class ToString {
        private final StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        private final AttendPresentCommand a1 = new AttendPresentCommand(targetSca);

        @Test
        @DisplayName("shows correct string")
        public void correctString() {
            String expected = new ToStringBuilder(a1)
                    .add("targetSCA", targetSca)
                    .toString();
            assertEquals(a1.toString(), expected);
        }
    }
}
