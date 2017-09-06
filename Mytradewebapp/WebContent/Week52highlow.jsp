<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function WEEK52diffFormChange() {
          	
        	
        	
        	
        	$.post(
                    "Week52HighLow", 
                    {WEEK52diffnse200attr : $(WEEK52diffnse200check).is(':checked'),
                     WEEK52diffotherattr : $(WEEK52diffothercheck).is(':checked'),
                     WEEK52lowdiffrangemax : $("#WEEK52maxdiffval").val(),
                     WEEK52highdiffrangemin : $("#WEEK52mindiffval").val(),
                     WEEK52diffstochk : $("#WEEK52StochKval").val(),
                     WEEK52diffstochd : $("#WEEK52StochDval").val()
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 
        	  
        }
        
        $('#dataTables-WEEK52DIFF').DataTable({
            responsive: true
    });
        
       
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>52 Week High/Low Diff</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEK52DIFF">
       
<thead>

<tr>
       <td>Stocksymbol</td>
       <td>HistHigh</td>
       <td>CurrHigh</td>
       <td>Highdiff</td>
       <td>HistLow</td>
       <td>CurrLow</td>
       <td>Lowdiff</td>
       <td>StochK</td>
        <td>StochD</td>
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getHighprice()}</td>
<td>${entry.value.getCurrenthighprice()}</td>
<td>${entry.value.getHighdiff()}</td>
<td>${entry.value.getLowprice()}</td>
<td>${entry.value.getCurrentlowprice()}</td>
<td>${entry.value.getLowdiff()}</td>
<td>${entry.value.getStockk()}</td>
<td>${entry.value.getStockd()}</td>
       
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
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">          
<input id="WEEK52diffnse200check" type="checkbox"     
name="nse200"      
onchange = "WEEK52diffFormChange()" <c:if test="${WEEK52diffnse eq true}">
checked
</c:if>>nse200       
<input id="WEEK52diffothercheck" type="checkbox"     
name="others"     
onchange = "WEEK52diffFormChange()" <c:if test="${WEEK52diffothers eq true}">
checked
</c:if>>Others <br>

<br>



 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">

LowMaxPercent<input id="WEEK52maxdiffslider" type="range" value="<c:out value="${WEEK52diffrangemax}"/>" min="0" max="20" step="1"  onchange="WEEK52diffFormChange()" oninput="WEEK52maxdiffval.value =WEEK52maxdiffslider.value">
<output name="WEEK52maxdiffOutputName" id="WEEK52maxdiffval"><c:out value="${WEEK52diffrangemax}"/></output> 
<br>
HighMinPercent<input id="WEEK52mindiffslider" type="range" value="<c:out value="${WEEK52diffrangemin}"/>" min="-20" max="0" step="1"  onchange="WEEK52diffFormChange()" oninput="WEEK52mindiffval.value =WEEK52mindiffslider.value">
<output name="WEEK52mindiffOutputName" id="WEEK52mindiffval"><c:out value="${WEEK52diffrangemin}"/></output> 
<br>
StochK<input id="WEEK52StochKslider" type="range" value="<c:out value="${WEEK52percentk}"/>" min="0" max="100" step="5"  onchange="WEEK52diffFormChange()" oninput="WEEK52StochKval.value =WEEK52StochKslider.value">
<output name="WEEK52StochKOutputName" id="WEEK52StochKval"><c:out value="${WEEK52percentk}"/></output> 
<br>
StochD<input id="WEEK52StochDslider" type="range" value="<c:out value="${WEEK52percentd}"/>" min="0" max="100" step="5"  onchange="WEEK52diffFormChange()" oninput="WEEK52StochDval.value =WEEK52StochDslider.value">
<output name="WEEK52StochDOutputName" id="WEEK52StochDval"><c:out value="${WEEK52percentd}"/></output> 
<br>

</div>
</div>

</div>
</div>
</div>
</div>
</div>

        </div>
</div>
</div>