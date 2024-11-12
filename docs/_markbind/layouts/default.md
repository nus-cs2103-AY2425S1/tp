<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">EduManage</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/se-edu/addressbook-level3" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [1. Quick Start]({{ baseUrl }}/UserGuide.html#1-quick-start)
  * [2. Command Summary]({{ baseUrl }}/UserGuide.html#2-command-summary)
  * [3. Features]({{ baseUrl }}/UserGuide.html#3-features)
    * [3.1 Student Management]({{ baseUrl }}/UserGuide.html#3-1-student-management)
    * [3.2 Task Management]({{ baseUrl }}/UserGuide.html#3-2-task-management)
    * [3.3 Data Management]({{ baseUrl }}/UserGuide.html#3-3-data-management)
    * [3.4 Navigation]({{ baseUrl }}/UserGuide.html#3-4-navigation)
  * [4. FAQ]({{ baseUrl }}/UserGuide.html#4-faq)
  * [5. Known Issues]({{ baseUrl }}/UserGuide.html#5-known-issues)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [1. Setting Up]({{ baseUrl }}/DeveloperGuide.html#1-setting-up-getting-started)
  * [2. Design]({{ baseUrl }}/DeveloperGuide.html#2-design)
    * [2.1 Architecture]({{ baseUrl }}/DeveloperGuide.html#2-1-architecture)
    * [2.2 UI Component]({{ baseUrl }}/DeveloperGuide.html#2-2-ui-component)
    * [2.3 Logic Component]({{ baseUrl }}/DeveloperGuide.html#2-3-logic-component)
    * [2.4 Model Component]({{ baseUrl }}/DeveloperGuide.html#2-4-model-component)
    * [2.5 Storage Component]({{ baseUrl }}/DeveloperGuide.html#2-5-storage-component)
    * [2.6 Common Classes]({{ baseUrl }}/DeveloperGuide.html#2-6-common-classes)
  * [3. Implementation]({{ baseUrl }}/DeveloperGuide.html#3-implementation)
    * [3.1 Lesson Time Parameter]({{ baseUrl }}/DeveloperGuide.html#3-1-lesson-time-parameter)
    * [3.2 Add Feature]({{ baseUrl }}/DeveloperGuide.html#3-2-add-feature)
    * [3.3 Tag Feature]({{ baseUrl }}/DeveloperGuide.html#3-3-tag-feature)
    * [3.4 View Specific Student Feature]({{ baseUrl }}/DeveloperGuide.html#3-4-view-specific-student-feature)
    * [3.5 Add Task Feature]({{ baseUrl }}/DeveloperGuide.html#3-5-add-task-feature)
  * [4. Documentation, Logging, Testing, Configuration, Dev-Ops]({{ baseUrl }}/DeveloperGuide.html#4-documentation-logging-testing-configuration-dev-ops)
  * [5. Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#5-appendix-requirements)
    * [5.1 Product Scope]({{ baseUrl }}/DeveloperGuide.html#5-1-product-scope)
    * [5.2 User Stories]({{ baseUrl }}/DeveloperGuide.html#5-2-user-stories)
    * [5.3 Use Cases]({{ baseUrl }}/DeveloperGuide.html#5-3-use-cases)
    * [5.4 Non-Functional Requirements]({{ baseUrl }}/DeveloperGuide.html#5-4-non-functional-requirements)
    * [5.5 Glossary]({{ baseUrl }}/DeveloperGuide.html#5-5-glossary)
  * [6. Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#6-appendix-instructions-for-manual-testing)
    * [6.1 Launch and Shutdown]({{ baseUrl }}/DeveloperGuide.html#6-1-launch-and-shutdown)
    * [6.2 Adding a Student]({{ baseUrl }}/DeveloperGuide.html#6-2-adding-a-student)
    * [6.3 Deleting a Student]({{ baseUrl }}/DeveloperGuide.html#6-3-deleting-a-student)
    * [6.4 Finding Specific Students]({{ baseUrl }}/DeveloperGuide.html#6-4-finding-specific-students)
    * [6.5 Adding a Task to a Student]({{ baseUrl }}/DeveloperGuide.html#6-5-adding-a-task-to-a-student)
  * [7. Appendix: Planned Enhancements]({{ baseUrl }}/DeveloperGuide.html#7-appendix-planned-enhancements)
    * [7.1 Cumulative Updates for Subjects and Lesson Times]({{ baseUrl }}/DeveloperGuide.html#7-1-cumulative-updates-for-subjects-and-lesson-times)
    * [7.2 Support for Special Characters in Names]({{ baseUrl }}/DeveloperGuide.html#7-2-support-for-special-characters-in-names)
    * [7.3 Handling Multiple Students with Identical Names]({{ baseUrl }}/DeveloperGuide.html#7-3-handling-multiple-students-with-identical-names)
    * [7.4 Restriction on Using Reserved Prefixes within Parameters]({{ baseUrl }}/DeveloperGuide.html#7-4-restriction-on-using-reserved-prefixes-within-parameters)
    * [7.5 Remove Note Display if Blank]({{ baseUrl }}/DeveloperGuide.html#7-5-remove-note-display-if-blank)
    * [7.6 Autoscale Student List When Manually Adjusting Size]({{ baseUrl }}/DeveloperGuide.html#7-6-autoscale-student-list-when-manually-adjusting-size)
    * [7.7 Restrict Task Deadlines to Dates After the Current Date]({{ baseUrl }}/DeveloperGuide.html#7-7-restrict-task-deadlines-to-dates-after-the-current-date)
    * [7.8 Allow Lesson Times to Span Across Multiple Days]({{ baseUrl }}/DeveloperGuide.html#7-8-allow-lesson-times-to-span-across-multiple-days)
* Tutorials
  * [Tracing code]({{ baseUrl }}/tutorials/TracingCode.html)
  * [Adding a command]({{ baseUrl }}/tutorials/AddRemark.html)
  * [Removing Fields]({{ baseUrl }}/tutorials/RemovingFields.html)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
