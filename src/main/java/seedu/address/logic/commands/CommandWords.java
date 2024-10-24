package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains the list of all command words (for input).
 */
public class CommandWords {
    public static final String ADD_PRODUCT_COMMAND = "add_product";
    public static final String ADD_SUPPLIER_COMMAND = "add_supplier";
    public static final String ASSIGN_PRODUCT_COMMAND = "assign";
    public static final String CLEAR_COMMAND = "clear";
    public static final String DELETE_COMMAND = "delete";
    public static final String DELETE_PRODUCT_COMMAND = "deleteproduct";
    public static final String DELETE_SUPPLIER_COMMAND = "deletesupplier";
    public static final String EDIT_COMMAND = "edit";
    public static final String EXIT_COMMAND = "exit";
    public static final String HELP_COMMAND = "help";
    public static final String LIST_COMMAND = "list";
    public static final String SET_THRESHOLD_COMMAND = "setthreshold";
    public static final String UNASSIGN_PRODUCT_COMMAND = "unassign_product";
    public static final String UPDATE_MAX_STOCK_LEVEL_COMMAND = "update_max_stock";
    public static final String UPDATE_STOCK_COMMAND = "update_stock";
    public static final String VIEW_PRODUCT_COMMAND = "viewproduct";
    public static final String VIEW_SUPPLIER_COMMAND = "viewsupplier";

    /**
     * Returns a list of all command keywords.
     */
    public static List<String> getCommandWords() {
        return new ArrayList<>(Arrays.asList(
            ADD_PRODUCT_COMMAND,
            ADD_SUPPLIER_COMMAND,
            ASSIGN_PRODUCT_COMMAND,
            CLEAR_COMMAND,
            DELETE_COMMAND,
            DELETE_PRODUCT_COMMAND,
            DELETE_SUPPLIER_COMMAND,
            EDIT_COMMAND,
            EXIT_COMMAND,
            HELP_COMMAND,
            LIST_COMMAND,
            SET_THRESHOLD_COMMAND,
            UNASSIGN_PRODUCT_COMMAND,
            UPDATE_MAX_STOCK_LEVEL_COMMAND,
            UPDATE_STOCK_COMMAND,
            VIEW_PRODUCT_COMMAND,
            VIEW_SUPPLIER_COMMAND
        ));
    }
}
