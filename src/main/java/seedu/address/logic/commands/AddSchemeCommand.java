package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_ADD_SCHEME_PERSON_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_ADD_SCHEME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.person.UpdatedAt;
import seedu.address.model.scheme.Scheme;
import seedu.address.model.scheme.SchemeRetrieval;
import seedu.address.model.tag.Tag;

/**
 * Adds a scheme to a person in the address book.
 */
public class AddSchemeCommand extends Command {

    public static final String COMMAND_WORD = "addscheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a scheme to the person identified by the "
            + "index number used in the displayed person list.\n"
            + "This command can only be used after Scheme command.\n"
            + "Parameters: PERSON_INDEX SCHEME_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 i/1";

    private final Index personIndex;
    private final Index schemeIndex;

    private final ArrayList<Scheme> newSchemes = new ArrayList<>();

    private Scheme schemeToBeAdded;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * Creates an AddSchemeCommand to add the specified {@code Scheme} to the person at the specified {@code Index}.
     */
    public AddSchemeCommand(Index personIndex, Index schemeIndex) {
        requireAllNonNull(personIndex, schemeIndex);
        this.personIndex = personIndex;
        this.schemeIndex = schemeIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ArrayList<Scheme> schemes = getSchemes(model);
        if (schemes.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_SCHEMES_AVAILABLE);
        }
        if (schemeIndex.getZeroBased() >= schemes.size() || schemeIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEMES_DISPLAYED_INDEX);
        }
        Scheme targetScheme = schemes.get(schemeIndex.getZeroBased());
        List<Person> lastShownList = model.getFilteredPersonList();
        personToEdit = lastShownList.get(personIndex.getZeroBased());
        editedPerson = addSchemeToPerson(personToEdit, targetScheme);

        if (!personToEdit.isSamePerson(editedPerson)) {
            if (model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.updateAppointments(personToEdit.getName(), editedPerson.getName());
        }

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_ADD_SCHEME_PERSON_SUCCESS, editedPerson.getName()));
    }

    /**
     * Retrieves the schemes from the last command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return List of schemes.
     * @throws CommandException If any of the index given falls out of range.
     */
    private ArrayList<Scheme> getSchemes(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size() || personIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetFamily = lastShownList.get(personIndex.getZeroBased());
        SchemeRetrieval schemeRetrieval = new SchemeRetrieval(targetFamily);
        return schemeRetrieval.getSchemes();
    }

    /**
     * Adds a scheme to a person.
     *
     * @param person Person to be edited.
     * @param scheme Scheme to be added.
     * @return Edited person.
     * @throws CommandException If the scheme already exists in the family.
     */
    private Person addSchemeToPerson(Person person, Scheme scheme) throws CommandException {
        ArrayList<Scheme> currentSchemes = person.getSchemes();
        if (currentSchemes.contains(scheme)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_SCHEME);
        }
        schemeToBeAdded = scheme;
        newSchemes.addAll(currentSchemes);
        newSchemes.add(scheme);
        Name updatedName = person.getName();
        Phone updatedPhone = person.getPhone();
        Email updatedEmail = person.getEmail();
        Address updatedAddress = person.getAddress();
        Priority updatedPriority = person.getPriority();
        Remark updatedRemark = person.getRemark();
        DateOfBirth updatedDateOfBirth = person.getDateOfBirth();
        Income updatedIncome = person.getIncome();
        FamilySize updatedFamilySize = person.getFamilySize();
        Set<Tag> updatedTags = person.getTags();
        boolean isArchived = person.isArchived();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPriority, updatedRemark,
                updatedDateOfBirth, updatedIncome, updatedFamilySize, updatedTags, newSchemes, UpdatedAt.now(),
                isArchived);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSchemeCommand)) {
            return false;
        }

        AddSchemeCommand otherAddSchemeCommand = (AddSchemeCommand) other;
        return personIndex.equals(otherAddSchemeCommand.personIndex)
                && schemeIndex.equals(otherAddSchemeCommand.schemeIndex);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    public Person getUneditedPerson() {
        return personToEdit;
    }

    public Person getEditedPerson() {
        return editedPerson;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        Person beforeEdit = this.getUneditedPerson();
        Person afterEdit = this.getEditedPerson();
        model.setPerson(afterEdit, beforeEdit);
        pastCommands.remove();
        StringBuilder schemeNames = new StringBuilder(MESSAGE_UNDO_ADD_SCHEME);
        schemeNames.append(schemeToBeAdded.getSchemeName()).append("\n");
        return String.format(schemeNames.toString(), beforeEdit.getName());
    }
}
