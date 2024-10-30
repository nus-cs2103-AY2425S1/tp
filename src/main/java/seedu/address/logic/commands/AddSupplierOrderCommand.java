package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplierOrder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class AddSupplierOrderCommand extends Command {
    public static final String COMMAND_WORD = "addSupplierOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new supplier order to the bakery's order list. "
            + "Parameters: PHONE_NUMBER PRODUCT_ID\n"
            + "Example: " + COMMAND_WORD + " 87654321 1";

    public static final String MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS = "New supplier order added: \n%1$s";

    private final String phoneNumber;
    private final ArrayList<Integer> idList;

    public AddSupplierOrderCommand(String phoneNumber, ArrayList<Integer> idList) {
        requireAllNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.idList = idList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        IngredientCatalogue ingredientCatalogue = model.getIngredientCatalogue();

        List<Product> productList = idList.stream()
                                        .map(ingredientCatalogue::getProductById)
                .                       filter(Objects::nonNull)
                                        .toList();

        SupplierOrder supplyOrder = new SupplierOrder(phoneNumber, productList, OrderStatus.PENDING);

        List<Person> personList = model.getFilteredPersonList();
        Person person = Person.getSupplier();

        for (Person p : personList) {
            if (p.getPhone().equals(new Phone(phoneNumber))) {
                person = p;
            }
        }
        supplyOrder.setPerson(person);

        model.addSupplyOrder(supplyOrder);

        return new CommandResult(String.format(MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS, supplyOrder.viewOrder()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSupplierOrderCommand)) {
            return false;
        }

        AddSupplierOrderCommand otherCommand = (AddSupplierOrderCommand) other;
        return phoneNumber.equals(otherCommand.phoneNumber) && idList.equals(otherCommand.idList);
    }
}
