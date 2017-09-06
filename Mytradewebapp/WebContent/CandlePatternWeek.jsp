<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        
function WeekcandleFormChange() {
  	
	
	var nsechecked = $(WeekCandlense200check).is(':checked');
	var otherschecked = $(WeekCandleothercheck).is(':checked');
	var dojiselected=Weekcandlepatterselected("doji");
    var beselected = Weekcandlepatterselected("Engulfing");
    var hamselected = Weekcandlepatterselected("Hammer");
    var bhselected = Weekcandlepatterselected("Harami");
    var bkselected = Weekcandlepatterselected("Kicker");



	$.post(
            "CandlePatternServlet", 
            {
            	
            	candleweeknseattr : nsechecked,
                candleweekotherattr : otherschecked,
                candleweekdojiattr : dojiselected,
                candleweekbeattr : beselected,
                candleweekhamattr : hamselected,
                candleweekbhattr : bhselected,
                candleweekbkeattr : bkselected
             
            },
            function(result) {
            	 $('#content').html(result);
        });
	
	
	  
	 	
}
    	
  	  $('#dataTables-CandleWeek').DataTable({
            responsive: true
    });
  	  
  	$('#WeekcdSelect').multiselect();
	 
	 function Weekcandlepatterselected (level)
		{
			
			var selected = "false";
			 $( "#WeekcdSelect option:selected" ).each(
				
					 function() {
						
	             if ($(this).val() == level)
	                selected = "true";
	            		 
	                         }
			 );
			 
			return selected;
		}
 	 
</script>



       
<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Weekly Candle pattern</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-CandleWeek">



<thead>
<tr>
       <td>Stock</td>
       <td>Open</td>
       <td>High</td>
       <td>Low</td>
       <td>Close</td>
       <td>Prev-Close</td>
       <td>Pattern</td>
       
      
    </tr>
    </thead>
    <tbody>
<c:forEach var="entry" items="${StockList}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getCurr_open()}</td>
<td>${entry.value.getCurr_high()}</td>
<td>${entry.value.getCurr_low()}</td>
<td>${entry.value.getCurr_close()}</td>
<td>${entry.value.getPrev_close()}</td>
<td>${entry.value.getCandlepattern()}</td>

       
</tr>
    
</c:forEach>

</tbody>


<c:import url="aftertbodybeforecriteria.txt" />


 
                                                    
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">                               

<input id="WeekCandlense200check" type="checkbox"     
name="nse200" onchange="WeekcandleFormChange()"     
 <c:if test="${CandleWeeknse eq true}">
checked
</c:if>>nse200       
<input id="WeekCandleothercheck" type="checkbox"     
name="others"   onchange="WeekcandleFormChange()"  
 <c:if test="${CandleWeekother eq true}">
checked
</c:if>>Others <br>

<br>


</div>
</div>
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Pattern Select</p>
                            </div>
                             <div class="panel-body">
                             
 <select id="WeekcdSelect" onchange="WeekcandleFormChange()" multiple="multiple">
 

 

 <option value="doji"  <c:if test="${CandleWeekdoji eq true}">
selected
</c:if>>Doji</option>

 <option value="Engulfing"  <c:if test="${CandleWeekBullishEngulfing eq true}">
selected
</c:if>>Engulfing</option>

 <option value="Hammer"  <c:if test="${CandleWeekHammer eq true}">
selected
</c:if>>Hammer</option>

 <option value="Harami"  <c:if test="${CandleWeekBullishHarami eq true}">
selected
</c:if>>Harami</option>

 <option value="Kicker"  <c:if test="${CandleWeekBullishKicker eq true}">
selected
</c:if>>Kicker</option>




</select>


</div>
</div>


<c:import url="aftercriteria.txt" />

    