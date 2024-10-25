package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

/**
 * Adds a property to the list of properties to buy for a specific contact.
 */
public class AddPropertyToBuyCommand extends Command {
    public static final String COMMAND_WORD = "addBuy";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the list of properties to buy"
            + " for this specific person. \n"
            + "Parameters: INDEX (Must be a positive integer) "
            + PREFIX_HOUSING_TYPE + "[HOUSING_TYPE] "
            + PREFIX_BUYING_PRICE + "[SELLING_PRICE] "
            + PREFIX_POSTAL_CODE + "[POSTAL_CODE] "
            + PREFIX_UNIT_NUMBER + "[UNIT_NUMBER] "
            + PREFIX_TAG + "[TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_HOUSING_TYPE + "c "
            + PREFIX_BUYING_PRICE + "1650000 "
            + PREFIX_POSTAL_CODE + "567510 "
            + PREFIX_UNIT_NUMBER + "10-65 "
            + PREFIX_TAG + "Extremely spacious"
            + PREFIX_TAG + "Near MRT";

    public static final String MESSAGE_SUCCESS = "New property added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists "
            + "in the list of properties to buy";

    private final Index index;
    private final Property propertyToBuyToBeAdded;

    /**
     *  Creates an AddPropertyToBuyCommand to add the specified {@code Property}
     * to the list of properties to buy for the contact at the specified {@code Index}.
     *
     * @param personIndex of the person in the filtered person list to edit
     * @param property property to be added to the list of properties to buy
     */
    public AddPropertyToBuyCommand(Index personIndex, Property property) {
        requireNonNull(personIndex);
        requireNonNull(property);

        this.index = personIndex;
        this.propertyToBuyToBeAdded = property;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        personToEdit.containsBuyProperty(propertyToBuyToBeAdded);
        if (personToEdit.containsBuyProperty(propertyToBuyToBeAdded)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        } else {
            personToEdit.addBuyProperty(propertyToBuyToBeAdded);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, propertyToBuyToBeAdded));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPropertyToBuyCommand // instanceof handles nulls
                && propertyToBuyToBeAdded.equals(((AddPropertyToBuyCommand) other).propertyToBuyToBeAdded));
    }
}
