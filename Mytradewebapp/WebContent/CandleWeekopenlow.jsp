<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        
function WeekcandleopenlowFormChange() {
  	
	
	
    
	$.post(
            "WeekCandleOpenlowServlet", 
            {
            	
            	candleweekopenlownseattr : $(WeekCandleopenlownse200check).is(':checked'),
                candleweekopenlowotherattr : $(WeekCandleopenlowothercheck).is(':checked')
                
            },
            function(result) {
            	 $('#content').html(result);
        });
	
	
	  
	 	
}
    	
  	  $('#dataTables-CandleWeekopenlow').DataTable({
            responsive: true
    });
  	  
  	
 	 
</script>



       
<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Weekly Candle pattern</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-CandleWeekopenlow">



<thead>
<tr>
       <td>Stock</td>
       <td>Open</td>
       <td>High</td>
       <td>Low</td>
       <td>Close</td>
       <td>Prev-Close</td>
       <td>Closediff</td>
       <td>Matched</td>
       
      
    </tr>
    </thead>
    <tbody>
<c:forEach var="entry" items="${openlowStockList}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getCurr_open()}</td>
<td>${entry.value.getCurr_high()}</td>
<td>${entry.value.getCurr_low()}</td>
<td>${entry.value.getCurr_close()}</td>
<td>${entry.value.getPrev_close()}</td>
<td>${entry.value.getClosediff()}</td>
<td>${entry.value.getMatch()}</td>

       
</tr>
    
</c:forEach>

</tbody>


<c:import url="aftertbodybeforecriteria.txt" />


 
                                                       
  <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">                               

<input id="WeekCandleopenlownse200check" type="checkbox"     
name="nse200" onchange="WeekcandleopenlowFormChange()"     
 <c:if test="${CandleWeekopenlownse eq true}">
checked
</c:if>>nse200       
<input id="WeekCandleopenlowothercheck" type="checkbox"     
name="others"   onchange="WeekcandleopenlowFormChange()"  
 <c:if test="${CandleWeekopenlowother eq true}">
checked
</c:if>>Others <br>
</div>
</div>

<c:import url="aftercriteria.txt" />

    