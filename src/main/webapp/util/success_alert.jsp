<%@ page import="com.guyson.kronos.dto.SimpleMessageDto" %><%
    SimpleMessageDto success = null;
    try {
        success = (SimpleMessageDto) request.getAttribute("success");
    } catch (Exception e) {
        e.printStackTrace();
    }

    if (success != null) {
%>

<div class="alert alert-primary alert-dismissible fade show alert-message" role="alert">
    <%= success.getMessage() %>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<% } %>