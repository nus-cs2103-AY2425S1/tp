package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * Lists all goods that match the given conditions to the user.
 */
public class ListGoodsCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all goods that match the specified conditions."
            + "All parameters are optional. \n"
            + "Parameters: [" + PREFIX_GOODS_NAME + "GOODS_NAME]"
            + "[" + PREFIX_CATEGORY + "GOODS_CATEGORY]"
            + "[" + PREFIX_NAME + "SUPPLIER_NAME]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GOODS_NAME + "BREAD";

    public static final String MESSAGE_SUCCESS = "Listed all matching goods.";

    private final Predicate<GoodsReceipt> predicate;

    public ListGoodsCommand(Predicate<GoodsReceipt> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReceiptsList(this.predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListGoodsCommand)) {
            return false;
        }

        ListGoodsCommand otherListGoodsCommand = (ListGoodsCommand) other;
        return predicate.equals(otherListGoodsCommand.predicate);
    }
}
