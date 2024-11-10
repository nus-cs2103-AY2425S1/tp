package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.person.Name;

/**
 * Deletes the goods receipt that belongs to a supplier.
 */
public class DeleteGoodsCommand extends Command {

    public static final String COMMAND_WORD = "deletegoods";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified goods from the specified supplier.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GOODS_NAME + "GOODS_NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_GOODS_NAME + "Gardenia Milk Bread";

    public static final String MESSAGE_SUCCESS = "%1$s has been deleted.";

    private final Name supplierName;
    private final GoodsName goodsName;


    /**
     * Constructs a delete goods command that deletes the goods receipt
     * with the specified supplier name and goods name.
     */
    public DeleteGoodsCommand(Name supplierName, GoodsName goodsName) {
        this.supplierName = supplierName;
        this.goodsName = goodsName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GoodsReceipt receipt = model
                .findGoodsReceipt(
                        r -> (r.isFromSupplier(supplierName)
                                && r.getGoods().getGoodsName().equals(goodsName)))
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_GOODS_RECEIPT_NOT_FOUND));
        model.deleteGoods(receipt);
        return new CommandResult(String.format(MESSAGE_SUCCESS, goodsName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGoodsCommand otherDeleteGoodsCommand)) {
            return false;
        }

        return goodsName.equals(otherDeleteGoodsCommand.goodsName);
    }
}
