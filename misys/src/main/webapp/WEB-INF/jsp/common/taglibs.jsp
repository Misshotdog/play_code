<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
 <%@taglib prefix= "feinno-frame" uri= "http://framework.feinno.com/tags/framework" %>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	request.setAttribute("ctx", basePath);
%>
