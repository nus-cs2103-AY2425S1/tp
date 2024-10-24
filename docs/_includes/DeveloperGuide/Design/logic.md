**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

#### Dual-parser setup
How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it will first check whether the command is known by the `AddressBookParser`:

   1.1. **It is first passed to an `AddressBookParser` object which will attempt to parse it.** If there is a matching command, it creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.

   1.2. **If there is no matching command, `Logic` will try to parse it with a  `TransactionBookParser`.** If  there is a matching command, a similar thing happens as with `AddressBookParser`. The partial sequence diagram below illustrates this case clearly:

   ![Partial sequence diagram for transaction command](images/LogicSequenceDiagram.png)

2. This ultimately results in a non-null `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.

3. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>

   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.

4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` or `TransactionBookParser` class creates a respective `AbCommandParser` (`Ab` is a placeholder for some specific address book command name e.g., `AddCommandParser`), which uses the other classes shown above to parse the user command and create a `AbCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object. Likewise for `TransactionBookParser` and `TbCommand`s.

- All `AbCommandParser` and `TbCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from a common `Parser` interface so that they can be treated similarly where possible e.g, during testing.
