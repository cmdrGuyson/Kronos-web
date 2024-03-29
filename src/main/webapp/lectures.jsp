<%@ page import="com.guyson.kronos.dto.LectureDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Kronos</title>
    <%@ include file="util/head_imports.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/index.css" />
    <link rel="icon" href="images/timetable.png" />
</head>

<body>
<!--Navigation Bar-->
<jsp:include page="util/navbar.jsp" >
    <jsp:param name="page" value="lectures" />
</jsp:include>

<!--Content-->
<sec:authorize access="hasRole('STUDENT')">
    <jsp:include page="util/carousel.jsp" >
        <jsp:param name="page" value="My Lectures" />
    </jsp:include>
</sec:authorize>
<sec:authorize access="hasRole('ACADEMIC_ADMIN')">
    <jsp:include page="util/carousel.jsp" >
        <jsp:param name="page" value="Manage Lectures" />
    </jsp:include>
</sec:authorize>


<div class="container container-home content">

    <%@ include file="util/error_alert.jsp" %>
    <%@ include file="util/success_alert.jsp" %>

    <div class="card card-filter">
        <h4 class="filter-title card-header">Filter Lectures</h4>
        <div class="input-group mb-3">
            <form class="filter-form" method="POST" action="/filter-lectures">
                <div class="row filter-row">
                    <div class="col-5">
                        <input type="date" name="date" class="filter-option select-filter" value="${inputDate}" required/>
                    </div>
                    <div class="col-5">
                        <select class="custom-select form-control filter-option" id="filter" name="order">
                            <option value="time" selected>Sort by Time</option>
                            <option value="module" >Sort by Module</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-outline-primary submit-filter filter-option" style="width: 100px;">
                            <i class="fas fa-filter btn-icon"></i>Filter
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="card recent-students lectures-card">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">Lectures for ${day}</h4>
            <sec:authorize access="hasRole('ACADEMIC_ADMIN')">
                <a type="button" class="btn btn-outline-info btn-in-add" data-toggle="modal" data-target="#addModal"><i class="fas fa-plus-circle btn-icon"></i>Add Lecture</a>
            </sec:authorize>
        </div>
        <hr class="table-hr"/>

        <%
            //If no lectures are present
            List<LectureDto> lect = new ArrayList<>();
            try { lect = (List<LectureDto>) request.getAttribute("lectures");}
            catch(Exception e){e.printStackTrace();}

            if (lect != null && lect.size() <= 0) {
        %>
            <div class="alert alert-secondary" role="alert">
               No lectures available for this day!
            </div>
        <%

            } else {
        %>

        <table id="example" class="table table-striped table-bordered recent-students-table" style="width:100%">
            <thead>
            <tr>
                <sec:authorize access="hasRole('ACADEMIC_ADMIN')">
                    <th>ID</th>
                </sec:authorize>
                <th>Module</th>
                <th>Start Time</th>
                <th>Duration</th>
                <th>Room</th>
                <th>Lecturer</th>
                <sec:authorize access="hasRole('ACADEMIC_ADMIN')">
                    <th>Action</th>
                </sec:authorize>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="lecture" items="${lectures}">
                <c:url value = "#" var = "url">
                    <c:param name = "lectureID" value = "${lecture.getLectureID()}"/>
                </c:url>
                <tr>

                    <sec:authorize access="hasRole('ACADEMIC_ADMIN')">
                        <td>${lecture.getLectureID()}</td>
                    </sec:authorize>
                    <td>${lecture.getModule().getName()}</td>
                    <td>${lecture.getStartTime()}</td>
                    <td>${lecture.getDuration()} ${lecture.getDuration() == "1" ? "hour" : "hours"}</td>
                    <td>${lecture.getRoom().getType()}-${lecture.getRoom().getRoomID()}</td>
                    <td>${lecture.getModule().getLecturer().getFirstName()} ${lecture.getModule().getLecturer().getLastName()}</td>
                    <sec:authorize access="hasRole('ACADEMIC_ADMIN')">
                        <td class="action-td">
                            <div class="btn-group" role="group" aria-label="Basic example">
                                <a type="button" title="Edit lecture" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#editModal${lecture.getLectureID()}">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a type="button" title="Delete lecture" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#deleteLectureModal" onclick="change(${lecture.getLectureID()})">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </div>
                        </td>
                    </sec:authorize>
                </tr>
                <%@ include file="modals/edit_lecture.jsp" %>
            </c:forEach>
            </tbody>
        </table>

        <% } %>
    </div>
</div>

<%@ include file="modals/add_lecture.jsp" %>
<%@ include file="modals/delete_lecture.jsp" %>
<%@ include file="util/footer.jsp" %>
<%@ include file="util/script_imports.jsp" %>
<script>
    //Convert table to JQuery data table
    $(document).ready(function() {
        $('#example').DataTable({
            "aaSorting": [],
            "bSort": false
        });
    } );
</script>
<script>
    //Script used to change the ID hidden input field inside the confirm delete modal
    function change(value) {

        document.getElementById("deleteIDInput").value = value;
        console.log(document.getElementById("deleteIDInput").value);
    }

</script>
</body>

</html>