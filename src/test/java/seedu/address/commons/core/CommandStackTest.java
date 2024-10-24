package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CommandStackTest {
    private static final String C1 = "find c/1";
    private static final String C2 = "find c/2";
    private static final String C3 = "find c/3";
    private static final String C4 = "find c/4";
    private static final String C5 = "find c/5";
    private static final CommandGetterResult CGR1 = CommandGetterResult.ofEmpty().updateStringToDisplay(C1);
    private static final CommandGetterResult CGR2 = CommandGetterResult.ofEmpty().updateStringToDisplay(C2);
    private static final CommandGetterResult CGR3 = CommandGetterResult.ofEmpty().updateStringToDisplay(C3);
    private static final CommandGetterResult CGR4 = CommandGetterResult.ofEmpty().updateStringToDisplay(C4);
    private static final CommandGetterResult CGR5 = CommandGetterResult.ofEmpty().updateStringToDisplay(C5);
    @Test
    public void commandStackConstructor_correctParameters_valuesAsExpected() {
        CommandStack commandStackEmpty = new CommandStack();
        CommandStack commandStackPopulated = new CommandStack(new ArrayList<>());
        assertEquals(commandStackEmpty.getCommandArrayList(), new ArrayList<>());
        assertEquals(commandStackEmpty.getCommandArrayListMaxSize(), CommandStack.DEFAULT_COMMAND_ARRAY_LIST_MAX_SIZE);

        assertEquals(commandStackPopulated.getCommandArrayList(), new ArrayList<>());
        assertEquals(commandStackPopulated.getCommandArrayListMaxSize(),
                CommandStack.DEFAULT_COMMAND_ARRAY_LIST_MAX_SIZE);
    }
    @Test
    public void commandStackConstructor_modifiedExternalArrayList_valuesAsExpected() {
        ArrayList<String> extArrayList = new ArrayList<>();
        CommandStack extCommandStack = new CommandStack(extArrayList);
        extArrayList.add("asdfasdfas");
        assertEquals(extCommandStack.getCommandArrayList(), new ArrayList<>());
    }
    @Test
    public void commandStackConstructor_nullArrayList_failure() {
        assertThrows(NullPointerException.class, () -> new CommandStack(null));
        assertThrows(NullPointerException.class, () -> new CommandStack(null, 1));
    }
    @Test
    public void getEarlierCommandGetterResult_smallStack_valuesAsExpected() {
        CommandStack extCommandStack = initSmallStack();
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR3);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR2);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR1);
        // user tried to go even earlier but gets the same thing
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR1);
    }

    @Test
    public void getLaterCommandGetterResult_smallStack_valuesAsExpected() {
        CommandStack extCommandStack = initSmallStack();

        // default index is at earliest command - no change if user tries to go later
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()),
                CommandGetterResult.ofEmpty());
        // go to the earliest command - more times than necessary
        for (int i = 0; i < 10; i++) {
            extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty());
        }
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR1);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()), CGR2);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()), CGR3);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()),
                CommandGetterResult.ofEmpty());
    }
    @Test
    public void commandGetters_smallStack_interweaveValuesAsExpected() {
        CommandStack extCommandStack = initSmallStack();
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR3);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR2);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR1);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()), CGR2);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()), CGR3);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR2);
        assertEquals(extCommandStack.getLaterCommandGetterResult(CommandGetterResult.ofEmpty()), CGR3);
    }
    @Test
    public void commandGetters_smallStackWithModifiedUserInput_valuesAsExpected() {
        CommandStack extCommandStack = initSmallStack();
        CommandGetterResult currCgr = CommandGetterResult.ofEmpty();
        // ================== SCENARIO 1 - WEAVING ARROW KEYS AND USER INPUT ===============================
        // user presses arrow key up
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        // user edits text
        currCgr = currCgr.updateStringToDisplay(C3 + "3403294").updateIsModified(true);
        // user presses arrow key down - no change
        currCgr = extCommandStack.getLaterCommandGetterResult(currCgr);
        assertEquals(currCgr, new CommandGetterResult(C3 + "3403294", true));
        // user presses arrow key up - goes back to C3
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        assertEquals(currCgr, CGR3);
        // user presses arrow key up twice
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        assertEquals(currCgr, CGR1);
        // user edits text
        currCgr = currCgr.updateStringToDisplay(C1 + "23").updateIsModified(true);
        // user presses arrow key down - no change
        currCgr = extCommandStack.getLaterCommandGetterResult(currCgr);
        assertEquals(currCgr, new CommandGetterResult(C1 + "23", true));
        // user presses arrow key up - goes back to C3
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        assertEquals(currCgr, CGR3);
        // =============================================== END OF SCENARIO 1 ================================
        extCommandStack = initSmallStack();
        currCgr = CommandGetterResult.ofEmpty();
        // ================== SCENARIO 2 - WEAVING ARROW KEYS, USER INPUT AND ADD COMMAND ====================
        // user presses arrow key up
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        // user edits text
        currCgr = currCgr.updateStringToDisplay(C3 + "3403294").updateIsModified(true);
        // user adds command
        extCommandStack = extCommandStack.addCommand(C3 + "3403294");
        currCgr = CommandGetterResult.ofEmpty();
        // user presses arrow key up
        currCgr = extCommandStack.getEarlierCommandGetterResult(currCgr);
        assertEquals(currCgr, new CommandGetterResult(C3 + "3403294", false));
        // user presses arrow key down
        currCgr = extCommandStack.getLaterCommandGetterResult(currCgr);
        assertEquals(currCgr, CommandGetterResult.ofEmpty());
        // =============================================== END OF SCENARIO 2 ================================
    }
    @Test
    public void addCommand_smallStack_behaviourAsExpected() {
        CommandStack extCommandStack = initSmallStack();
        CommandStack newCommandStack = extCommandStack.addCommand(C4);
        assertEquals(newCommandStack.getCommandArrayList().get(3), C4);
        // test that new command stack resets internal index
        assertEquals(newCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR4);
        newCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty());
        newCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty());
        newCommandStack = newCommandStack.addCommand(C5);
        assertEquals(newCommandStack.getCommandArrayList().get(4), C5);
        assertEquals(newCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR5);
    }
    @Test
    public void addCommand_fullSmallStack_behaviourAsExpected() {
        CommandStack extCommandStack = initFullSmallStack();
        extCommandStack = extCommandStack.addCommand(C4);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CommandGetterResult.ofEmpty()), CGR4);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CGR4), CGR3);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CGR3), CGR2);
        assertEquals(extCommandStack.getEarlierCommandGetterResult(CGR2), CGR2); // reached the earliest command
    }
    private static CommandStack initSmallStack() {
        ArrayList<String> extArrayList = new ArrayList<>();
        extArrayList.add(C1);
        extArrayList.add(C2);
        extArrayList.add(C3);
        return new CommandStack(extArrayList);
    }
    private static CommandStack initFullSmallStack() {
        ArrayList<String> extArrayList = new ArrayList<>();
        extArrayList.add(C1);
        extArrayList.add(C2);
        extArrayList.add(C3);
        return new CommandStack(extArrayList, 3);
    }

}
