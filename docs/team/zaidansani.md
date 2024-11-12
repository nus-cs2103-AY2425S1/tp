---
  layout: default.md
  title: "Zaidan's Project Portfolio Page"
---

### Project: DocTrack

**DocTrack** helps general practitioners (GPs) at small clinics quickly access patient contact details, appointments, and treatment records. It is optimized for users who prefer a command-line interface (CLI), enabling faster completion of frequent tasks through typed commands.

---
Role:
- Team Lead
- Developer
- Integration

Given below are my contributions to the project:
- Refactoring Person into PersonDescriptor to support PersonID
- Refactoring of `DeleteCommand` to support new command format
- Refactoring of `ClearCommand` to support new command format
- Refactoring of models to support running count for implementation of IDs
- Updated test cases in the above to work
- Adding of `FindAppointmentCommand`
  - Created new predicates
  - Find by date and person name
- Autosorting of listed appointments by date
- Autosorting of listed persons by name
