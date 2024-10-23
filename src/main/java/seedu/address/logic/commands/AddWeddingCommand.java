package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a wedding to the address book.
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds a wedding to the address book.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_CONTACT + "CONTACT_INDEX...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Wedding1 "
            + PREFIX_DATE + "12/12/2024 "
            + PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";

    private final Wedding toAdd;
    private final Set<Index> contactIndexes;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}
     */
    public AddWeddingCommand(Wedding wedding, Set<Index> contactIndexes) {
        requireNonNull(wedding);
        toAdd = wedding;
        this.contactIndexes = contactIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<PersonId> assignedPersonIds = new ArrayList<>(toAdd.getAssignees());

        if (contactIndexes != null && !contactIndexes.isEmpty()) {
            for (Index index : contactIndexes) {
                if (index.getZeroBased() >= lastShownPersonList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                Person personToAssign = lastShownPersonList.get(index.getZeroBased());
                if (assignedPersonIds.contains(personToAssign.getId())) {
                    throw new CommandException(personToAssign.getName().toString()
                            + " has already been assigned to this wedding.");
                }
                assignedPersonIds.add(personToAssign.getId());
            }
        }

        Wedding newWedding = new Wedding(toAdd.getWeddingName(), toAdd.getWeddingDate(), assignedPersonIds);
        model.addWedding(newWedding);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatWedding(newWedding)));
    }

    public Wedding getWedding() {
        return toAdd;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddWeddingCommand
                && toAdd.equals(((AddWeddingCommand) other).toAdd));
    }
}
