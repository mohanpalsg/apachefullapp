<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function CPSbreakFormChange() {
          	
        	
        	$.post(
                    "CPSbreakservlet", 
                    {CPSbreaknse200attr : $(CPSbreaknse200check).is(':checked'),
                     CPSbreakotherattr : $(CPSbreakothercheck).is(':checked'),
                     CPSbreakdiffattr: $("#CPSbreakoutputval").val(),
                     CPSbreakoptionSMA : $(CPSbreakSMAcheck).is(':checked'),
                     CPSbreakoptionPP : $(CPSbreakPPcheck).is(':checked'),
                     CPSbreakoptionS1 : $(CPSbreakS1check).is(':checked'),
                     CPSbreakoptionS2 : $(CPSbreakS2check).is(':checked'),
                     CPSbreakoptionS3 : $(CPSbreakS3check).is(':checked'),
                     CPSbreakoptionS4 : $(CPSbreakS4check).is(':checked'),
                     CPSbreakoptionR1 : $(CPSbreakR1check).is(':checked'),
                     CPSbreakoptionR2 : $(CPSbreakR2check).is(':checked'),
                     CPSbreakoptionR3 : $(CPSbreakR3check).is(':checked'),
                     CPSbreakoptionR4 : $(CPSbreakR4check).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	
        	  
        	 	
        }
        $('#dataTables-WKSMAORPV').DataTable({
            responsive: true
    });
</script>


<div class="panel panel-default">
                            <div class="panel-heading">
                                <p>Live- Weekly Pivot/SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WKSMAORPV">

<thead>
<tr>
       <td>Stock</td>
       <td>B-level</td>
       <td>L-Val</td>
       <td>P-close</td>
       <td>L-Price</td>
       <td>diff</td>
       
      
    </tr>
    </thead>
    <tbody>
<c:forEach var="entry" items="${currentpricepivotsmabreak}" >
      
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

<c:import url="aftertbodybeforecriteria.txt" />

<input id="CPSbreaknse200check" type="checkbox"     
name="nse200"      
onchange = "CPSbreakFormChange()" <c:if test="${cpsbreaknse eq true}">
checked
</c:if>>nse200       
<input id="CPSbreakothercheck" type="checkbox"     
name="others"     
onchange = "CPSbreakFormChange()" <c:if test="${cpsbreakothers eq true}">
checked
</c:if>>Others 
&nbsp;&nbsp;
Percent<input id="CPSbreakslider" type="range" value="<c:out value="${cpsperslide}"/>" min="0" max="10" step="0.5"  onchange="CPSbreakFormChange()" oninput="CPSbreakoutputval.value =CPSbreakslider.value">
<output name="CPSbreakOutputName" id="CPSbreakoutputval"><c:out value="${cpsperslide}"/></output> 
&nbsp;&nbsp;
<div>
<p>Level</p>

<input id="CPSbreakSMAcheck" type="checkbox"     
name="CPSbreakSMAcheck"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakSMA eq true}">
checked
</c:if>>SMA50
<input id="CPSbreakPPcheck" type="checkbox"     
name="CPSbreakPPcheck"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakPP eq true}">
checked
</c:if>>Pivot-PP <br>
<input id="CPSbreakS1check" type="checkbox"     
name="CPSbreakS1check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="CPSbreakS2check" type="checkbox"     
name="CPSbreakS2check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="CPSbreakS3check" type="checkbox"     
name="CPSbreakS3check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="CPSbreakS4check" type="checkbox"     
name="CPSbreakS4check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakS4 eq true}">
checked
</c:if>>Pivot-S4<br>
<input id="CPSbreakR1check" type="checkbox"     
name="CPSbreakR1check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="CPSbreakR2check" type="checkbox"     
name="CPSbreakR2check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="CPSbreakR3check" type="checkbox"     
name="CPSbreakR3check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="CPSbreakR4check" type="checkbox"     
name="CPSbreakR4check"      
onchange = "CPSbreakFormChange()" <c:if test="${CPSbreakR4 eq true}">
checked
</c:if>>Pivot-R4
</div>

<c:import url="aftercriteria.txt" />


       
       