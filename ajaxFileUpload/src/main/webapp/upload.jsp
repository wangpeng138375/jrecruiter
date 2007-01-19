<%@include file="/includes/taglibs.jsp"%>
<!DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Ajax File Upload - Spring MVC</title>
        <link href="<c:url value='/styles/global.css'/>" rel="stylesheet" type="text/css"/>
        
        <script src="<c:url value='/js/upload.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/dwr/interface/UploadMonitor.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/dwr/engine.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/dwr/util.js'/>" type="text/javascript"></script>
	</head>
	<body> 
		<form:form commandName="command" method="post" action="${ctx}/upload.html" enctype="multipart/form-data" onsubmit="startProgress();">    
	        <div id="header">
	            <h1>Ajax File Upload</h1>
	                <span>for Spring MVC</span>
	        </div>
	        <div id="main">         
	            <%-- Success Messages --%>
	            <c:if test="${not empty message}">
	                <div class="message">${message}</div>
	            </c:if>
	
				<form:errors path="*" cssClass="error"/>
	            <c:if test="${empty status.errorMessages}">
	                <div class="error_message" id="errorMessagesDiv" style="display: none;">    
	                </div>
	            </c:if>
	
					    <table id="file-upload">
		                    <tbody>
		                        <tr>
		                            <td><label for="birthday">File</label>:</td>
		                            <td>
		                            	<spring:bind path="command.file">
		                                    <input 	type="file" name="${status.expression}"
			                                        value="${status.value}" id="file1"
			                                        onblur="javascript:this.className=''" 
			                                        onfocus="javascript:this.className='field_selected'"/>
		                                </spring:bind>      
		                                <form:errors path="file" cssClass="fieldError"/>
		                            </td>           
		                        </tr>
		                        <tr>
		                            <td>&nbsp;</td>
		                            <td>
		                            	<p>
		                                Currently the upload maximum is set to <fmt:formatNumber type="number" maxFractionDigits="2"
		                                value="${uploadSizeMax / 1024 / 1024 }" />  Mb.
		                                </p>
		                                <p>
		                                Files are uploaded to the system temp directory. 
		                                </p>
		                            </td>
		                        </tr>
		                    </tbody>
		                    <tfoot>
		                        <tr>
		                            <td>&nbsp;</td>
		                            <td>
		                            <input type="submit" value="Upload file" id="uploadbutton"/>
		
		                            </td>
		                        </tr>
		                    </tfoot>
		                </table>
	                
	
	            <div id="progressBar" style="display: none;">
	                    <div id="progressBarText"></div>
	                    <div id="progressBarBox">
	                        <div id="progressBarBoxContent"></div>
	                    </div>
	            </div>
	                
	        </div>
	        <div id="footer">Gunnar Hillert, 2006 - <a href="http://www.hillert.com">www.hillert.com</a></div>
        </form:form>
	</body>
</html>


