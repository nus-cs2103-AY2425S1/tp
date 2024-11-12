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

public class AttendClearCommandTest {
    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendClearCommand(null));
    }

    // ====================== EXECUTE ==========================
    @Test
    public void execute_successfulScaMatch_successfulAttendanceAdd() throws CommandException {
        StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        ModelStubWithScaList modelStub =
                new ModelStubWithScaList(new AttendCommandTestUtil.ScaListWithMatch(targetSca));

        // add attendance to targetSca first
        targetSca.getAttendance().addAttendedLesson();
        targetSca.getAttendance().addAttendedLesson();
        targetSca.getAttendance().addAbsentLesson();
        targetSca.getAttendance().addAttendedLesson();

        CommandResult commandResult = new AttendClearCommand(targetSca).execute(modelStub);

        assertEquals(String.format(AttendClearCommand.MESSAGE_SUCCESS, Messages.format(targetSca)),
                commandResult.getFeedbackToUser());
        assertEquals(targetSca.getAttendance().getAttendanceAttendedCount(), 0);
        assertEquals(targetSca.getAttendance().getAttendanceTotalCount(), 0);
    }

    @Test
    public void execute_unsuccessfulScaMatch_throwsCommandException() {
        StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        ModelStubWithScaList modelStub =
                new ModelStubWithScaList(new AttendCommandTestUtil.ScaListNoMatch());
        AttendClearCommand attendClearCommand = new AttendClearCommand(targetSca);

        String expectedExceptionMessage = String.format(
                AttendClearCommand.MESSAGE_NO_SCA_FOUND, Messages.format(targetSca));

        Assert.assertThrows(CommandException.class,
                expectedExceptionMessage, () -> attendClearCommand.execute(modelStub));
    }

    // ====================== MISC ==========================

    @Nested
    @DisplayName("Equals")
    class Equals {
        private final StudentCourseAssociation targetSca = AttendCommandTestUtil.getNewScaToTestAttendance();
        private final AttendClearCommand a1 = new AttendClearCommand(targetSca);
        @Nested
        @DisplayName("returns true for")
        class TrueEquals {
            @Test
            @DisplayName("same AttendClearCommand object")
            public void sameAttendClearCommandObject() {
                assertEquals(a1, a1);
            }

            @Test
            @DisplayName("same targetSca")
            public void sameTargetSca() {
                AttendClearCommand a2 = new AttendClearCommand(targetSca);
                assertEquals(a1, a2);
            }
        }

        @Nested
        @DisplayName("returns false for")
        class FalseEquals {
            @Test
            @DisplayName("different targetSca")
            public void differentTargetSca() {
                AttendClearCommand a2 = new AttendClearCommand(ScaBuilder.createSampleSecond());
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
        private final AttendClearCommand a1 = new AttendClearCommand(targetSca);

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
