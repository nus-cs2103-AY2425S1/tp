---
  layout: default.md
  title: "Documentation guide"
  pageNav: 3
---

# Documentation Guide

* We use [**MarkBind**](https://markbind.org/) to manage documentation.
* The `docs/` folder contains the source files for the documentation website.
* To learn how set it up and maintain the project website, follow the guide [[se-edu/guides] Working with Forked MarkBind sites](https://se-education.org/guides/tutorials/markbind-forked-sites.html) for project documentation.

**Style guidance:**

* Follow the [**_Google developer documentation style guide_**](https://developers.google.com/style).
* Also relevant is the [_se-edu/guides **Markdown coding standard**_](https://se-education.org/guides/conventions/markdown.html).


**Converting to PDF**

* See the guide [_se-edu/guides **Saving web documents as PDF files**_](https://se-education.org/guides/tutorials/savingPdf.html).

## Adding New Sections

* To add a new section to the documentation, create a new Markdown file in the `docs/` folder.
* Update the `_markbind/navigation.md` file to include a link to the new section.
* Follow the existing style and structure guidelines to ensure consistency.

## Updating Existing Sections

* To update an existing section, edit the corresponding Markdown file in the `docs/` folder.
* Ensure that any changes adhere to the style and structure guidelines.
* Test the changes locally using MarkBind to ensure they render correctly.

## Testing Documentation Changes

* Use the `markbind serve` command to preview changes locally.
* Ensure that all links and images are working correctly.
* Check for any formatting issues and resolve them before committing changes.

## Submitting Documentation Changes

* Commit your changes to a new branch.
* Create a pull request for review.
* Ensure that the pull request description includes a summary of the changes and any relevant context.
