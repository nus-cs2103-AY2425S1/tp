package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    // AddSupplier/AddProduct
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // AddSupplier
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");

    // AddProduct
    public static final Prefix PREFIX_STOCK_LEVEL = new Prefix("stk/");
    public static final Prefix PREFIX_MIN_STOCK_LEVEL = new Prefix("min/");
    public static final Prefix PREFIX_MAX_STOCK_LEVEL = new Prefix("max/");
    public static final Prefix PREFIX_PRODUCT_SUPPLIER_NAME = new Prefix("su/");

    // AssignProduct
    public static final Prefix PREFIX_PRODUCT_NAME = new Prefix("pr/");
    public static final Prefix PREFIX_SUPPLIER_NAME = new Prefix("su/");
}
