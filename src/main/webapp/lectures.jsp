<%@ page import="com.guyson.kronos.dto.LectureDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="util/navbar_admin.jsp" >
    <jsp:param name="page" value="lectures" />
</jsp:include>

<!--Content-->
<jsp:include page="util/carousel.jsp" >
    <jsp:param name="page" value="Manage Lectures" />
</jsp:include>


<div class="container container-home content">

    <%@ include file="util/error_alert.jsp" %>

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
            <a type="button" class="btn btn-outline-info btn-in-add" data-toggle="modal" data-target="#addModal"><i class="fas fa-plus-circle btn-icon"></i>Add Lecture</a>
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
                <th>ID</th>
                <th>Module</th>
                <th>Start Time</th>
                <th>Duration</th>
                <th>Room</th>
                <th>Lecturer</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="lecture" items="${lectures}">
                <c:url value = "#" var = "url">
                    <c:param name = "lectureID" value = "${lecture.getLectureID()}"/>
                </c:url>
                <tr>

                    <td>${lecture.getLectureID()}</td>
                    <td>${lecture.getModule().getName()}</td>
                    <td>${lecture.getStartTime()}</td>
                    <td>${lecture.getDuration()} hours</td>
                    <td>${lecture.getRoom().getType()}-${lecture.getRoom().getRoomID()}</td>
                    <td>${lecture.getModule().getLecturer().getFirstName()} ${lecture.getModule().getLecturer().getLastName()}</td>
                    <td class="action-td">
                        <div class="btn-group" role="group" aria-label="Basic example">
                            <a type="button" title="Edit lecture" class="btn btn-outline-secondary btn-delete" >
                                <i class="fas fa-edit"></i>
                            </a>
                            <a type="button" title="Delete lecture" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#deleteLectureModal">
                                <i class="fas fa-trash-alt"></i>
                            </a>
                        </div>
                    </td>
                </tr>
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
</body>

</html>