package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;
import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validStudent);

        assertCommandSuccess(new AddStudentCommand(validStudent), model,
                String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                expectedModel);
    }

    @Test
    public void execute_newCompany_success() {
        Company validCompany = new CompanyBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validCompany);

        assertCommandSuccess(new AddCompanyCommand(validCompany), model,
                String.format(AddCompanyCommand.MESSAGE_SUCCESS, Messages.format(validCompany)),
                expectedModel);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student studentInList = (Student) model.getAddressBook().getPersonList().stream()
                .filter(person -> person instanceof Student).findFirst().orElse(null);
        if (studentInList != null) {
            assertCommandFailure(new AddStudentCommand(studentInList), model,
                    AddStudentCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company companyInList = (Company) model.getAddressBook().getPersonList().stream()
                .filter(person -> person instanceof Company).findFirst().orElse(null);
        if (companyInList != null) {
            assertCommandFailure(new AddCompanyCommand(companyInList), model,
                    AddCompanyCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }
}
