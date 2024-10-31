package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.person.Name;
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
    public static final String COMMAND_WORD = "addSupplyOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new supplier order to the bakery's order list. "
            + "Parameters: PHONE_NUMBER PRODUCT_ID\n"
            + "Example: " + COMMAND_WORD + " 87654321 1";

    public static final String MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS = "New supplier order added: \n%1$s";

    private final Name name;
    private final Phone phone;
    private final ArrayList<Integer> idList;

    public AddSupplierOrderCommand(Name name, Phone phone, ArrayList<Integer> idList) {
        requireAllNonNull(phone);
        this.name = name;
        this.phone = phone;
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

        List<Person> personList = model.getFilteredPersonList();
        Person person = null;

        for (Person p : personList) {
            if (p.getPhone().equals(phone)) {
                person = p;

            }
        }
        if (person == null) {
            person = Person.getGuest(name, phone);
            model.addPerson(person);
        }

        SupplyOrder supplyOrder = new SupplyOrder(person, productList, OrderStatus.PENDING);

        person.addOrder(supplyOrder);

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
        return phone.equals(otherCommand.phone) && idList.equals(otherCommand.idList);
    }
}
