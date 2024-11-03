package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
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

public class DeleteSchemeCommand extends Command {
    public static final String COMMAND_WORD = "deletescheme";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a scheme from the family identified by the "
            + "index number used in the displayed family list.\n"
            + "This command can only be used after Scheme command.\n"
            + "Parameters: PERSON_INDEX SCHEME_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 i/1";
    private final Index personIndex;
    private final ArrayList<Index> schemeIndex = new ArrayList<>();

    private final ArrayList<Scheme> schemesToBeDeleted = new ArrayList<>();

    public DeleteSchemeCommand(Index personIndex, ArrayList<Index> schemeIndex) {
        requireAllNonNull(personIndex, schemeIndex);
        this.personIndex = personIndex;
        this.schemeIndex.addAll(schemeIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size() || personIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person targetFamily = lastShownList.get(personIndex.getZeroBased());
        Person editedPerson = deleteSchemeFromPerson(targetFamily, schemeIndex);

        if (!targetFamily.isSamePerson(editedPerson)) {
            if (model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.updateAppointments(targetFamily.getName(), editedPerson.getName());
        }

        model.setPerson(targetFamily, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_DELETE_SCHEME_PERSON_SUCCESS);

    }

    private Person deleteSchemeFromPerson(Person targetFamily, ArrayList<Index> schemeIndex) throws CommandException {
        ArrayList<Scheme> currentSchemes = targetFamily.getSchemes();
        for (Index index : schemeIndex) {
            if (index.getZeroBased() >= currentSchemes.size() || index.getZeroBased() < 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_SCHEMES_DISPLAYED_INDEX);
            }
            schemesToBeDeleted.add(currentSchemes.get(index.getZeroBased()));
        }
        currentSchemes.removeAll(schemesToBeDeleted);

        Name updatedName = targetFamily.getName();
        Phone updatedPhone = targetFamily.getPhone();
        Email updatedEmail = targetFamily.getEmail();
        Address updatedAddress = targetFamily.getAddress();
        Priority updatedPriority = targetFamily.getPriority();
        Remark updatedRemark = targetFamily.getRemark();
        DateOfBirth updatedDateOfBirth = targetFamily.getDateOfBirth();
        Income updatedIncome = targetFamily.getIncome();
        FamilySize updatedFamilySize = targetFamily.getFamilySize();
        Set<Tag> updatedTags = targetFamily.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPriority, updatedRemark,
                updatedDateOfBirth, updatedIncome, updatedFamilySize, updatedTags, UpdatedAt.now(), currentSchemes);
    }

    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
