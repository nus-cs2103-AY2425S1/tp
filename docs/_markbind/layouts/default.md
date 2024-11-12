<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">data coNdUctorS</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2425S1-CS2103-F09-1/tp" target="_blank" class="nav-link"><md>:fab-github:</md></a>
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
  * [Quick Start]({{ baseUrl }}/UserGuide.html#quick-start)
  * [Feature Details]({{ baseUrl }}/UserGuide.html#feature-details)
    * [General Notes about Command Format]({{ baseUrl }}/UserGuide.html#general-notes-about-command-format)
    * [Adding a Contact: add]({{ baseUrl }}/UserGuide.html#adding-a-contact-add)
    * [Editing a Contact: edit]({{ baseUrl }}/UserGuide.html#editing-a-contact-edit)
    * [Deleting a Contact: delete]({{ baseUrl }}/UserGuide.html#deleting-a-contact-delete)
    * [Finding Contacts by Contact Details: find]({{ baseUrl }}/UserGuide.html#finding-contacts-by-contact-details-find)
    * [Listing all Contacts: list]({{ baseUrl }}/UserGuide.html#listing-all-contacts-list)
    * [Viewing Help: help]({{ baseUrl }}/UserGuide.html#viewing-help-help)
    * [Clearing all Entries: clear]({{ baseUrl }}/UserGuide.html#clearing-all-entries-clear)
    * [Exiting the Program: exit]({{ baseUrl }}/UserGuide.html#exiting-the-program-exit)
    * [Displaying Contacts in Pages -- Pagination]({{ baseUrl }}/UserGuide.html#displaying-contacts-in-pages----pagination)
    * [Showing Total Number of Contacts -- Footer Status Bar]({{ baseUrl }}/UserGuide.html#showing-total-number-of-contacts----footer-status-bar)
    * [Saving the Data]({{ baseUrl }}/UserGuide.html#saving-the-data)
    * [Editing the Data File]({{ baseUrl }}/UserGuide.html#editing-the-data-file)
  * [What is Considered as Invalid Contacts]({{ baseUrl }}/UserGuide.html#what-is-considered-as-invalid-contacts)
  * [Contact Fields Constraints]({{ baseUrl }}/UserGuide.html#contact-fields-constraints)
  * [FAQ]({{ baseUrl }}/UserGuide.html#faq)
  * [Known Issues]({{ baseUrl }}/UserGuide.html#known-issues)
    
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, Logging, Testing, Configuration, Dev-Ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for Manual Testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
  * [Appendix: Planned Enhancements]({{ baseUrl }}/DeveloperGuide.html#appendix-planned-enhancements)
  * [Appendix: Effort]({{ baseUrl }}/DeveloperGuide.html#appendix-effort)
    
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
