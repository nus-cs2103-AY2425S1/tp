package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGoods.getTypicalGoodsReceipts;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model getDefaultModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalGoodsReceipts());
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        for (Person person : getDefaultModel().getPersonList()) {
            EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(
                    Optional.of(person.getName()),
                    Optional.of(person.getPhone()),
                    Optional.of(person.getAddress()),
                    Optional.of(person.getTags()));
            EditCommand editCommand = new EditCommand(person.getName(), editPersonDescriptor);

            String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(person));
            assertCommandSuccess(editCommand, getDefaultModel(), expectedMessage, getDefaultModel());
        }
    }

    private List<EditPersonDescriptor> permuteEditFieldDescriptor(Name name, Phone phone,
                                                                       Address address, Set<Tag> tags) {
        ArrayList<EditPersonDescriptor> editPersonDescriptors = new ArrayList<>();
        for (boolean editName : List.of(true, false)) {
            for (boolean editPhone : List.of(true, false)) {
                for (boolean editAddress : List.of(true, false)) {
                    for (boolean editTags : List.of(true, false)) {
                        editPersonDescriptors.add(new EditPersonDescriptor(
                                editName ? Optional.of(name) : Optional.empty(),
                                editPhone ? Optional.of(phone) : Optional.empty(),
                                editAddress ? Optional.of(address) : Optional.empty(),
                                editTags ? Optional.of(tags) : Optional.empty()));
                    }
                }
            }
        }
        return editPersonDescriptors;
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        for (EditPersonDescriptor editPersonDescriptor : permuteEditFieldDescriptor(
                new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB),
                new Address(VALID_ADDRESS_BOB), Set.of(new Tag(VALID_TAG_HUSBAND)))) {

            Optional<Name> name = editPersonDescriptor.name();
            Optional<Phone> phone = editPersonDescriptor.phone();
            Optional<Address> address = editPersonDescriptor.address();
            Optional<Set<Tag>> tags = editPersonDescriptor.tags();

            for (Person person : getDefaultModel().getPersonList()) {

                EditCommand editCommand =
                        new EditCommand(person.getName(), editPersonDescriptor);

                Person editedPerson = new Person(
                        name.orElse(person.getName()),
                        phone.orElse(person.getPhone()),
                        address.orElse(person.getAddress()),
                        tags.orElse(person.getTags()));

                String expectedMessage = String.format(
                        EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

                Model expectedModel = getDefaultModel();
                expectedModel.setPerson(person, editedPerson);

                assertCommandSuccess(editCommand, getDefaultModel(), expectedMessage, expectedModel);
            }
        }
    }

    @Test
    public void execute_noFieldsSpecified_success() {
        for (Person person : getDefaultModel().getPersonList()) {
            EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
            EditCommand editCommand = new EditCommand(person.getName(), editPersonDescriptor);

            String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(person));
            assertCommandSuccess(editCommand, getDefaultModel(), expectedMessage, getDefaultModel());
        }
    }

    @Test
    public void execute_duplicatePerson_failure() {
        List<Person> persons = getDefaultModel().getPersonList();
        for (Person person1 : persons) {
            for (Person person2 : persons) {
                if (person1 == person2) {
                    continue;
                }
                EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(
                        Optional.of(person2.getName()), Optional.empty(),
                        Optional.empty(), Optional.empty());
                EditCommand editCommand = new EditCommand(person1.getName(), editPersonDescriptor);
                assertCommandFailure(editCommand, getDefaultModel(), EditCommand.MESSAGE_DUPLICATE_PERSON);
            }
        }
    }

    @Test
    public void execute_nonExistentPerson_failure() {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(
                Optional.of(new Name(VALID_NAME_BOB)), Optional.empty(),
                Optional.empty(), Optional.empty());
        EditCommand editCommand = new EditCommand(new Name(VALID_NAME_BOB), editPersonDescriptor);

        assertCommandFailure(editCommand, getDefaultModel(), Messages.MESSAGE_PERSON_NOT_FOUND);
    }
}
