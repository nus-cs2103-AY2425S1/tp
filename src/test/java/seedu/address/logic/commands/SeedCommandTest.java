package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

public class SeedCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        ReadOnlyAddressBook sampleData = SampleDataUtil.getSampleAddressBook();
        expectedModel = new ModelManager(sampleData, new UserPrefs());
    }

    @Test
    public void execute_emptySocialBook_showPopulatedSocialBook() {
        assertCommandSuccess(new SeedCommand(), model, SeedCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptySocialBook_showPopulatedSocialBook() {
        model.addPerson(SampleDataUtil.getSamplePersons()[0]);
        model.addPerson(SampleDataUtil.getSamplePersons()[1]);
        model.addPerson(SampleDataUtil.getSamplePersons()[2]);
        assertCommandSuccess(new SeedCommand(), model, SeedCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
