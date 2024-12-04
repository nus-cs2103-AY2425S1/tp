package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;

/**
 * Assigns an existing product to an existing supplier in the address book.
 */
public class AutoCompleteCommand extends Command {

    public static final String SUGGESTIONS_DELIMITER = "\t\t\t";
    public static final int MAX_SUGGESTIONS = 8;

    private final String currentInput;
    private final Prefix inputType;

    /**
     * @param currentInput The current input the user is typing.
     */
    public AutoCompleteCommand(String currentInput, String inputType) {
        this.currentInput = currentInput;
        if (inputType == null) {
            this.inputType = null;
        } else {
            this.inputType = new Prefix(inputType);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<String> possibleCompletions = new ArrayList<>();

        if (inputType == null) {
            possibleCompletions = CommandWords.getCommandWords();
        } else if (inputType.equals(PREFIX_SUPPLIER_NAME)) {
            possibleCompletions = model.getModifiedSupplierList().stream()
                    .map(Supplier::getName)
                    .map(name -> name.fullName)
                    .collect(Collectors.toList());
        } else if (inputType.equals(PREFIX_PRODUCT_NAME)) {
            possibleCompletions = model.getModifiedProductList().stream()
                    .map(Product::getName)
                    .map(name -> name.getOriginalName())
                    .collect(Collectors.toList());
        } else if (inputType.equals(PREFIX_TAG)) {
            Set<String> tagSet = new HashSet<>();

            model.getModifiedProductList().stream()
                    .flatMap(product -> product.getTags().stream())
                    .map(tag -> tag.tagName)
                    .forEach(tagSet::add);

            model.getModifiedSupplierList().stream()
                    .flatMap(supplier -> supplier.getTags().stream())
                    .map(tag -> tag.tagName)
                    .forEach(tagSet::add);

            possibleCompletions = new ArrayList<>(tagSet);
        }

        List<String> filteredCompletions = possibleCompletions.stream()
                .filter(completion -> completion.startsWith(currentInput))
                .collect(Collectors.toList());

        java.util.Collections.sort(filteredCompletions);

        if (filteredCompletions.size() > MAX_SUGGESTIONS) {
            filteredCompletions = filteredCompletions.subList(0, MAX_SUGGESTIONS);
        }

        if (filteredCompletions.size() == 0) {
            return new CommandResult("", false, false, false, false);
        } else if (filteredCompletions.size() == 1) {
            return new CommandResult(filteredCompletions.get(0).substring(currentInput.length()),
                false, false, true, false);
        }

        // TODO: Only a single tab character doesn't work ... why?
        // TODO: Handle text not fitting
        return new CommandResult(String.join(SUGGESTIONS_DELIMITER, filteredCompletions));
    }

    /**
     * Returns the current input the user is typing.
     */
    public String getCurrentInput() {
        return currentInput;
    }

    /**
     * Returns the type of input the user is typing.
     */
    public Prefix getInputType() {
        return inputType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof AutoCompleteCommand) {
            AutoCompleteCommand otherCommand = (AutoCompleteCommand) other;
            return this.currentInput.equals(otherCommand.currentInput)
                    && this.inputType.equals(otherCommand.inputType);
        }

        return false;
    }

    @Override
    public int hashCode() {
        // Use Objects.hash to generate a hash code based on the fields
        return Objects.hash(inputType, currentInput);
    }
}
