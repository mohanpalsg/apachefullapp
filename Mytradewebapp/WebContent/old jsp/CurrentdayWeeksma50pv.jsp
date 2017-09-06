<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function Currentweeksmapvselect() {
        	
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
                    "DayweeksmapvServlet", 
                    {cnse200attr : $(curwknse200check).is(':checked'),cotherattr : $(curwknseothercheck).is(':checked'),cpivotdiffattr: $("#curwksmpvval").val(), csmadiffattr : $("#curweeksmapvsmval").val(),
                    	cppcheckattr: $(CurrentWeeksma50pvPPcheck).is(':checked'),
                    	cs1checkattr: $(CurrentWeeksma50pvS1check).is(':checked'),
                    	cs2checkattr: $(CurrentWeeksma50pvS2check).is(':checked'),
                    	cs3checkattr: $(CurrentWeeksma50pvS3check).is(':checked'),
                    	cs4checkattr: $(CurrentWeeksma50pvS4check).is(':checked'),
                    	cr1checkattr: $(CurrentWeeksma50pvR1check).is(':checked'),
                    	cr2checkattr: $(CurrentWeeksma50pvR2check).is(':checked'),
                    	cr3checkattr: $(CurrentWeeksma50pvR3check).is(':checked'),
                    	cr4checkattr: $(CurrentWeeksma50pvR4check).is(':checked')
                    	
                    
                    }, //meaasge you want to send
                    
                    function(result) {
                    	 $('#content').html(result);
                });
        	
        	 
        }
        $('#dataTables-WEEKLIVESMAPV').DataTable({
            responsive: true
    });
</script>



<div class="panel panel-default">
                            <div class="panel-heading">
                                <p>Dashboard Live- Weekly Pivot &amp; SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKLIVESMAPV">

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

<c:forEach var="entry" items="${currentpriceallbreakstocklist}" >
      
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

<input id="curwknse200check" type="checkbox"     
name="nse200"      
onchange = "Currentweeksmapvselect()" <c:if test="${cnsechecked eq true}">
checked
</c:if>>nse200     
<input id="curwknseothercheck" type="checkbox"     
name="others"     
onchange = "Currentweeksmapvselect()" <c:if test="${cotherchecked eq true}">
checked
</c:if>/>Others  
&nbsp;&nbsp;&nbsp;&nbsp;
Smadiff<input id="curweeksmapvsmslider" type="range" value="<c:out value="${csmadiffval}"/>" min="0" max="10" step="0.5"  onchange="Currentweeksmapvselect()" oninput="curweeksmapvsmval.value = curweeksmapvsmslider.value">
<output name="curweeksmapvsmOutputName" id="curweeksmapvsmval"><c:out value="${csmadiffval}"/></output> 
&nbsp;&nbsp;
pivotdiff<input id="curweeksmapvpivotslider" type="range" value="<c:out value="${cpivotdiffval}"/>" min="0" max="10" step="0.5" onchange="Currentweeksmapvselect()" oninput="curwksmpvval.value = curweeksmapvpivotslider.value">
<output name="curweeksmapvpivotsliderOutputName" id="curwksmpvval"><c:out value="${cpivotdiffval}"/></output>
<br>
<p> Pivot Level </p>
<input id="CurrentWeeksma50pvPPcheck" type="checkbox"     
name="CurrentWeeksma50pvPPcheck"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvPP eq true}">
checked
</c:if>>Pivot-PP 
<input id="CurrentWeeksma50pvS1check" type="checkbox"     
name="CurrentWeeksma50pvS1check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="CurrentWeeksma50pvS2check" type="checkbox"     
name="CurrentWeeksma50pvS2check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="CurrentWeeksma50pvS3check" type="checkbox"     
name="CurrentWeeksma50pvS3check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="CurrentWeeksma50pvS4check" type="checkbox"     
name="CurrentWeeksma50pvS4check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvS4 eq true}">
checked
</c:if>>Pivot-S4
<input id="CurrentWeeksma50pvR1check" type="checkbox"     
name="CurrentWeeksma50pvR1check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="CurrentWeeksma50pvR2check" type="checkbox"     
name="CurrentWeeksma50pvR2check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="CurrentWeeksma50pvR3check" type="checkbox"     
name="CurrentWeeksma50pvR3check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="CurrentWeeksma50pvR4check" type="checkbox"     
name="CurrentWeeksma50pvR4check"      
onchange = "Currentweeksmapvselect()" <c:if test="${cCurrentWeeksma50pvR4 eq true}">
checked
</c:if>>Pivot-R4

<c:import url="aftercriteria.txt" />