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
  * [1. Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#1-acknowledgements)
  * [2. Setting Up]({{ baseUrl }}/DeveloperGuide.html#2-setting-up-getting-started)
  * [3. Design]({{ baseUrl }}/DeveloperGuide.html#3-design)
    * [3.1 Architecture]({{ baseUrl }}/UserGuide.html#3-1-architecture)
    * [3.2 UI Component]({{ baseUrl }}/UserGuide.html#3-2-ui-component)
    * [3.3 Logic Component]({{ baseUrl }}/UserGuide.html#3-3-logic-component)
    * [3.4 Model Component]({{ baseUrl }}/UserGuide.html#3-4-model-component)
    * [3.5 Storage Component]({{ baseUrl }}/UserGuide.html#3-5-storage-component)
    * [3.6 Common Classes]({{ baseUrl }}/UserGuide.html#3-6-common-classes)
  * [4. Implementation]({{ baseUrl }}/DeveloperGuide.html#4-implementation)
    * [4.1 Lesson Time Parameter]({{ baseUrl }}/UserGuide.html#4-1-lesson-time-parameter)
    * [4.2 Add Feature]({{ baseUrl }}/UserGuide.html#4-2-add-feature)
    * [4.3 Delete Feature]({{ baseUrl }}/UserGuide.html#4-3-delete-feature)
    * [4.4 Tag Feature]({{ baseUrl }}/UserGuide.html#4-4-tag-feature)
    * [4.5 View Specific Student Feature]({{ baseUrl }}/UserGuide.html#4-5-view-specific-student-feature)
    * [4.6 Add Task Feature]({{ baseUrl }}/UserGuide.html#4-6-add-task-feature)
  * [5. Documentation, Logging, Testing, Configuration, Dev-Ops]({{ baseUrl }}/DeveloperGuide.html#5-documentation-logging-testing-configuration-dev-ops)
  * [6. Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#6-appendix-requirements)
    * [6.1 Product Scope]({{ baseUrl }}/UserGuide.html#6-1-product-scope)
    * [6.2 User Stories]({{ baseUrl }}/UserGuide.html#6-2-user-stories)
    * [6.3 Use Cases]({{ baseUrl }}/UserGuide.html#6-3-use-cases)
    * [6.4 Non-Functional Requirements]({{ baseUrl }}/UserGuide.html#6-4-non-functional-requirements)
    * [6.5 Glossary]({{ baseUrl }}/UserGuide.html#6-5-glossary)
  * [7. Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#7-appendix-instructions-for-manual-testing)
    * [7.1 Launch and Shutdown]({{ baseUrl }}/UserGuide.html#7-1-launch-and-shutdown)
    * [7.2 Adding a Student]({{ baseUrl }}/UserGuide.html#7-2-adding-a-student)
    * [7.3 Deleting a Student]({{ baseUrl }}/UserGuide.html#7-3-deleting-a-student)
    * [7.4 Finding Specific Students]({{ baseUrl }}/UserGuide.html#7-4-finding-specific-students)
    * [7.5 Adding a Task to a Student]({{ baseUrl }}/UserGuide.html#7-5-adding-a-task-to-a-student)
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
