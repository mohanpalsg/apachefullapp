<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function DayEPSdiffFormChange() {
          	
        	var smachecked=Leveloptionselectedpg3("SMA");
        	var PPchecked=Leveloptionselectedpg3("PP");
            var S1checked = Leveloptionselectedpg3("S1");
            var S2checked = Leveloptionselectedpg3("S2");
            var S3checked = Leveloptionselectedpg3("S3");
            var S4checked = Leveloptionselectedpg3("S4");
            var R1checked = Leveloptionselectedpg3("R1");
            var R2checked = Leveloptionselectedpg3("R2");
            var R3checked = Leveloptionselectedpg3("R3");
            var R4checked = Leveloptionselectedpg3("R4");
        	
        	
        	$.post(
                    "DayEPSdiffservlet", 
                    {DayEPSdiffnse200attr : $(DayEPSdiffnse200check).is(':checked'),
                     DayEPSdiffotherattr : $(DayEPSdiffothercheck).is(':checked'),
                     DayEPSdifftestsmaattr : $(DayEPSdifftestsma).is(':checked'),
                     DayEPSdifftestpivotattr : $(DayEPSdifftestpivot).is(':checked'),
                     DayEPSdiffSMAattr : smachecked,
                     DayEPSdiffPPattr : PPchecked,
                     DayEPSdiffS1attr : S1checked,
                     DayEPSdiffS2attr : S2checked,
                     DayEPSdiffS3attr : S3checked,
                     DayEPSdiffS4attr : S4checked,
                     DayEPSdiffR1attr : R1checked,
                     DayEPSdiffR2attr : R2checked,
                     DayEPSdiffR3attr : R3checked,
                     DayEPSdiffR4attr : R4checked,
                     DayEPSdiffrangemin : $("#EODDayEPSmindiffval").val(),
                     DayEPSdiffrangemax : $("#EODDayEPSmaxdiffval").val(),
                     DayEPSdiffstochk : $("#EODDayEPSStochKval").val(),
                     DayEPSdiffstochd : $("#EODDayEPSStochDval").val()
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 
        	  
        }
        
        $('#dataTables-WEEKEODSMAPVDIFF').DataTable({
            responsive: true
    });
        
        $('#W3LevelSelect').multiselect();
     	 
     	 function Leveloptionselectedpg3 (level)
     		{
     			
     			var selected = "false";
     			 $( "#W3LevelSelect option:selected" ).each(
     				
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
                                <p>EOD- Daily Pivot &amp; SMA Diff</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKEODSMAPVDIFF">
       
<thead>

<tr>
       <td>Stocksymbol</td>
       <td>Price level</td>
       <td>LevelValue</td>
       <td>Prevclose</td>
       <td>Currclose</td>
       <td>diff</td>
        <td>StochK</td>
        <td>StochD</td>
       
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${EODdaypivotsmadiff}" >
      
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

 <select id="W3LevelSelect" onchange="DayEPSdiffFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${DayepsdiffSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${DayepsdiffPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${DayepsdiffS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${DayepsdiffS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${DayepsdiffS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${DayepsdiffS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${DayepsdiffR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${DayepsdiffR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${DayepsdiffR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${DayepsdiffR4 eq true}">
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
<input id="DayEPSdiffnse200check" type="checkbox"     
name="nse200"      
onchange = "DayEPSdiffFormChange()" <c:if test="${Dayepsdiffnse eq true}">
checked
</c:if>>nse200       
<input id="DayEPSdiffothercheck" type="checkbox"     
name="others"     
onchange = "DayEPSdiffFormChange()" <c:if test="${Dayepsdiffothers eq true}">
checked
</c:if>>Others <br>

<input id="DayEPSdifftestsma" type="checkbox"     
name="others"     
onchange = "DayEPSdiffFormChange()" <c:if test="${Dayepsdifftestsma eq true}">
checked
</c:if>>TestSma <br>
<input id="DayEPSdifftestpivot" type="checkbox"     
name="others"     
onchange = "DayEPSdiffFormChange()" <c:if test="${Dayepsdifftestpivot eq true}">
checked
</c:if>>TestPivot <br>
<br>

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
MinPercent<input id="EODDayEPSmindiffslider" type="range" value="<c:out value="${Dayepsdiffrangemin}"/>" min="-10" max="0" step="0.5"  onchange="DayEPSdiffFormChange()" oninput="EODDayEPSmindiffval.value =EODDayEPSmindiffslider.value">
<output name="EODDayEPSmindiffOutputName" id="EODDayEPSmindiffval"><c:out value="${Dayepsdiffrangemin}"/></output> 
<br>
MaxPercent<input id="EODDayEPSmaxdiffslider" type="range" value="<c:out value="${Dayepsdiffrangemax}"/>" min="0" max="10" step="0.5"  onchange="DayEPSdiffFormChange()" oninput="EODDayEPSmaxdiffval.value =EODDayEPSmaxdiffslider.value">
<output name="EODDayEPSmaxdiffOutputName" id="EODDayEPSmaxdiffval"><c:out value="${Dayepsdiffrangemax}"/></output> 
<br>

StochK<input id="EODDayEPSStochKslider" type="range" value="<c:out value="${percentk}"/>" min="0" max="100" step="5"  onchange="DayEPSdiffFormChange()" oninput="EODDayEPSStochKval.value =EODDayEPSStochKslider.value">
<output name="EODDayEPSStochKOutputName" id="EODDayEPSStochKval"><c:out value="${percentk}"/></output> 
<br>
StochD<input id="EODDayEPSStochDslider" type="range" value="<c:out value="${percentd}"/>" min="0" max="100" step="5"  onchange="DayEPSdiffFormChange()" oninput="EODDayEPSStochDval.value =EODDayEPSStochDslider.value">
<output name="EODDayEPSStochDOutputName" id="EODDayEPSStochDval"><c:out value="${percentd}"/></output> 
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