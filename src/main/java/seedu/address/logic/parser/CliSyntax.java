package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_STUDENT_NAME = new Prefix("sn/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_GROUP_NAME = new Prefix("gn/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("sno/");
    public static final Prefix PREFIX_TASK_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TASK_DEADLINE = new Prefix("td/");
    public static final Prefix PREFIX_TASK_STATUS = new Prefix("ts/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_QUERY = new Prefix("q/");
    public static final ArrayList<Prefix> ALL_PREFIX = new ArrayList<Prefix>(Arrays.asList(
        PREFIX_INDEX,
        PREFIX_EMAIL, PREFIX_TAG, PREFIX_INDEX, PREFIX_STUDENT_NUMBER,
        PREFIX_GROUP_NAME, PREFIX_QUERY,
        PREFIX_TASK_NAME, PREFIX_TASK_STATUS, PREFIX_TASK_DEADLINE));
}
