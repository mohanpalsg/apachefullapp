<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<script type="text/javascript">
        function weeksmapvselect() {
        	
        	
        	
        	
        	$.post(
                    "WeeksmapvServlet", 
                    {nse200attr : $(wknse200check).is(':checked'),otherattr : $(wknseothercheck).is(':checked'),pivotdiffattr: $("#wksmpvval").val(), smadiffattr : $("#weeksmapvsmval").val(),
                    	ppcheckattr: $(Weeksma50pvPPcheck).is(':checked'),
                    	s1checkattr: $(Weeksma50pvS1check).is(':checked'),
                    	s2checkattr: $(Weeksma50pvS2check).is(':checked'),
                    	s3checkattr: $(Weeksma50pvS3check).is(':checked'),
                    	s4checkattr: $(Weeksma50pvS4check).is(':checked'),
                    	r1checkattr: $(Weeksma50pvR1check).is(':checked'),
                    	r2checkattr: $(Weeksma50pvR2check).is(':checked'),
                    	r3checkattr: $(Weeksma50pvR3check).is(':checked'),
                    	r4checkattr: $(Weeksma50pvR4check).is(':checked')
                    	
                    
                    }, //meaasge you want to send
                    
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	
        }
        
        
    
            $('#dataTables-EODWEEKSMAANDPV').DataTable({
                    responsive: true
            });
 

</script>


<div class="panel panel-default">
                            <div class="panel-heading">
                                <p>Dashboard EOD- Weekly Pivot &amp; SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-EODWEEKSMAANDPV">

<thead>
<tr>
       <th>Stock</th>
       <th>PvL</th>
       <th>PvVal</th>
       <th>SmaVal</th>
       <th>P-Close</th>
       <th>C-Close</th>
       <th>SmaDiff</th>
       <th>PvDiff</th>
      
    </tr>
 </thead>  
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
    <tr>
       <td>${entry.value.getStocksymbol()}</td>
       <td>${entry.value.getPivotlevel()}</td>
       <td>${entry.value.getPivotvalue()}</td>
       <td>${entry.value.getSmavalue()}</td>
       <td>${entry.value.getPrevclose()}</td>
       <td>${entry.value.getCurrclose()}</td>
       <td>${entry.value.getSmadiff()}</td>
       <td>${entry.value.getPivotdiff()}</td>
       
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


 

<div class="btn-group" data-toggle="buttons">
    <label class="btn btn-primary">
      <input id="wknse200check" type="checkbox"     
name="nse200"      
onchange = "weeksmapvselect()"  > NSE200
    </label>
    <label class="btn btn-primary ">
      <input id="wknseothercheck" type="checkbox"     
name="others"     
onchange = "weeksmapvselect()"> OTHERS
    </label>
  </div>
  
  
&nbsp;&nbsp;&nbsp;&nbsp;
Smadiff<input id="weeksmapvsmslider" type="range" value="<c:out value="${smadiffval}"/>" min="0" max="10" step="0.5"  onchange="weeksmapvselect()" oninput="weeksmapvsmval.value = weeksmapvsmslider.value">
<output name="weeksmapvsmOutputName" id="weeksmapvsmval"><c:out value="${smadiffval}"/></output> 
&nbsp;&nbsp;
pivotdiff<input id="weeksmapvpivotslider" type="range" value="<c:out value="${pivotdiffval}"/>" min="0" max="10" step="0.5" onchange="weeksmapvselect()" oninput="wksmpvval.value = weeksmapvpivotslider.value">
<output name="weeksmapvpivotsliderOutputName" id="wksmpvval"><c:out value="${pivotdiffval}"/></output>
<br>
<p> Pivot Level </p>
<input id="Weeksma50pvPPcheck" type="checkbox"     
name="Weeksma50pvPPcheck"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvPP eq true}">
checked
</c:if>>Pivot-PP 
<input id="Weeksma50pvS1check" type="checkbox"     
name="Weeksma50pvS1check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="Weeksma50pvS2check" type="checkbox"     
name="Weeksma50pvS2check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="Weeksma50pvS3check" type="checkbox"     
name="Weeksma50pvS3check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="Weeksma50pvS4check" type="checkbox"     
name="Weeksma50pvS4check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvS4 eq true}">
checked
</c:if>>Pivot-S4
<input id="Weeksma50pvR1check" type="checkbox"     
name="Weeksma50pvR1check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="Weeksma50pvR2check" type="checkbox"     
name="Weeksma50pvR2check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="Weeksma50pvR3check" type="checkbox"     
name="Weeksma50pvR3check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="Weeksma50pvR4check" type="checkbox"     
name="Weeksma50pvR4check"      
onchange = "weeksmapvselect()" <c:if test="${Weeksma50pvR4 eq true}">
checked
</c:if>>Pivot-R4

</div>
</div>
</div>
        </div>
</div>
</div>
    