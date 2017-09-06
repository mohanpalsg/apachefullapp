<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function CPSbreakFormChange() {
          	
        	
        	var smachecked=Leveloptionselectedpg5("SMA");
        	var PPchecked=Leveloptionselectedpg5("PP");
            var S1checked = Leveloptionselectedpg5("S1");
            var S2checked = Leveloptionselectedpg5("S2");
            var S3checked = Leveloptionselectedpg5("S3");
            var S4checked = Leveloptionselectedpg5("S4");
            var R1checked = Leveloptionselectedpg5("R1");
            var R2checked = Leveloptionselectedpg5("R2");
            var R3checked = Leveloptionselectedpg5("R3");
            var R4checked = Leveloptionselectedpg5("R4");
            
        	$.post(
                    "CPSbreakservlet", 
                    {CPSbreaknse200attr : $(CPSbreaknse200check).is(':checked'),
                     CPSbreakotherattr : $(CPSbreakothercheck).is(':checked'),
                     CPSbreakdiffattr: $("#CPSbreakoutputval").val(),
                     CPSbreakoptionSMA :  smachecked,
                     CPSbreakoptionPP : PPchecked,
                     CPSbreakoptionS1 : S1checked,
                     CPSbreakoptionS2 : S2checked,
                     CPSbreakoptionS3 : S3checked,
                     CPSbreakoptionS4 : S4checked,
                     CPSbreakoptionR1 : R1checked,
                     CPSbreakoptionR2 : R2checked,
                     CPSbreakoptionR3 : R3checked,
                     CPSbreakoptionR4 : R4checked
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	
        	  
        	 	
        }
        
        $('#dataTables-WKSMAORPV').DataTable({
            responsive: true
    });
        
        
    	$('#W5LevelSelect').multiselect();
     	 
     	 function Leveloptionselectedpg5 (level)
     		{
     			
     			var selected = "false";
     			 $( "#W5LevelSelect option:selected" ).each(
     				
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


 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Pivot Level</p>
                            </div>
                             <div class="panel-body">

 <select id="W5LevelSelect" onchange="CPSbreakFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${CPSbreakSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${CPSbreakPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${CPSbreakS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${CPSbreakS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${CPSbreakS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${CPSbreakS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${CPSbreakR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${CPSbreakR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${CPSbreakR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${CPSbreakR4 eq true}">
selected
</c:if>>Pivot R4</option>
</select>

  </div>
  </div>
<br><br>
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">
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

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
<br><br>
Percent<input id="CPSbreakslider" type="range" value="<c:out value="${cpsperslide}"/>" min="0" max="10" step="0.5"  onchange="CPSbreakFormChange()" oninput="CPSbreakoutputval.value =CPSbreakslider.value">
<output name="CPSbreakOutputName" id="CPSbreakoutputval"><c:out value="${cpsperslide}"/></output> 
</div>
</div>

</div>
</div>

<c:import url="aftercriteria.txt" />


       
       