<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="#">
            <img src="../images/timetable.png" width="40" height="40" alt="" />
        </a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link ${param.page == "home" ? "active" : null}" href="#">Home</a>
                <a class="nav-item nav-link" href="#">Lectures</a>
                <a class="nav-item nav-link" href="#">Students</a>
                <a class="nav-item nav-link" href="#">Lecturers</a>
                <a class="nav-item nav-link" href="#">Modules</a>
                <a class="nav-item nav-link" href="#">Classes</a>
                <a class="nav-item nav-link" href="#">Rooms</a>
            </div>
        </div>
        <a class="btn btn-outline-danger my-2 my-sm-0 logout-btn" href="/logout">
            Logout
        </a>
    </div>
</nav>