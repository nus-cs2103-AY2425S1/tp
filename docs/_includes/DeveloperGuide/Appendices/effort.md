### Difficulty Level

Overall, compared to iP, if iP was considered a 10/20, then this project would be a 17/20.

- **2 points** for ensuring comprehensive User Guide (UG) and Developer Guide (DG) documentation (much more effort needed in documentation than iP). We invested considerable effort in making the documentation thorough, offering clear explanations of each feature, use case, and design decision.
- **2 points** for extensive code refactoring in a large and complex codebase to enhance developer experience, modularity, and maintainability. We added approximately 17000 lines of code, carefully restructuring the codebase to introduce reusable components. This refactoring simplified development in the mid-to-late stages and will facilitate easier future development and debugging.
- **2 points** for designing and implementing thorough equality checks, validation logic, etc. Much more time was required for validation of fields like names and other inputs. Implementing and testing these validations to ensure robustness across different input types demanded careful handling of edge cases and error conditions. This was vastly different from iP where there were not many validation checks required.
- **1 point** for collaborative development and version control management. Coordinating among team members in a large codebase posed additional difficulties in managing merge conflicts and synchronizing code changes. We employed best practices in Git workflows, including pull requests, reviews, and conflict resolution, which required strong teamwork and communication.

### Challenges Faced

1. **GUI Testing**:
   The prominent challenge faced was trying to get UI testing to work using Monocle, as Monocle support ended at JDK 12. We spent a couple of days diagnosing why the headless testing environment on GitHub Actions kept failing.

2. **Refactoring**:
   Refactoring the codebase to make it more modular and easier to maintain was a challenge as it required a lot of effort and time, especially when the majority of the AB3 codebase was tightly coupled.

3. **Working with a Large Codebase**:
   The large codebase made it difficult to understand and make changes without breaking other parts of the code. Reusable components were not well-defined, and the codebase was not modular.

### Effort Required

1. We spent significant effort refactoring the codebase to make it more modular and easier to maintain.

2. We spent a lot of time adhering to defensive coding practices to ensure that the codebase was robust and less prone to bugs, even going to the extent of writing tests for the GUI, even though it was optional.

### Achievements of the Project

1. **Successful Feature Implementation**: We delivered nearly all planned features for the MVP, including the core functionalities of managing transactions such as adding, editing, and deleting transactions. Filtering and searching transactions were also implemented, which were not planned initially.

2. **Comprehensive Documentation**: Our UG and DG provide detailed information for users and developers, supporting long-term use and maintenance.

3. **Modular Codebase with Thorough Test Cases**: Through extensive refactoring, we transformed the codebase into a more modular structure, making future enhancements easier to manage and reducing technical debt. Our code coverage is above 90% for the core functionalities.
