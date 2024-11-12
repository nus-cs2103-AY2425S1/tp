package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PUBLIC_ADDRESS_NETWORK;
import static seedu.address.model.addresses.PublicAddressesComposition.MESSAGE_DUPLICATE_PUBLIC_ADDRESS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;

/**
 * Represents a command to edit the public address of a person in the address book.
 */
public class EditPublicAddressCommand extends Command {

    public static final String COMMAND_WORD = "editpa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a public address of a contact.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "NETWORK "
            + PREFIX_PUBLIC_ADDRESS_LABEL + "LABEL "
            + PREFIX_PUBLIC_ADDRESS + "NEW_ADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PUBLIC_ADDRESS_NETWORK + "BTC "
            + PREFIX_PUBLIC_ADDRESS_LABEL + "MyWallet "
            + PREFIX_PUBLIC_ADDRESS + "14qViLJfdGaP4EeHnDyJbEGQysnCpwk3gd";

    public static final String MESSAGE_NON_MATCHING_LABEL = "Label does not match any %1$s public addresses of %2$s";

    public static final String MESSAGE_SUCCESS = "Edited Person: %1$s";

    private static final Logger logger = LogsCenter.getLogger(EditPublicAddressCommand.class);

    private final Index index;
    private final PublicAddress publicAddress;

    /**
     * Creates an EditPublicAddressCommand to edit the public address of a specified person.
     *
     * @param index         of the person in the filtered person list to edit
     * @param publicAddress new public address
     */
    public EditPublicAddressCommand(Index index, PublicAddress publicAddress) {
        requireAllNonNull(index, publicAddress);

        this.index = index;
        this.publicAddress = publicAddress;
    }

    /**
     * Executes the edit public address command by modifying the public address of the specified person.
     *
     * @param model Model where the command will be executed
     * @return CommandResult indicating the result of the operation
     * @throws CommandException if the command fails due to invalid input or state
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToEdit = getPersonToEdit(model);

        String paToAdd = publicAddress.getPublicAddressString();

        List<String> allPAs = model.getAddressBook().getPersonList().stream()
                .flatMap(person -> person.getPublicAddressesComposition()
                        .getAllPublicAddresses()
                        .stream())
                .map(PublicAddress::getPublicAddressString)
                .toList();

        for (String pa : allPAs) {
            if (paToAdd.equals(pa)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_PUBLIC_ADDRESS, paToAdd));
            }
        }

        if (!personToEdit.hasPublicAddressWithLabelWithinNetwork(publicAddress.getNetwork(), publicAddress.label)) {
            logger.warning(String.format(
                    "Attempted to edit a public address with a non-matching label %1$s for network %2$s on person %3$s",
                    publicAddress.label, publicAddress.getNetwork(), personToEdit.getName()));
            throw new CommandException(
                    String.format(MESSAGE_NON_MATCHING_LABEL, publicAddress.getNetwork(), personToEdit.getName()));
        }

        Person editedPerson = personToEdit.withUpdatedPublicAddress(publicAddress);
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    private Person getPersonToEdit(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(index.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPublicAddressCommand otherCommand)) {
            return false;
        }

        return index.equals(otherCommand.index) && publicAddress.equals(otherCommand.publicAddress);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("publicAddress", publicAddress)
                .toString();
    }

}
