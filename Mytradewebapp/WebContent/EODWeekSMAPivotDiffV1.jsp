<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function EPSdiffFormChange() {
          	
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
            var ST3checked = Leveloptionselectedpg3("ST3");
            var ST5checked = Leveloptionselectedpg3("ST5");
        	
        	
        	$.post(
                    "EPSdiffservlet", 
                    {EPSdiffnse200attr : $(EPSdiffnse200check).is(':checked'),
                     EPSdiffotherattr : $(EPSdiffothercheck).is(':checked'),
                     EPSdifftestsmaattr : $(EPSdifftestsma).is(':checked'),
                     EPSdifftestpivotattr : $(EPSdifftestpivot).is(':checked'),
                     EPSdiffSMAattr : smachecked,
                     EPSdiffPPattr : PPchecked,
                     EPSdiffS1attr : S1checked,
                     EPSdiffS2attr : S2checked,
                     EPSdiffS3attr : S3checked,
                     EPSdiffS4attr : S4checked,
                     EPSdiffR1attr : R1checked,
                     EPSdiffR2attr : R2checked,
                     EPSdiffR3attr : R3checked,
                     EPSdiffR4attr : R4checked,
                     EPSdiffST3attr : ST3checked,
                     EPSdiffST5attr : ST5checked,
                     EPSdiffrangemin : $("#EODEPSmindiffval").val(),
                     EPSdiffrangemax : $("#EODEPSmaxdiffval").val(),
                     EPSdiffstochk : $("#EODEPSStochKval").val(),
                     EPSdiffstochd : $("#EODEPSStochDval").val()
                     
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
                                <p>EOD- Weekly Pivot &amp; SMA Diff</p>
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
          <td>DayStochK</td>
        <td>DayStochD</td>
        
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${EODpivotsmadiff}" >
      
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

 <select id="W3LevelSelect" onchange="EPSdiffFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${epsdiffSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${epsdiffPP eq true}">
selected
</c:if>>Pivot PP</option>
    <option value="S1" <c:if test="${epsdiffS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${epsdiffS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${epsdiffS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${epsdiffS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${epsdiffR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${epsdiffR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${epsdiffR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${epsdiffR4 eq true}">
selected
</c:if>>Pivot R4</option>
<option value="ST3" <c:if test="${epsdiffST3 eq true}">
selected
</c:if>>Trend 3</option>
<option value="ST5" <c:if test="${epsdiffST5 eq true}">
selected
</c:if>>Trend 5</option>
</select>

  
                                                        
                             
                 <br><br>
                 </div>
                 </div>
                 
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">          
<input id="EPSdiffnse200check" type="checkbox"     
name="nse200"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffnse eq true}">
checked
</c:if>>nse200       
<input id="EPSdiffothercheck" type="checkbox"     
name="others"     
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffothers eq true}">
checked
</c:if>>Others <br>
<input id="EPSdifftestsma" type="checkbox"     
name="others"     
onchange = "EPSdiffFormChange()" <c:if test="${epsdifftestsma eq true}">
checked
</c:if>>TestSma <br>
<input id="EPSdifftestpivot" type="checkbox"     
name="others"     
onchange = "EPSdiffFormChange()" <c:if test="${epsdifftestpivot eq true}">
checked
</c:if>>TestPivot <br>
<br>



 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
MinPercent<input id="EODEPSmindiffslider" type="range" value="<c:out value="${epsdiffrangemin}"/>" min="-10" max="0" step="0.5"  onchange="EPSdiffFormChange()" oninput="EODEPSmindiffval.value =EODEPSmindiffslider.value">
<output name="EODEPSmindiffOutputName" id="EODEPSmindiffval"><c:out value="${epsdiffrangemin}"/></output> 
<br>
MaxPercent<input id="EODEPSmaxdiffslider" type="range" value="<c:out value="${epsdiffrangemax}"/>" min="0" max="10" step="0.5"  onchange="EPSdiffFormChange()" oninput="EODEPSmaxdiffval.value =EODEPSmaxdiffslider.value">
<output name="EODEPSmaxdiffOutputName" id="EODEPSmaxdiffval"><c:out value="${epsdiffrangemax}"/></output> 
<br>
StochK<input id="EODEPSStochKslider" type="range" value="<c:out value="${percentk}"/>" min="0" max="100" step="5"  onchange="EPSdiffFormChange()" oninput="EODEPSStochKval.value =EODEPSStochKslider.value">
<output name="EODEPSStochKOutputName" id="EODEPSStochKval"><c:out value="${percentk}"/></output> 
<br>
StochD<input id="EODEPSStochDslider" type="range" value="<c:out value="${percentd}"/>" min="0" max="100" step="5"  onchange="EPSdiffFormChange()" oninput="EODEPSStochDval.value =EODEPSStochDslider.value">
<output name="EODEPSStochDOutputName" id="EODEPSStochDval"><c:out value="${percentd}"/></output> 
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