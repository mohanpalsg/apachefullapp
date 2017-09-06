<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function CPSdiffFormChange() {
          	
        	var smachecked=Leveloptionselectedpg6("SMA");
        	var PPchecked=Leveloptionselectedpg6("PP");
            var S1checked = Leveloptionselectedpg6("S1");
            var S2checked = Leveloptionselectedpg6("S2");
            var S3checked = Leveloptionselectedpg6("S3");
            var S4checked = Leveloptionselectedpg6("S4");
            var R1checked = Leveloptionselectedpg6("R1");
            var R2checked = Leveloptionselectedpg6("R2");
            var R3checked = Leveloptionselectedpg6("R3");
            var R4checked = Leveloptionselectedpg6("R4");
        	
        	
        	$.post(
                    "CPSdiffservlet", 
                    {CPSdiffnse200attr : $(CPSdiffnse200check).is(':checked'),
                     CPSdiffotherattr : $(CPSdiffothercheck).is(':checked'),
                     CPSdifftestsmaattr : $(CPSdifftestsma).is(':checked'),
                     CPSdifftestpivotattr : $(CPSdifftestpivot).is(':checked'),
                     CPSdiffSMAattr : smachecked,
                     CPSdiffPPattr : PPchecked,
                     CPSdiffS1attr : S1checked,
                     CPSdiffS2attr : S2checked,
                     CPSdiffS3attr : S3checked,
                     CPSdiffS4attr : S4checked,
                     CPSdiffR1attr : R1checked,
                     CPSdiffR2attr : R2checked,
                     CPSdiffR3attr : R3checked,
                     CPSdiffR4attr : R4checked,
                     CPSdiffrangemin : $("#CurrentCPSmindiffval").val(),
                     CPSdiffrangemax : $("#CurrentCPSmaxdiffval").val(),
                     CPSdiffstochk : $("#EODCPSStochKval").val(),
                     CPSdiffstochd : $("#EODCPSStochDval").val()
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 
        	  
        }
    	
  	  $('#dataTables-WEEKSMAPVdiff').DataTable({
            responsive: true
    });
  	  
  	 $('#W6LevelSelect').multiselect();
 	 
 	 function Leveloptionselectedpg6 (level)
 		{
 			
 			var selected = "false";
 			 $( "#W6LevelSelect option:selected" ).each(
 				
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
                                <p>Live- Weekly Pivot &amp; SMA Diff</p>
                                                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKSMAPVdiff">



<thead>
<tr>
       <td>Stock</td>
       <td>P-level</td>
       <td>L-Val</td>
       <td>P-close</td>
       <td>L-price</td>
       <td>diff</td>
         <td>DayStochK</td>
        <td>DayStochD</td>
      
    </tr>
    </thead>
    <tbody>
<c:forEach var="entry" items="${currentpricesmadiff}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getPricelevel()}</td>
<td>${entry.value.getPriceval()}</td>
<td>${entry.value.getPrevclose()}</td>
<td>${entry.value.getCurrclose()}</td>
<td>${entry.value.getPricediff()}</td>
<td>${entry.value.getStochk()}</td>
<td>${entry.value.getStochd()}</td>       
</tr>
    
</c:forEach>

</tbody>


<c:import url="aftertbodybeforecriteria.txt" />


 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Pivot Level</p>
                            </div>
                             <div class="panel-body">

 <select id="W6LevelSelect" onchange="CPSdiffFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${cpsdiffSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${cpsdiffPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${cpsdiffS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${cpsdiffS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${cpsdiffS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${cpsdiffS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${cpsdiffR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${cpsdiffR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${cpsdiffR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${cpsdiffR4 eq true}">
selected
</c:if>>Pivot R4</option>
</select>
</div>
</div>
  
                                                        
<br>

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">                                

<input id="CPSdiffnse200check" type="checkbox"     
name="nse200"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffnse eq true}">
checked
</c:if>>nse200       
<input id="CPSdiffothercheck" type="checkbox"     
name="others"     
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffothers eq true}">
checked
</c:if>>Others <br>
<input id="CPSdifftestsma" type="checkbox"     
name="others"     
onchange = "CPSdiffFormChange()" <c:if test="${cpsdifftestsma eq true}">
checked
</c:if>>TestSma <br>
<input id="CPSdifftestpivot" type="checkbox"     
name="others"     
onchange = "CPSdiffFormChange()" <c:if test="${cpsdifftestpivot eq true}">
checked
</c:if>>TestPivot <br>
<br>


<br>

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
MinPercent<input id="CurrentCPSmindiffslider" type="range" value="<c:out value="${cpsdiffrangemin}"/>" min="-10" max="0" step="0.5"  onchange="CPSdiffFormChange()" oninput="CurrentCPSmindiffval.value =CurrentCPSmindiffslider.value">
<output name="CurrentCPSmindiffOutputName" id="CurrentCPSmindiffval"><c:out value="${cpsdiffrangemin}"/></output> 
<br>
MaxPercent<input id="CurrentCPSmaxdiffslider" type="range" value="<c:out value="${cpsdiffrangemax}"/>" min="0" max="10" step="0.5"  onchange="CPSdiffFormChange()" oninput="CurrentCPSmaxdiffval.value =CurrentCPSmaxdiffslider.value">
<output name="CurrentCPSmaxdiffOutputName" id="CurrentCPSmaxdiffval"><c:out value="${cpsdiffrangemax}"/></output> 

<br>
StochK<input id="EODCPSStochKslider" type="range" value="<c:out value="${percentk}"/>" min="0" max="100" step="5"  onchange="CPSdiffFormChange()" oninput="EODCPSStochKval.value =EODCPSStochKslider.value">
<output name="EODCPSStochKOutputName" id="EODCPSStochKval"><c:out value="${percentk}"/></output> 
<br>
StochD<input id="EODCPSStochDslider" type="range" value="<c:out value="${percentd}"/>" min="0" max="100" step="5"  onchange="CPSdiffFormChange()" oninput="EODCPSStochDval.value =EODCPSStochDslider.value">
<output name="EODCPSStochDOutputName" id="EODCPSStochDval"><c:out value="${percentd}"/></output> 
<br>


</div>
</div>
</div>
</div>
  
<c:import url="aftercriteria.txt" />

    