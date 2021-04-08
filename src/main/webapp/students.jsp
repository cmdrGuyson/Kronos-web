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
    <jsp:param name="page" value="students" />
</jsp:include>

<!--Content-->
<jsp:include page="util/carousel.jsp" >
    <jsp:param name="page" value="Manage Students" />
</jsp:include>


<div class="container container-home content">

    <div class="card recent-students">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">All Students</h4>
            <button type="button" class="btn btn-outline-info btn-in-add"><i class="fas fa-plus-circle btn-icon"></i>Add Student</button>
        </div>
        <hr class="table-hr"/>
        <table id="example" class="table table-striped table-bordered recent-students-table" style="width:100%">
            <thead>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Class</th>
                <th>Joined On</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${students}">
                <c:url value = "#" var = "url">
                    <c:param name = "username" value = "${student.getUsername()}"/>
                </c:url>
                <tr>

                    <td>${student.getUsername()}</td>
                    <td>${student.getFirstName()}</td>
                    <td>${student.getLastName()}</td>
                    <td>${student.get_class().getType()}-${student.get_class().getClassID()}</td>
                    <td>${student.getJoinedOn()}</td>
                    <td class="action-td">
                        <a type="button" title="Delete student" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#deleteStudentModal">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="modals/delete_student.jsp" %>
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