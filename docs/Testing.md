---
layout: page
title: Testing guide
---

## Table of Contents

* Table of Contents
{:toc}

## Running tests

There are two ways to run tests.

* **Method 1: Using IntelliJ JUnit test runner**
  * To run all tests, right-click on the `src/test/java` folder and choose `Run 'All Tests'`
  * To run a subset of tests, you can right-click on a test package,
    test class, or a test and choose `Run 'ABC'`
* **Method 2: Using Gradle**
  * Open a console and run the command `gradlew clean test` (Mac/Linux: `./gradlew clean test`)

{: .alert .alert-secondary}
:link: **Link**: Read [this Gradle Tutorial from the se-edu/guides](https://se-education.org/guides/tutorials/gradle.html) to learn more about using Gradle.

[Back to Table of Contents](#table-of-contents)
## Types of tests

This project has three types of tests:

1. *Unit tests* targeting the lowest level methods/classes.  
   e.g. `seedu.address.commons.StringUtilTest`
1. *Integration tests* that are checking the integration of multiple code units (those code units are assumed to be working).  
   e.g. `seedu.address.storage.StorageManagerTest`
1. Hybrids of unit and integration tests. These test are checking multiple code units as well as how the are connected together.    
   e.g. `seedu.address.logic.LogicManagerTest`
   
[Back to Table of Contents](#table-of-contents)
## Known Quirks

When running unit test for command parsers, testing for blank preamble requires the _DESC_ to start with a " ", else the first command gets swallowed into the preamble

Example:

In `DeleteApptCommandParser`, the **unit test** for testing blank preamble fails with:

```
"@d/2024-12-12 @t/1234-1400 i/S1234567i" 
```
This will set the preamble to "@d/2024-12-12" and then an invalid command is thrown because there is no @d/ value.

Instead, this works:
```
" @d/2024-12-12 @t/1234-1400 i/S1234567i" 
 ^ note extra preceding space
```

——
In normal application flow, this does not raise any issues since the `ArgumentTokenizer` immediately chops at the end of the command word, which includes the space right after the command word. Therefore, this is not considered a bug, and is intended.

```
 command  arg-string sent to ArgumentTokenizer
 |-----||--------------------------------------|
"delAppt @d/2024-12-12 @t/1234-1400 i/S1234567i"
        ^
        note: space is included in raw argument string
```

[Back to Table of Contents](#table-of-contents)
