<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function ATHLdiffFormChange() {
          	
        	
        	
        	
        	$.post(
                    "AllTimeHighLowServlet", 
                    {ATHLdiffnse200attr : $(ATHLdiffnse200check).is(':checked'),
                     ATHLdiffotherattr : $(ATHLdiffothercheck).is(':checked'),
                     ATHLlowdiffrangemax : $("#ATHLmaxdiffval").val(),
                     ATHLhighdiffrangemin : $("#ATHLmindiffval").val(),
                     ATHLdiffstochk : $("#ATHLStochKval").val(),
                     ATHLdiffstochd : $("#ATHLStochDval").val()
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 
        	  
        }
        
        $('#dataTables-ATHLDIFF').DataTable({
            responsive: true
    });
        
       
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>ALL TIME High/Low Diff</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-ATHLDIFF">
       
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
<input id="ATHLdiffnse200check" type="checkbox"     
name="nse200"      
onchange = "ATHLdiffFormChange()" <c:if test="${ATHLdiffnse eq true}">
checked
</c:if>>nse200       
<input id="ATHLdiffothercheck" type="checkbox"     
name="others"     
onchange = "ATHLdiffFormChange()" <c:if test="${ATHLdiffothers eq true}">
checked
</c:if>>Others <br>

<br>



 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">

LowMaxPercent<input id="ATHLmaxdiffslider" type="range" value="<c:out value="${ATHLdiffrangemax}"/>" min="0" max="20" step="1"  onchange="ATHLdiffFormChange()" oninput="ATHLmaxdiffval.value =ATHLmaxdiffslider.value">
<output name="ATHLmaxdiffOutputName" id="ATHLmaxdiffval"><c:out value="${ATHLdiffrangemax}"/></output> 
<br>
HighMinPercent<input id="ATHLmindiffslider" type="range" value="<c:out value="${ATHLdiffrangemin}"/>" min="-20" max="0" step="1"  onchange="ATHLdiffFormChange()" oninput="ATHLmindiffval.value =ATHLmindiffslider.value">
<output name="ATHLmindiffOutputName" id="ATHLmindiffval"><c:out value="${ATHLdiffrangemin}"/></output> 
<br>
StochK<input id="ATHLStochKslider" type="range" value="<c:out value="${ATHLpercentk}"/>" min="0" max="100" step="5"  onchange="ATHLdiffFormChange()" oninput="ATHLStochKval.value =ATHLStochKslider.value">
<output name="ATHLStochKOutputName" id="ATHLStochKval"><c:out value="${ATHLpercentk}"/></output> 
<br>
StochD<input id="ATHLStochDslider" type="range" value="<c:out value="${ATHLpercentd}"/>" min="0" max="100" step="5"  onchange="ATHLdiffFormChange()" oninput="ATHLStochDval.value =ATHLStochDslider.value">
<output name="ATHLStochDOutputName" id="ATHLStochDval"><c:out value="${ATHLpercentd}"/></output> 
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