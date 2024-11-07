package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.logic.commands.FilterCommand.MESSAGE_SUCCESS;
import static seedu.academyassist.testutil.TypicalPersons.ALICE;
import static seedu.academyassist.testutil.TypicalPersons.DANIEL;
import static seedu.academyassist.testutil.TypicalPersons.ELLE;
import static seedu.academyassist.testutil.TypicalPersons.FIONA;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.filter.FilterParam;
import seedu.academyassist.model.person.PersonInYearPredicate;
import seedu.academyassist.model.person.PersonTakeSubjectPredicate;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.model.person.YearGroup;

/**
 * Contains tests for {@code FilterCommand}
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void sortByYearGroup_success() {
        YearGroup yg = new YearGroup("2");
        FilterCommand command = new FilterCommand(new FilterParam("yearGroup"), yg);
        expectedModel.updateFilteredPersonList(new PersonInYearPredicate((YearGroup) yg));
        CommandResult cr = new CommandResult(String.format(MESSAGE_SUCCESS, "yearGroup", yg.toString(),
                expectedModel.getFilteredPersonList().size()));
        assertCommandSuccess(command, model, cr, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE), expectedModel.getFilteredPersonList());
    }

    @Test
    public void sortBySubject_success() {
        Subject subj = new Subject("English");
        FilterCommand command = new FilterCommand(new FilterParam("subject"), subj);
        expectedModel.updateFilteredPersonList(new PersonTakeSubjectPredicate((Subject) subj));
        CommandResult cr = new CommandResult(String.format(MESSAGE_SUCCESS, "subject", subj.toString(),
                expectedModel.getFilteredPersonList().size()));
        assertCommandSuccess(command, model, cr, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL, ELLE, FIONA), expectedModel.getFilteredPersonList());
    }

}
