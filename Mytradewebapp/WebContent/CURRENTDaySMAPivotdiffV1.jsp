<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function DayCPSdiffFormChange() {
          	
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
                    "DayCPSdiffservlet", 
                    {DayCPSdiffnse200attr : $(DayCPSdiffnse200check).is(':checked'),
                     DayCPSdiffotherattr : $(DayCPSdiffothercheck).is(':checked'),
                     DayCPSdifftestsmaattr : $(DayCPSdifftestsma).is(':checked'),
                     DayCPSdifftestpivotattr : $(DayCPSdifftestpivot).is(':checked'),
                     DayCPSdiffSMAattr : smachecked,
                     DayCPSdiffPPattr : PPchecked,
                     DayCPSdiffS1attr : S1checked,
                     DayCPSdiffS2attr : S2checked,
                     DayCPSdiffS3attr : S3checked,
                     DayCPSdiffS4attr : S4checked,
                     DayCPSdiffR1attr : R1checked,
                     DayCPSdiffR2attr : R2checked,
                     DayCPSdiffR3attr : R3checked,
                     DayCPSdiffR4attr : R4checked,
                     DayCPSdiffrangemin : $("#CurrentDayCPSmindiffval").val(),
                     DayCPSdiffrangemax : $("#CurrentDayCPSmaxdiffval").val(),
                     DayCPSdiffstochk : $("#EODDayCPSStochKval").val(),
                     DayCPSdiffstochd : $("#EODDayCPSStochDval").val()
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
                                <p>Live- Daily Pivot &amp; SMA Diff</p>
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
       <td>StochK</td>
        <td>StochD</td>
      
    </tr>
    </thead>
    <tbody>
<c:forEach var="entry" items="${daycurrentpricesmadiff}" >
      
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

 <select id="W6LevelSelect" onchange="DayCPSdiffFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${DaycpsdiffSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${DaycpsdiffPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${DaycpsdiffS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${DaycpsdiffS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${DaycpsdiffS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${DaycpsdiffS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${DaycpsdiffR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${DaycpsdiffR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${DaycpsdiffR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${DaycpsdiffR4 eq true}">
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

<input id="DayCPSdiffnse200check" type="checkbox"     
name="nse200"      
onchange = "DayCPSdiffFormChange()" <c:if test="${Daycpsdiffnse eq true}">
checked
</c:if>>nse200       
<input id="DayCPSdiffothercheck" type="checkbox"     
name="others"     
onchange = "DayCPSdiffFormChange()" <c:if test="${Daycpsdiffothers eq true}">
checked
</c:if>>Others <br>

<input id="DayCPSdifftestsma" type="checkbox"     
name="others"     
onchange = "DayCPSdiffFormChange()" <c:if test="${Daycpsdifftestsma eq true}">
checked
</c:if>>TestSma <br>
<input id="DayCPSdifftestpivot" type="checkbox"     
name="others"     
onchange = "DayCPSdiffFormChange()" <c:if test="${Daycpsdifftestpivot eq true}">
checked
</c:if>>TestPivot <br>
<br>

<br>

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
MinPercent<input id="CurrentDayCPSmindiffslider" type="range" value="<c:out value="${Daycpsdiffrangemin}"/>" min="-10" max="0" step="0.5"  onchange="DayCPSdiffFormChange()" oninput="CurrentDayCPSmindiffval.value =CurrentDayCPSmindiffslider.value">
<output name="CurrentDayCPSmindiffOutputName" id="CurrentDayCPSmindiffval"><c:out value="${Daycpsdiffrangemin}"/></output> 
<br>
MaxPercent<input id="CurrentDayCPSmaxdiffslider" type="range" value="<c:out value="${Daycpsdiffrangemax}"/>" min="0" max="10" step="0.5"  onchange="DayCPSdiffFormChange()" oninput="CurrentDayCPSmaxdiffval.value =CurrentDayCPSmaxdiffslider.value">
<output name="CurrentDayCPSmaxdiffOutputName" id="CurrentDayCPSmaxdiffval"><c:out value="${Daycpsdiffrangemax}"/></output> 
<br>
StochK<input id="EODDayCPSStochKslider" type="range" value="<c:out value="${percentk}"/>" min="0" max="100" step="5"  onchange="DayCPSdiffFormChange()" oninput="EODDayCPSStochKval.value =EODDayCPSStochKslider.value">
<output name="EODDayCPSStochKOutputName" id="EODDayCPSStochKval"><c:out value="${percentk}"/></output> 
<br>
StochD<input id="EODDayCPSStochDslider" type="range" value="<c:out value="${percentd}"/>" min="0" max="100" step="5"  onchange="DayCPSdiffFormChange()" oninput="EODDayCPSStochDval.value =EODDayCPSStochDslider.value">
<output name="EODDayCPSStochDOutputName" id="EODDayCPSStochDval"><c:out value="${percentd}"/></output> 
<br>


</div>
</div>
</div>
</div>
  
<c:import url="aftercriteria.txt" />

    