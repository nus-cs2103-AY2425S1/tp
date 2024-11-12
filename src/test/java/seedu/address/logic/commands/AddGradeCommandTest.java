package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddGradeCommandTest {
    private static final Index VALID_INDEX = Index.fromOneBased(16);
    private static final Grade VALID_GRADE = new Grade("Midterm", 95F, 30F);

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGradeCommand(null, VALID_GRADE));
    }

    @Test
    public void constructor_nullGrade_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGradeCommand(VALID_INDEX, null));
    }

    @Test
    public void addGrade_exceedsWeightage_throwsCommandException() throws CommandException {
        Grade firstGrade = new Grade("Midterm", 86.4F, 50.0F); // 50% weightage
        Grade secondGrade = new Grade("Final", 86.4F, 50.0F); // 50% weightage
        Grade thirdGrade = new Grade("Extra Assignment", 1.0F, 10.0F); // Exceeds 100%

        Person person = new PersonBuilder(ALICE).build();
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        // Add first grade
        AddGradeCommand addFirstGradeCommand = new AddGradeCommand(Index.fromZeroBased(0), firstGrade);
        model.addPerson(person);
        addFirstGradeCommand.execute(model);

        // Add second grade
        AddGradeCommand addSecondGradeCommand = new AddGradeCommand(Index.fromZeroBased(0), secondGrade);
        addSecondGradeCommand.execute(model);

        // Add third grade, which will cause the weightage to exceed 100%
        AddGradeCommand addThirdGradeCommand = new AddGradeCommand(Index.fromZeroBased(0), thirdGrade);

        // Execute and expect CommandException to be thrown
        assertThrows(CommandException.class, () -> addThirdGradeCommand.execute(model));
    }

}
