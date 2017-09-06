<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function EPSbreakFormChange() {
          	
        	
        	$.post(
                    "EPSbreakservlet", 
                    {EPSbreaknse200attr : $(EPSbreaknse200check).is(':checked'),
                     EPSbreakotherattr : $(EPSbreakothercheck).is(':checked'),
                     EPSbreakdiffattr: $("#EPSbreakoutputval").val(),
                     EPSbreakoptionSMA : $(EPSbreakSMAcheck).is(':checked'),
                     EPSbreakoptionPP : $(EPSbreakPPcheck).is(':checked'),
                     EPSbreakoptionS1 : $(EPSbreakS1check).is(':checked'),
                     EPSbreakoptionS2 : $(EPSbreakS2check).is(':checked'),
                     EPSbreakoptionS3 : $(EPSbreakS3check).is(':checked'),
                     EPSbreakoptionS4 : $(EPSbreakS4check).is(':checked'),
                     EPSbreakoptionR1 : $(EPSbreakR1check).is(':checked'),
                     EPSbreakoptionR2 : $(EPSbreakR2check).is(':checked'),
                     EPSbreakoptionR3 : $(EPSbreakR3check).is(':checked'),
                     EPSbreakoptionR4 : $(EPSbreakR4check).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 
        }
    	
  	  $('#dataTables-WEEKEODSMAPV').DataTable({
            responsive: true
    });
</script>

<div class="panel panel-default">
                            <div class="panel-heading">
                                <p>EOD- Weekly Pivot/SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKEODSMAPV">

<thead>
<tr>
       <th>Stock</th>
       <th>B-Level</th>
       <th>L-Val</th>
       <th>P-close</th>
       <th>C-close</th>
       <th>diff</th>
       
      
    </tr>
 </thead>
 <tbody>  
<c:forEach var="entry" items="${EODpivotsmabreak}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<c:if test="${entry.value.isBreaksma() eq true}">
<td>SMA50</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotlevel()}</td>
</c:if>
<c:if test="${entry.value.isBreaksma() eq true}">
<td>${entry.value.getSmavalue()}</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotval()}</td>
</c:if>

       <td>${entry.value.getPrevclose()}</td>
       <td>${entry.value.getCurrclose()}</td>
       
<c:if test="${entry.value.isBreaksma() eq true}">
<td>${entry.value.getSmadiff()}</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotdiff()}</td>
</c:if>

      
    </tr>
    
</c:forEach>
</tbody> 

</table>
   </div>
                            
                                <div class="dataTable_wrapper col-lg-3">
                                <div class="panel panel-default">
                            <div class="panel-heading">
                                <p>Applied Filters</p>
                            </div>
                             <div class="panel-body">
<input id="EPSbreaknse200check" type="checkbox"     
name="nse200"      
onchange = "EPSbreakFormChange()" <c:if test="${epsbreaknse eq true}">
checked
</c:if>>nse200       
<input id="EPSbreakothercheck" type="checkbox"     
name="others"     
onchange = "EPSbreakFormChange()" <c:if test="${epsbreakothers eq true}">
checked
</c:if>>Others 
&nbsp;&nbsp;
Percent<input id="EPSbreakslider" type="range" value="<c:out value="${epsperslide}"/>" min="0" max="10" step="0.5"  onchange="EPSbreakFormChange()" oninput="EPSbreakoutputval.value =EPSbreakslider.value">
<output name="EPSbreakOutputName" id="EPSbreakoutputval"><c:out value="${epsperslide}"/></output> 
&nbsp;&nbsp;
<div>
<p>Level</p>

<input id="EPSbreakSMAcheck" type="checkbox"     
name="EPSbreakSMAcheck"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakSMA eq true}">
checked
</c:if>>SMA50
<input id="EPSbreakPPcheck" type="checkbox"     
name="EPSbreakPPcheck"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakPP eq true}">
checked
</c:if>>Pivot-PP <br>
<input id="EPSbreakS1check" type="checkbox"     
name="EPSbreakS1check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="EPSbreakS2check" type="checkbox"     
name="EPSbreakS2check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="EPSbreakS3check" type="checkbox"     
name="EPSbreakS3check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="EPSbreakS4check" type="checkbox"     
name="EPSbreakS4check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakS4 eq true}">
checked
</c:if>>Pivot-S4<br>
<input id="EPSbreakR1check" type="checkbox"     
name="EPSbreakR1check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="EPSbreakR2check" type="checkbox"     
name="EPSbreakR2check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="EPSbreakR3check" type="checkbox"     
name="EPSbreakR3check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="EPSbreakR4check" type="checkbox"     
name="EPSbreakR4check"      
onchange = "EPSbreakFormChange()" <c:if test="${EPSbreakR4 eq true}">
checked
</c:if>>Pivot-R4
</div>

</div>
</div>
</div>
        </div>
</div>
    </div>
    