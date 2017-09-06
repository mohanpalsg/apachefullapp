<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        
function MonthcandleFormChange() {
  	
	
	var nsechecked = $(MonthCandlense200check).is(':checked');
	var otherschecked = $(MonthCandleothercheck).is(':checked');
	var dojiselected=Monthcandlepatterselected("doji");
    var beselected = Monthcandlepatterselected("Engulfing");
    var hamselected = Monthcandlepatterselected("Hammer");
    var bhselected = Monthcandlepatterselected("Harami");
    var bkselected = Monthcandlepatterselected("Kicker");



	$.post(
            "CandlePatternMonthServlet", 
            {
            	
            	candlemonthnseattr : nsechecked,
                candlemonthotherattr : otherschecked,
                candlemonthdojiattr : dojiselected,
                candlemonthbeattr : beselected,
                candlemonthhamattr : hamselected,
                candlemonthbhattr : bhselected,
                candlemonthbkeattr : bkselected
             
            },
            function(result) {
            	 $('#content').html(result);
        });
	
	
	  
	 	
}
    	
  	  $('#dataTables-CandleMonth').DataTable({
            responsive: true
    });
  	  
  	$('#MonthcdSelect').multiselect();
	 
	 function Monthcandlepatterselected (level)
		{
			
			var selected = "false";
			 $( "#MonthcdSelect option:selected" ).each(
				
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
                                <p>Monthly Candle pattern</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-CandleMonth">



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

<input id="MonthCandlense200check" type="checkbox"     
name="nse200" onchange="MonthcandleFormChange()"     
 <c:if test="${CandleMonthnse eq true}">
checked
</c:if>>nse200       
<input id="MonthCandleothercheck" type="checkbox"     
name="others"   onchange="MonthcandleFormChange()"  
 <c:if test="${CandleMonthother eq true}">
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
                             
 <select id="MonthcdSelect" onchange="MonthcandleFormChange()" multiple="multiple">
 

 

 <option value="doji"  <c:if test="${CandleMonthdoji eq true}">
selected
</c:if>>Doji</option>

 <option value="Engulfing"  <c:if test="${CandleMonthBullishEngulfing eq true}">
selected
</c:if>>Engulfing</option>

 <option value="Hammer"  <c:if test="${CandleMonthHammer eq true}">
selected
</c:if>>Hammer</option>

 <option value="Harami"  <c:if test="${CandleMonthBullishHarami eq true}">
selected
</c:if>>Harami</option>

 <option value="Kicker"  <c:if test="${CandleMonthBullishKicker eq true}">
selected
</c:if>>Kicker</option>




</select>

</div>
</div>



<c:import url="aftercriteria.txt" />

    