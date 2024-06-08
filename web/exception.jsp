<%
    Exception e = (Exception) request.getAttribute("exception");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "header.html" %>
<div class="align-items-center h-100 d-flex w-50 mx-auto">
    <div class="mx-auto text-center">
        <h1 class="display-1 m-0 font-weight-bolder text-muted" style="font-size:80px;">Exception</h1>
        <h1 class="mb-3"><%= e.getMessage() %></h1>
    </div>
</div>
<%@ include file = "footer.html" %>
