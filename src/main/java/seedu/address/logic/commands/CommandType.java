package seedu.address.logic.commands;

/**
 * Enum representing the types of commmand
 */
public enum CommandType {
    // Command Type for General Use
    LIST,
    CLEAR,
    HELP,
    EXIT,

    // Command Type for Students
    ADDSTUDENT,
    EDITSTUDENT,
    FINDSTUDENT,
    DELETESTUDENT,
    EXPORTSTUDENT,

    // Command Type for Consultations TODO
    ADDCONSULT,
    DELETECONSULT,
    REMOVEFROMCONSULT
}
