<img src="images/ArchitectureDiagram.png" width="100%" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

> **Disclaimer:** Our team has decided to add the `spleetwaise.commons` package as a common package for classes 
> that are used by multiple components, in our case, `address` and `transaction`. This is an enhancement for 
> modularity on top of the original design of the AddressBook-Level3 project, which only have a `seedu.address` 
> package. The refactoring is almost 90% complete, and we are working on the remaining 10%.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

The app consists of three packages: `address`, `transactions`, and `common`.

- `common` is where the main application logic lives. It contains general classes that are used by multiple components in the app.
- `address` contains classes related to address book.
- `transactions` contains classes related to transactions.

<div markdown="span" class="alert alert-info">:information_source:
**Package structure:**
Packages follow this general package structure: <br>
- **`logic`**: Contains classes related to commands/command parsing.<br>
- **`model`**: Contains classes for representing data in the app.<br>
- **`storage`**: Contains classes related to reading and writing data from, and to storage.<br>
- **`ui`**: Contains classes related to the GUI of the app.<br>
</div>

**Main application**

The entry-point to the app, **`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/commons/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/commons/MainApp.java)), lives in the `common` package, and is in charge of managing the app's lifecycle.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`Ui`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.
