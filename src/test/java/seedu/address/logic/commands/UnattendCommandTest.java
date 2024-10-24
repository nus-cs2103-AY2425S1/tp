package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.exceptions.TutDateNotFoundException;

public class UnattendCommandTest {

    private Model model;

    private final StudentId aliceId = ALICE.getStudentId();
    private final TutorialId tutorial1Id = TUTORIAL1.getTutorialId();
    private Date date;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();

        date = getTodayDateWithoutTime();

        model.addStudent(ALICE);
        model.addTutorial(TUTORIAL1);

        model.assignStudent(ALICE, tutorial1Id);
    }

    @Test
    public void execute_unattend_success() throws Exception {
        model.setStudentAttendance(aliceId, tutorial1Id, date);

        UnattendCommand command = new UnattendCommand(aliceId, tutorial1Id, date);

        CommandResult result = command.execute(model);

        String expectedMessage = UnattendCommand.MESSAGE_SUCCESS + "\n" + command;
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertThrows(TutDateNotFoundException.class, () -> model.setStudentAbsent(aliceId, tutorial1Id, date));
    }

    @Test
    public void execute_unattendTwice_failure() throws Exception {
        model.setStudentAttendance(aliceId, tutorial1Id, date);

        UnattendCommand command = new UnattendCommand(aliceId, tutorial1Id, date);

        CommandResult result = command.execute(model);

        String expectedMessage = UnattendCommand.MESSAGE_SUCCESS + "\n" + command;
        assertEquals(expectedMessage, result.getFeedbackToUser());

        assertThrows(TutDateNotFoundException.class, () -> model.setStudentAbsent(aliceId, tutorial1Id, date));
    }

    @Test
    public void execute_unattendWithoutPriorAttendance_failure() throws Exception {
        UnattendCommand command = new UnattendCommand(aliceId, tutorial1Id, date);

        assertThrows(TutDateNotFoundException.class, () -> command.execute(model));
    }

    @Test
    public void execute_tutorialDoesNotExist_failure() {
        TutorialId nonExistentTutorialId = TutorialId.of("T9999");

        UnattendCommand command = new UnattendCommand(aliceId, nonExistentTutorialId, date);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        UnattendCommand command = new UnattendCommand(aliceId, tutorial1Id, date);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    private static Date getTodayDateWithoutTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
