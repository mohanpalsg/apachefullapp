<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function EPSdiffFormChange() {
          	
        	
        	
        	$.post(
                    "EPSdiffservlet", 
                    {EPSdiffnse200attr : $(EPSdiffnse200check).is(':checked'),
                     EPSdiffotherattr : $(EPSdiffothercheck).is(':checked'),
                     EPSdiffSMAattr : $(EPSdiffSMAcheck).is(':checked'),
                     EPSdiffPPattr : $(EPSdiffPPcheck).is(':checked'),
                     EPSdiffS1attr : $(EPSdiffS1check).is(':checked'),
                     EPSdiffS2attr : $(EPSdiffS2check).is(':checked'),
                     EPSdiffS3attr : $(EPSdiffS3check).is(':checked'),
                     EPSdiffS4attr : $(EPSdiffS4check).is(':checked'),
                     EPSdiffR1attr : $(EPSdiffR1check).is(':checked'),
                     EPSdiffR2attr : $(EPSdiffR2check).is(':checked'),
                     EPSdiffR3attr : $(EPSdiffR3check).is(':checked'),
                     EPSdiffR4attr : $(EPSdiffR4check).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 
        	  
        }
        
        $('#dataTables-WEEKEODSMAPVDIFF').DataTable({
            responsive: true
    });
        
</script>





<div class="panel panel-default">
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

<p>Level</p>

<input id="EPSdiffSMAcheck" type="checkbox"     
name="EPSdiffSMAcheck"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffSMA eq true}">
checked
</c:if>>SMA50
<input id="EPSdiffPPcheck" type="checkbox"     
name="EPSdiffPPcheck"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffPP eq true}">
checked
</c:if>>Pivot-PP 
<input id="EPSdiffS1check" type="checkbox"     
name="EPSdiffS1check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="EPSdiffS2check" type="checkbox"     
name="EPSdiffS2check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="EPSdiffS3check" type="checkbox"     
name="EPSdiffS3check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="EPSdiffS4check" type="checkbox"     
name="EPSdiffS4check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffS4 eq true}">
checked
</c:if>>Pivot-S4
<input id="EPSdiffR1check" type="checkbox"     
name="EPSdiffR1check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="EPSdiffR2check" type="checkbox"     
name="EPSdiffR2check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="EPSdiffR3check" type="checkbox"     
name="EPSdiffR3check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="EPSdiffR4check" type="checkbox"     
name="EPSdiffR4check"      
onchange = "EPSdiffFormChange()" <c:if test="${epsdiffR4 eq true}">
checked
</c:if>>Pivot-R4   

</div>
</div>
</div>
        </div>
</div>
</div>