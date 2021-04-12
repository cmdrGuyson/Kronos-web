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
    <jsp:param name="page" value="classes" />
</jsp:include>

<!--Content-->
<jsp:include page="util/carousel.jsp" >
    <jsp:param name="page" value="Manage Classes" />
</jsp:include>


<div class="container container-home content">

    <%@ include file="util/error_alert.jsp" %>
    <%@ include file="util/success_alert.jsp" %>

    <div class="card recent-students">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">All Classes</h4>
            <a type="button" class="btn btn-outline-info btn-in-add" data-toggle="modal" data-target="#addModal"><i class="fas fa-plus-circle btn-icon"></i>Add Class</a>
        </div>
        <hr class="table-hr"/>
        <table id="example" class="table table-striped table-bordered recent-students-table" style="width:100%">
            <thead>
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="class" items="${classes}">
                <c:url value = "#" var = "url">
                    <c:param name = "classID" value = "${class.getClassID()}"/>
                </c:url>
                <tr>

                    <td>${class.getClassID()}</td>
                    <td>${class.getType()}</td>
                    <td>${class.getDescription()}</td>
                    <td class="action-td">
                        <a type="button" title="Delete class" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#deleteClassModal" onclick="change(${class.getClassID()})">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="modals/add_class.jsp" %>
<%@ include file="modals/delete_class.jsp" %>
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