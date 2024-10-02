package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ARRIVAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCUREMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.goods.Goods;

/**
 * Adds trackable goods linked to contacted suppliers.
 */
public class AddGoodsCommand extends Command {
    public static final String COMMAND_WORD = "goods";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds linked goods to the address book. "
            + "Parameters: "
            + PREFIX_GOODS_NAME + "GOODS_NAME "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_PROCUREMENT_DATE + "PROCUREMENT_DATE "
            + PREFIX_ARRIVAL_DATE + "ARRIVAL_DATE "
            + PREFIX_NAME + "SUPPLIER_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GOODS_NAME + "Gardenia Milk Bread "
            + PREFIX_QUANTITY + "2 "
            + PREFIX_PRICE + "5 "
            + PREFIX_CATEGORY + "CONSUMABLES "
            + PREFIX_PROCUREMENT_DATE + "2024-08-08 11:00 "
            + PREFIX_ARRIVAL_DATE + "2024-09-09 11:00 "
            + PREFIX_NAME + "Bread Seller \n";

    public static final String MESSAGE_SUCCESS = "New goods added: %1$s";

    private final Goods toAdd;

    /**
     * Creates an AddGoodsCommand for the goods
     */
    public AddGoodsCommand(Goods goods) {
        requireNonNull(goods);
        this.toAdd = goods;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: ADD GOODS TO STORAGE
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddGoodsCommand)) {
            return false;
        }

        AddGoodsCommand otherAddCommand = (AddGoodsCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
