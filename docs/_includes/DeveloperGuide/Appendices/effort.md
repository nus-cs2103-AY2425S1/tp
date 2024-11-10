#### Difficulty Level
Compared to iP (rated 10/20), this project rates at 17/20 due to:

- **2 points** for comprehensive UG and DG documentation, providing detailed explanations of each feature, use case, and design decision.
- **2 points** for extensive refactoring in a large codebase. Approximately 17,000 lines of code were added/modified to improve modularity and maintainability through reusable components, simplifying development in later stages.
- **2 points** for implementing complex validation and equality checks. Significant effort was spent on robust validation across various input fields, handling edge cases thoroughly.
- **1 point** for collaborative development and effective Git workflows, including pull requests, reviews, and conflict resolution.

#### Additional Complexity in Transaction Features
Much more effort was spent on implementing transaction features in SpleetWaise compared to the AB3 features.

One example is the `find` command in AB3 vs. `filterTxn` in SpleetWaise. In AB3, `find` only filters by name using a single `NameContainsKeywordsPredicate` class. In SpleetWaise, `filterTxn` filters transactions by multiple parameters (e.g., description, amount, status, person) in any combination. This required a modular `FilterPredicate` system, resulting in seven `*FilterPredicate` classes and a `FilterCommandPredicate` wrapper, each with its own test cases. This approach reduced code complexity and duplication by reusing `FilterPredicate` classes in the UI.

#### Achievements of the Project
1. **Feature Implementation**: Delivered core transaction management features, including filtering and searching.
2. **Comprehensive Documentation**: UG and DG support long-term use and maintenance.
3. **Modular Codebase with High Test Coverage**: Achieved a modular structure with over 90% code coverage for core functionalities, reducing technical debt.
