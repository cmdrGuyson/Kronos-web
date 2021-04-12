<%@ page import="com.guyson.kronos.dto.StudentModuleDto" %>
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
    <jsp:param name="page" value="${myModules == null ? 'modules' : 'my-modules'}" />
</jsp:include>

<!--Content-->
<sec:authorize access="hasRole('STUDENT')">
    <jsp:include page="util/carousel.jsp" >
        <jsp:param name="page" value="${myModules == null ? 'Manage Enrollments' : 'My Modules'}" />
    </jsp:include>
</sec:authorize>
<sec:authorize access="hasRole('ADMIN')">
    <jsp:include page="util/carousel.jsp" >
        <jsp:param name="page" value="Manage Modules" />
    </jsp:include>
</sec:authorize>



<div class="container container-home content">

    <%@ include file="util/error_alert.jsp" %>
    <%@ include file="util/success_alert.jsp" %>

    <div class="card recent-students card-filter">
        <div class="title-add">
            <h4 class="recent-students-title title-in-add">${myModules == null ? 'All Modules' : 'My Modules'}</h4>
            <sec:authorize access="hasRole('ADMIN')">
                <a type="button" class="btn btn-outline-info btn-in-add" data-toggle="modal" data-target="#addModal"><i class="fas fa-plus-circle btn-icon"></i>Add Module</a>
            </sec:authorize>
        </div>
        <hr class="table-hr"/>
        <table id="example" class="table table-striped table-bordered recent-students-table" style="width:100%">
            <thead>
            <tr>
                <th>Module ID</th>
                <th>Name</th>
                <th>Credits</th>
                <th>Description</th>
                <th>Lecturer</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="module" items="${modules}">
                <c:url value = "#" var = "url">
                    <c:param name = "moduleID" value = "${module.getModuleID()}"/>
                </c:url>
                <tr>

                    <td>${module.getModuleID()}</td>
                    <td>${module.getName()}</td>
                    <td>${module.getCredits()}</td>
                    <td>${module.getDescription()}</td>
                    <td>${module.getLecturer().getFirstName()} ${module.getLecturer().getLastName()}</td>

                        <sec:authorize access="hasRole('ADMIN')">
                            <td class="action-td">
                                <a type="button" title="Delete module" class="btn btn-outline-secondary btn-delete" data-toggle="modal" data-target="#deleteModuleModal" onclick="change(${module.getModuleID()})">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </td>
                        </sec:authorize>
                        <sec:authorize access="hasRole('STUDENT')">
                            <% StudentModuleDto studentModuleDto = (StudentModuleDto) pageContext.getAttribute("module");
                                if (studentModuleDto.isEnrolled()) {
                            %>
                            <td class="action-td" style="width: 12% !important;">

                                <form method="POST" action="/unroll">
                                    <input hidden value="${module.getModuleID()}" name="moduleID" />
                                    <button type="submit" title="Unroll from module" class="btn btn-outline-danger enrol-btn">
                                        <i class="fas fa-minus-circle enrol-icon"></i>Unroll
                                    </button>
                                </form>

                            </td>
                            <% } else { %>
                            <td class="action-td" style="width: 12% !important;">
                                <form method="POST" action="/enroll">
                                    <input hidden value="${module.getModuleID()}" name="moduleID" />
                                    <button type="submit" title="Enroll in module" class="btn btn-outline-primary enrol-btn">
                                        <i class="fas fa-plus-circle enrol-icon"></i>Enroll
                                    </button>
                                </form>
                            </td>
                            <% } %>
                        </sec:authorize>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="modals/add_module.jsp" %>
<%@ include file="modals/delete_module.jsp" %>
<%@ include file="util/footer.jsp" %>
<%@ include file="util/script_imports.jsp" %>
<script>
    //Convert table to JQuery data table
    $(document).ready(function() {
        $('#example').DataTable({
            "order": [[ 0, "asc" ]],
            //"bSort": false
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