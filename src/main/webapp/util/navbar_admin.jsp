<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="/">
            <img src="../images/timetable.png" width="40" height="40" alt="" />
        </a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link ${param.page == "home" ? "active" : null}" href="/">Home</a>
                <a class="nav-item nav-link ${param.page == "lectures" ? "active" : null}" href="/lectures">Lectures</a>
                <a class="nav-item nav-link ${param.page == "students" ? "active" : null}" href="/students">Students</a>
                <a class="nav-item nav-link ${param.page == "lecturers" ? "active" : null}" href="/lecturers">Lecturers</a>
                <a class="nav-item nav-link ${param.page == "modules" ? "active" : null}" href="/modules">Modules</a>
                <a class="nav-item nav-link ${param.page == "classes" ? "active" : null}" href="/classes">Classes</a>
                <a class="nav-item nav-link ${param.page == "rooms" ? "active" : null}" href="/rooms">Rooms</a>
            </div>
        </div>
        <a class="btn btn-outline-secondary my-2 my-sm-0 logout-btn settings-btn">Settings</a>
        <a class="btn btn-outline-danger my-2 my-sm-0 logout-btn" href="/logout">
            Logout
        </a>
    </div>
</nav>