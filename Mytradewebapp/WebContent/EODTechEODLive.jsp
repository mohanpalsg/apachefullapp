<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function EODLivetechFormChange() {
          	
        	
         
        	
        	$.post(
                    "EODtechnicalfilershowliveServlet", 
                    {
                  
                     chartinterval : $( "#PeriodSelect option:selected" ).val()
                     
                     




                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        
        
        
        $('#dataTables-EODLivetechFormChange').DataTable({
            responsive: true
    });
        
       
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Technical Indicators EOD Filtered - Live </p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-EODLivetechFormChange">
       
<thead>

<tr>
       <td>Symbol</td>
       <td>O-Price</td>
       <td>C-Price</td>
       <td>S50</td>
       <td>SK</td>
       <td>SD</td>
       <td>WPR</td>
       <td>RSI</td>
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getOpen()}</td>
<td>${entry.value.getClose()}</td>
<td>${entry.value.getSma50()}</td>
<td>${entry.value.getDayk()}</td>
<td>${entry.value.getDayd()}</td>
<td>${entry.value.getDaywilliamsr()}</td>
<td>${entry.value.getDayrsi()}</td>


</tr>
    
</c:forEach>
</tbody>   

</table>
   </div>
                            
                                <div class="dataTable_wrapper col-lg-3">
                                <div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Applied Filters</p>
                            </div>
                             <div class="panel-body">
<div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Candle Interval</p>
                            </div>
                             <div class="panel-body">
                             
 <select id="PeriodSelect" onchange="EODLivetechFormChange()" >
 
 <option value="5"  <c:if test="${Minselect eq 5}">
selected
</c:if>>5Min</option>
 

 <option value="15"  <c:if test="${Minselect eq 15}">
selected
</c:if>>15Min</option>

 <option value="30"  <c:if test="${Minselect eq 30}">
selected
</c:if>>30Min</option>

<option value="60"  <c:if test="${Minselect eq 60}">
selected
</c:if>>1Hour</option>

<option value="120"  <c:if test="${Minselect eq 120}">
selected
</c:if>>2Hour</option>



</select>

</div>
</div>

</div>

        </div>
</div>
</div>