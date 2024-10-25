package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/"); //TODO: Add test for this
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_LOG = new Prefix("l/");
    public static final Prefix PREFIX_PROPERTY_ADDRESS = new Prefix("address/");
    public static final Prefix PREFIX_TOWN = new Prefix("town/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_SIZE = new Prefix("size/");
    public static final Prefix PREFIX_BEDROOMS = new Prefix("bed/");
    public static final Prefix PREFIX_BATHROOMS = new Prefix("bath/");
    public static final Prefix PREFIX_PRICE = new Prefix("price/");

}