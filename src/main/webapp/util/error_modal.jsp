<%@ page import="com.guyson.kronos.exception.APIException" %><%
    APIException error = null;
    try {
        error = (APIException) request.getAttribute("errorSetting");
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (error != null) {
%>

<div class="alert alert-danger alert-dismissible fade show alert-message" role="alert">
    <%= error.getMessage() %>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<% } %>