<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<script type="text/javascript">
        function weeksmapvselect() {
        	
        	
        	  var PPchecked=Leveloptionselected("PP");
              var S1checked = Leveloptionselected("S1");
              var S2checked = Leveloptionselected("S2");
              var S3checked = Leveloptionselected("S3");
              var S4checked = Leveloptionselected("S4");
              var R1checked = Leveloptionselected("R1");
              var R2checked = Leveloptionselected("R2");
              var R3checked = Leveloptionselected("R3");
              var R4checked = Leveloptionselected("R4");
              
        	
        	
        	$.post(
                    "WeeksmapvServlet", 
                    {nse200attr : $(wknse200check).is(':checked'),otherattr : $(wknseothercheck).is(':checked'),pivotdiffattr: $("#wksmpvval").val(), smadiffattr : $("#weeksmapvsmval").val(),
                    	ppcheckattr: PPchecked,
                    	s1checkattr: S1checked,
                    	s2checkattr: S2checked,
                    	s3checkattr: S3checked,
                    	s4checkattr: S4checked,
                    	r1checkattr: R1checked,
                    	r2checkattr: R2checked,
                    	r3checkattr: R3checked,
                    	r4checkattr: R4checked,
                    	
                    
                    }, //meaasge you want to send
                    
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	
        }
        
        
 $('#dataTables-EODWEEKSMAANDPV').DataTable({
        responsive: true
});

 
	$('#W1LevelSelect').multiselect();
 	 
 	 function Leveloptionselected (level)
 		{
 			
 			var selected = "false";
 			 $( "#W1LevelSelect option:selected" ).each(
 				
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
                                <p>Dashboard EOD- Weekly Pivot &amp; SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-EODWEEKSMAANDPV">

<thead>
<tr>
       <td>Stock</td>
       <td>PvL</td>
       <td>PvVal</td>
       <td>SmaVal</td>
       <td>P-Close</td>
       <td>C-Close</td>
       <td>SmaDiff</td>
       <td>PvDiff</td>
      
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
                                <div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Applied Filters</p>
                            </div>
                             <div class="panel-body">

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Pivot Level</p>
                            </div>
                             <div class="panel-body">

 <select id="W1LevelSelect" onchange="weeksmapvselect()" multiple="multiple">
    <option id="Weeksma50pvPPcheck"  value="PP"  <c:if test="${Weeksma50pvPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${Weeksma50pvS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${Weeksma50pvS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${Weeksma50pvS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${Weeksma50pvS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${Weeksma50pvR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${Weeksma50pvR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${Weeksma50pvR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${Weeksma50pvR4 eq true}">
selected
</c:if>>Pivot R4</option>
</select>

<br><br>
</div>
</div>

  <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">
                                <input id="wknse200check" type="checkbox"     
name="nse200"      
onchange = "weeksmapvselect()" <c:if test="${nsechecked eq true}">
checked
</c:if>>nse200     
<input id="wknseothercheck" type="checkbox"     
name="others"     
onchange = "weeksmapvselect()" <c:if test="${otherchecked eq true}">
checked
</c:if>/>Others  


  <br><br>
 
  
   <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">

Smadiff<input id="weeksmapvsmslider" type="range" value="<c:out value="${smadiffval}"/>" min="0" max="10" step="0.5"  onchange="weeksmapvselect()" oninput="weeksmapvsmval.value = weeksmapvsmslider.value">
<output name="weeksmapvsmOutputName" id="weeksmapvsmval"><c:out value="${smadiffval}"/></output> 
&nbsp;&nbsp;
<br>
pivotdiff<input id="weeksmapvpivotslider" type="range" value="<c:out value="${pivotdiffval}"/>" min="0" max="10" step="0.5" onchange="weeksmapvselect()" oninput="wksmpvval.value = weeksmapvpivotslider.value">
<output name="weeksmapvpivotsliderOutputName" id="wksmpvval"><c:out value="${pivotdiffval}"/></output>
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
    