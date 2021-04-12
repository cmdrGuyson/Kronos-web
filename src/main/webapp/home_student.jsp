<%@ page import="com.guyson.kronos.dto.LectureDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <jsp:param name="page" value="home" />
</jsp:include>

<!--Content-->
<jsp:include page="util/carousel.jsp" >
    <jsp:param name="page" value="Kronos" />
</jsp:include>

<div class="container container-home content">
    <div class="text-center">
        <h2 class="welcome-title">Welcome ${name}!</h2>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Ullamcorper morbi tincidunt ornare massa eget egestas. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum.
            Quis viverra nibh cras pulvinar mattis nunc sed blandit libero. Pulvinar sapien et ligula ullamcorper. Tellus elementum sagittis vitae et leo duis.
            Duis at tellus at urna condimentum mattis pellentesque.
        </p>
    </div>

    <div class="card recent-students">
        <h4 class="recent-students-title">Today's Lectures</h4>
        <hr class="table-hr">

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
                <th>Module</th>
                <th>Start Time</th>
                <th>Duration</th>
                <th>Room</th>
                <th>Lecturer</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="lecture" items="${lectures}">
                <tr>
                    <td>${lecture.getModule().getName()}</td>
                    <td>${lecture.getStartTime()}</td>
                    <td>${lecture.getDuration()} ${lecture.getDuration() == "1" ? "hour" : "hours"}</td>
                    <td>${lecture.getRoom().getType()}-${lecture.getRoom().getRoomID()}</td>
                    <td>${lecture.getModule().getLecturer().getFirstName()} ${lecture.getModule().getLecturer().getLastName()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <% } %>
    </div>
</div>



<%@ include file="util/footer.jsp" %>
<%@ include file="util/script_imports.jsp" %>
<script>
    //Convert table to JQuery data table
    $(document).ready(function() {
        $('#example').DataTable({
            "aaSorting": [],
            "bLengthChange": false,
            "bSort": false
        });
    } );
</script>
</body>

</html>