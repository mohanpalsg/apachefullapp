<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function Currentdaysmapvselect() {
        	
        	  var PPchecked=Leveloptionselectedpg4("PP");
              var S1checked = Leveloptionselectedpg4("S1");
              var S2checked = Leveloptionselectedpg4("S2");
              var S3checked = Leveloptionselectedpg4("S3");
              var S4checked = Leveloptionselectedpg4("S4");
              var R1checked = Leveloptionselectedpg4("R1");
              var R2checked = Leveloptionselectedpg4("R2");
              var R3checked = Leveloptionselectedpg4("R3");
              var R4checked = Leveloptionselectedpg4("R4");
              
        	$.post(
                    "DaydaysmapvServlet", 
                    {cnse200attr : $(curwknse200check).is(':checked'),cotherattr : $(curwknseothercheck).is(':checked'),cpivotdiffattr: $("#curwksmpvval").val(), csmadiffattr : $("#curdaysmapvsmval").val(),
                    	cppcheckattr: PPchecked,
                    	cs1checkattr: S1checked,
                    	cs2checkattr: S2checked,
                    	cs3checkattr: S3checked,
                    	cs4checkattr: S4checked,
                    	cr1checkattr: R1checked,
                    	cr2checkattr: R2checked,
                    	cr3checkattr: R3checked,
                    	cr4checkattr: R4checked
                    	
                    
                    }, //meaasge you want to send
                    
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	 
        }
        
        $('#dataTables-WEEKLIVESMAPV').DataTable({
            responsive: true
    });
        
        $('#W4LevelSelect').multiselect();
    	 
    	 function Leveloptionselectedpg4 (level)
    		{
    			
    			var selected = "false";
    			 $( "#W4LevelSelect option:selected" ).each(
    				
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
                                <p>Dashboard Live- Daily Pivot &amp; SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKLIVESMAPV">

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

<c:forEach var="entry" items="${daycurrentpriceallbreakstocklist}" >
      
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

<c:import url="aftertbodybeforecriteria.txt" />


 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Pivot Level</p>
                            </div>
                             <div class="panel-body">

 <select id="W4LevelSelect" onchange="Currentdaysmapvselect()" multiple="multiple">
    <option   value="PP"  <c:if test="${cCurrentDaysma50pvPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${cCurrentDaysma50pvS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${cCurrentDaysma50pvS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${cCurrentDaysma50pvS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${cCurrentDaysma50pvS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${cCurrentDaysma50pvR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${cCurrentDaysma50pvR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${cCurrentDaysma50pvR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${cCurrentDaysma50pvR4 eq true}">
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
<input id="curwknse200check" type="checkbox"     
name="nse200"      
onchange = "Currentdaysmapvselect()" <c:if test="${cnsechecked eq true}">
checked
</c:if>>nse200     
<input id="curwknseothercheck" type="checkbox"     
name="others"     
onchange = "Currentdaysmapvselect()" <c:if test="${cotherchecked eq true}">
checked
</c:if>/>Others  
<br><br>


 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
Smadiff<input id="curdaysmapvsmslider" type="range" value="<c:out value="${csmadiffval}"/>" min="0" max="10" step="0.5"  onchange="Currentdaysmapvselect()" oninput="curdaysmapvsmval.value = curdaysmapvsmslider.value">
<output name="curdaysmapvsmOutputName" id="curdaysmapvsmval"><c:out value="${csmadiffval}"/></output> 
&nbsp;&nbsp;
pivotdiff<input id="curdaysmapvpivotslider" type="range" value="<c:out value="${cpivotdiffval}"/>" min="0" max="10" step="0.5" onchange="Currentdaysmapvselect()" oninput="curwksmpvval.value = curdaysmapvpivotslider.value">
<output name="curdaysmapvpivotsliderOutputName" id="curwksmpvval"><c:out value="${cpivotdiffval}"/></output>
<br>
</div>
</div>
</div>
</div>

<c:import url="aftercriteria.txt" />