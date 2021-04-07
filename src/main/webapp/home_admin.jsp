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
<jsp:include page="util/navbar.jsp" >
    <jsp:param name="page" value="home" />
</jsp:include>

<!--Content-->
<%@ include file="util/carousel.jsp" %>

<div class="container container-home content">
    <div class="text-center">
        <h2 class="welcome-title">Welcome Gayanga!</h2>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Ullamcorper morbi tincidunt ornare massa eget egestas. Elementum pulvinar etiam non quam lacus suspendisse faucibus interdum.
            Quis viverra nibh cras pulvinar mattis nunc sed blandit libero. Pulvinar sapien et ligula ullamcorper. Tellus elementum sagittis vitae et leo duis.
            Duis at tellus at urna condimentum mattis pellentesque.
        </p>
    </div>

    <div class="card recent-students">
        <h4 class="recent-students-title">Recently joined students</h4>
        <table id="example" class="table table-striped table-bordered" style="width:100%">
            <thead>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Class</th>
                <th>Joined On</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${recent_students}">
                <tr>
                    <td>${student.getUsername()}</td>
                    <td>${student.getFirstName()}</td>
                    <td>${student.getLastName()}</td>
                    <td>${student.get_class().getType()}-${student.get_class().getClassID()}</td>
                    <td>${student.getJoinedOn()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>



<%@ include file="util/footer.jsp" %>
<%@ include file="util/script_imports.jsp" %>
<script>
    //Convert table to JQuery data table
    $(document).ready(function() {
        $('#example').DataTable({
            "aaSorting": []
        });
    } );
</script>
</body>

</html>