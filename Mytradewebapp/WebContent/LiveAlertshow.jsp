<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        
        
        $('#dataTables-LIVEALERTSHOW').DataTable({
            responsive: true
    });
        
        alerttimerID = setInterval(function() {
        	
        	
		     $.get('LiveAlertServlet',{auto:"true"},function(responseText) { 
		    	 if($.trim(responseText) == 'nothing')
		    		 {
		    		 
		    		 
		    		 
		    		 }
		    	 else
		    		 {
		    		 clearInterval(alerttimerID); 
                  $('#content').html(responseText);  
		    		 }
              });
		  
		   
		    
		  
		  }, 120 * 1000);
        
        
       
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Live Alert </p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-LIVEALERTSHOW">
       
<thead>

<tr>
       <td>AlertTime</td>
       <td>stocksymbol</td>
       <td>Message</td>
     
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>

 <td>${entry.value.getAlertime()}</td>
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getMessage()}</td>



</tr>
    
</c:forEach>
</tbody>   

</table>

<c:if test="${alarmsound}">
<audio src="https://notificationsounds.com/notification-sounds/coins-497" autoplay="autoplay"></audio>
</c:if>
   </div>
                            
        
</div>
</div>