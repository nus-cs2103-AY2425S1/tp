<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Cedarville+Cursive&family=Parisienne&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>

<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
  <script src="{{baseUrl}}/scripts/script.js"></script>
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand"><span class="wedLinkerStyle">WedLinker</span></a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">

  <!-- Original Left scrollbar -->

  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html)
  * [Quick Start]({{ baseUrl }}/UserGuide.html#quick-start)
  * [Features]({{ baseUrl }}/UserGuide.html#features)
  * [Command Summary]({{ baseUrl }}/UserGuide.html#command-summary)
  * [FAQ]({{ baseUrl }}/UserGuide.html#frequently-asked-questions)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html)
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>





<!-- Main Content -->
  <div id="content-wrapper">
    {{ content }}
  </div>

  <!-- Right scrollbar -->

  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>

  <scroll-top-button></scroll-top-button>
</div>

<footer style="background-color: #333; color: white; text-align: center; padding: 10px 0; font-size: 0.9rem;">
  <div style="display: flex; justify-content: center; align-items: center; gap: 15px;">
    <a href="{{baseUrl}}/AboutUs.html" class="nav-link" style="color: #E83E8C; font-weight: bold; text-decoration: none;">About Us</a>
    <a href="https://github.com/AY2425S1-CS2103T-F15-4/tp" target="_blank" class="nav-link" style="color: #E83E8C; font-weight: bold; text-decoration: none;">
      <i class="fab fa-github"></i>
    </a>
  </div>
</footer>


<div id="imageModal" class="modal">
  <img class="modal-content" id="modalImage">
</div>
