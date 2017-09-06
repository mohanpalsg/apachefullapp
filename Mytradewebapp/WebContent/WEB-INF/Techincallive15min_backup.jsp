<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function OthertechFormChange() {
          	
        	
         
        	
        	$.post(
                    "Technicals15min", 
                    {
                     commontechRSPrangeattr : $("#COMMONTECHRSIRANGE").val(),
                     commontechWRPrangeattr : $("#COMMONTECHWRRANGE").val(),
                     commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                     commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
                     testsmattr : $(Techlive15testsma).is(':checked'),
                     testpvattr : $(Techlive15testpv).is(':checked'),
                     breaksmattr : $(Techlive15testbreaksma).is(':checked'),
                     breakpvattr : $(Techlive15breakpv).is(':checked'),
                     commontechlowdiffattr : $("#COMMONTECHSMADIFF").val(),
                     commontechhighdiffattr : $("#COMMONTECHPVTDIFF").val()

                     




                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        
function OthertechtimeFormChange() {
          	
        	
	 
        	
        	$.post(
                    "Technicals15min", 
                    {
                     commontechRSPrangeattr : $("#COMMONTECHRSIRANGE").val(),
                     commontechWRPrangeattr : $("#COMMONTECHWRRANGE").val(),
                     commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                     commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                     chartinterval : $( "#PeriodSelect option:selected" ).val(),
                     testsmattr : $(Techlive15testsma).is(':checked'),
                     testpvattr : $(Techlive15testpv).is(':checked'),
                     breaksmattr : $(Techlive15testbreaksma).is(':checked'),
                     breakpvattr : $(Techlive15breakpv).is(':checked'),
                     commontechlowdiffattr : $("#COMMONTECHSMADIFF").val(),
                     commontechhighdiffattr : $("#COMMONTECHPVTDIFF").val()

                     




                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	$('#content').html("Loading Data.....");
        } 
        
        
        
        $('#dataTables-techlive15').DataTable({
            responsive: true
    });
        
       
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Technical Indicators Live </p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-techlive15">
       
<thead>

<tr>
       <td>Symbol</td>
       <td>O-Price</td>
       <td>C-Price</td>
      <td>S50</td>
       <td>Level</td>
       <td>L-value</td>
       <td>Sdiff</td>
       <td>Pdiff</td> 
       <td>SK</td>
       <td>SD</td>
       <td>WPR</td>
       <td>Trendup</td>
       <td>Trenddown</td>
       <td>Trend</td>
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getOpenprice()}</td>
<td>${entry.value.getCloseprice()}</td>
<td>${entry.value.getSma50()}</td>
<td>${entry.value.getLevel()}</td>
<td>${entry.value.getLevelvalue()}</td>
<td>${entry.value.getSmadiff()}</td>
<td>${entry.value.getPivotdiff()}</td> 
<td>${entry.value.getStochk()}</td>
<td>${entry.value.getStochd()}</td>
<td>${entry.value.getWpr()}</td>
<td>${entry.value.getDownvalue()}</td>
<td>${entry.value.getUpvalue()}</td>
<td>${entry.value.getTrend()}</td>


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
                                <p>Candle Interval</p>
                            </div>
                             <div class="panel-body">
                             
 <select id="PeriodSelect" onchange="OthertechtimeFormChange()" >
 
 <option value="5"  <c:if test="${Minselect eq 5}">
selected
</c:if>>5Min</option>
 

 <option value="15"  <c:if test="${Minselect eq 15}">
selected
</c:if>>15Min</option>

 <option value="30"  <c:if test="${Minselect eq 30}">
selected
</c:if>>30Min</option>

<option value="60"  <c:if test="${Minselect eq 60}">
selected
</c:if>>1Hour</option>

<option value="120"  <c:if test="${Minselect eq 120}">
selected
</c:if>>2Hour</option>



</select>

</div>
</div>

<div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Price Test</p>
                            </div>
                             <div class="panel-body">      
<input id="Techlive15testsma" type="checkbox"     
name="Techlive15testsma" onchange="OthertechFormChange()"     
 <c:if test="${testsmachecked eq true}">
checked
</c:if>>TestSMA
<input id="Techlive15testpv" type="checkbox"     
name="Techlive15testpv" onchange="OthertechFormChange()"     
 <c:if test="${testpvchecked eq true}">
checked
</c:if>>TestPivot
<br><br>

<input id="Techlive15testbreaksma" type="checkbox"     
name="Techlive15breaksma" onchange="OthertechFormChange()"     
 <c:if test="${breaksmachecked eq true}">
checked
</c:if>>BreakSMA
<input id="Techlive15breakpv" type="checkbox"     
name="Techlive15breakpv" onchange="OthertechFormChange()"     
 <c:if test="${breakpvchecked eq true}">
checked
</c:if>>BreakPivot


 
    
</div>
</div>
                
                 
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Range Filters</p>
                            </div>
                             <div class="panel-body">          

          
RSI-Range<input id="COMMONTECHRSIRANGEslider" type="range" value="<c:out value="${Commontechrsirange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHRSIRANGE.value =COMMONTECHRSIRANGEslider.value">
<output name="COMMONTECHRSIRANGEoname" id="COMMONTECHRSIRANGE"><c:out value="${Commontechrsirange}"/></output> 
<br>
WPR-Range<input id="COMMONTECHWRRANGEslider" type="range" value="<c:out value="${Commontechwprrange}"/>" min="-100" max="0" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHWRRANGE.value =COMMONTECHWRRANGEslider.value">
<output name="COMMONTECHWRRANGEoname" id="COMMONTECHWRRANGE"><c:out value="${Commontechwprrange}"/></output> 
<br>
Stock-k<input id="COMMONTECHSKRANGEslider" type="range" value="<c:out value="${Commontechskrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSKRANGE.value =COMMONTECHSKRANGEslider.value">
<output name="COMMONTECHSKRANGEoname" id="COMMONTECHSKRANGE"><c:out value="${Commontechskrange}"/></output> 
<br>
Stock-D<input id="COMMONTECHSDRANGEslider" type="range" value="<c:out value="${Commontechsdrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSDRANGE.value =COMMONTECHSDRANGEslider.value">
<output name="COMMONTECHSDRANGEoname" id="COMMONTECHSDRANGE"><c:out value="${Commontechsdrange}"/></output> 
<br>

Low-diff<input id="COMMONTECHSMADIFFslider" type="range" value="<c:out value="${Commontechsmadiff}"/>" min="-5" max="0" step="0.25"  onchange="OthertechFormChange()" oninput="COMMONTECHSMADIFF.value =COMMONTECHSMADIFFslider.value">
<output name="COMMONTECHSMADIFFoname" id="COMMONTECHSMADIFF"><c:out value="${Commontechsmadiff}"/></output> 
<br>

high-diff<input id="COMMONTECHPVTDIFFslider" type="range" value="<c:out value="${Commontechpvtdiff}"/>" min="0" max="5" step="0.25"  onchange="OthertechFormChange()" oninput="COMMONTECHPVTDIFF.value =COMMONTECHPVTDIFFslider.value">
<output name="COMMONTECHPVTDIFFoname" id="COMMONTECHPVTDIFF"><c:out value="${Commontechpvtdiff}"/></output> 
<br>



</div>
</div>
</div>
</div>
</div>

        </div>
</div>
</div>