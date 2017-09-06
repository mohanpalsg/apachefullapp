<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>

<body>
<script type="text/javascript">
        function CPSdiffFormChange() {
          	
        	
        	
        	$.post(
                    "CPSdiffservlet", 
                    {CPSdiffnse200attr : $(CPSdiffnse200check).is(':checked'),
                     CPSdiffotherattr : $(CPSdiffothercheck).is(':checked'),
                     CPSdiffSMAattr : $(CPSdiffSMAcheck).is(':checked'),
                     CPSdiffPPattr : $(CPSdiffPPcheck).is(':checked'),
                     CPSdiffS1attr : $(CPSdiffS1check).is(':checked'),
                     CPSdiffS2attr : $(CPSdiffS2check).is(':checked'),
                     CPSdiffS3attr : $(CPSdiffS3check).is(':checked'),
                     CPSdiffS4attr : $(CPSdiffS4check).is(':checked'),
                     CPSdiffR1attr : $(CPSdiffR1check).is(':checked'),
                     CPSdiffR2attr : $(CPSdiffR2check).is(':checked'),
                     CPSdiffR3attr : $(CPSdiffR3check).is(':checked'),
                     CPSdiffR4attr : $(CPSdiffR4check).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 
        	  
        }
    	
  	  $('#dataTables-WEEKSMAPVdiff').DataTable({
            responsive: true
    });
</script>



       
<div class="panel panel-default">
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
       
</tr>
    
</c:forEach>

</tbody>


<c:import url="aftertbodybeforecriteria.txt" />

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

<p>Level</p>

<input id="CPSdiffSMAcheck" type="checkbox"     
name="CPSdiffSMAcheck"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffSMA eq true}">
checked
</c:if>>SMA50
<input id="CPSdiffPPcheck" type="checkbox"     
name="CPSdiffPPcheck"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffPP eq true}">
checked
</c:if>>Pivot-PP 
<input id="CPSdiffS1check" type="checkbox"     
name="CPSdiffS1check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffS1 eq true}">
checked
</c:if>>Pivot-S1
<input id="CPSdiffS2check" type="checkbox"     
name="CPSdiffS2check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffS2 eq true}">
checked
</c:if>>Pivot-S2
<input id="CPSdiffS3check" type="checkbox"     
name="CPSdiffS3check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffS3 eq true}">
checked
</c:if>>Pivot-S3
<input id="CPSdiffS4check" type="checkbox"     
name="CPSdiffS4check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffS4 eq true}">
checked
</c:if>>Pivot-S4
<input id="CPSdiffR1check" type="checkbox"     
name="CPSdiffR1check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffR1 eq true}">
checked
</c:if>>Pivot-R1
<input id="CPSdiffR2check" type="checkbox"     
name="CPSdiffR2check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffR2 eq true}">
checked
</c:if>>Pivot-R2
<input id="CPSdiffR3check" type="checkbox"     
name="CPSdiffR3check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffR3 eq true}">
checked
</c:if>>Pivot-R3
<input id="CPSdiffR4check" type="checkbox"     
name="CPSdiffR4check"      
onchange = "CPSdiffFormChange()" <c:if test="${cpsdiffR4 eq true}">
checked
</c:if>>Pivot-R4
<br>

<c:import url="aftercriteria.txt" />

    