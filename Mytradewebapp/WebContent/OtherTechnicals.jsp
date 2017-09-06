<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">


        function OthertechFormChange() {
          	
        	
        	
        	
        	$.post(
                    "CommonTechnicalsServlet", 
                    {Commontechnse200attr : $(Commontechnse200check).is(':checked'),
                     Commontechotherattr : $(Commontechothercheck).is(':checked'),
                     commontechDWRPattr : $("#COMMONTECHDWRPERIOD").val(),
                     commontechDRSIPattr : $("#COMMONTECHDRSIPERIOD").val(),
                     commontechWWRPattr : $("#COMMONTECHWWRPERIOD").val(),
                     commontechWRSPIattr : $("#COMMONTECHWRSIPERIOD").val(),
                     commontechRSPrangeattr : $("#COMMONTECHRSIRANGE").val(),
                     commontechWRPrangeattr : $("#COMMONTECHWRRANGE").val(),
                     commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                     commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                     commontechprevzeroattr : $(Commontechprevkzerocheck).is(':checked'),
                     commontechuptrendattr :  $(Commontechuptrendcheck).is(':checked'),
                     commontechweekprevzeroattr : $(Commontechweekprevkzerocheck).is(':checked'),
                     commontechweekuptrendattr :  $(Commontechweekuptrendcheck).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        function OthertechFormreloadChange() {
          	
        	
        	
        	
        	$.post(
                    "CommonTechnicalsServlet", 
                    {Commontechnse200attr : $(Commontechnse200check).is(':checked'),
                     Commontechotherattr : $(Commontechothercheck).is(':checked'),
                     commontechDWRPattr : $("#COMMONTECHDWRPERIOD").val(),
                     commontechDRSIPattr : $("#COMMONTECHDRSIPERIOD").val(),
                     commontechWWRPattr : $("#COMMONTECHWWRPERIOD").val(),
                     commontechWRSPIattr : $("#COMMONTECHWRSIPERIOD").val(),
                     commontechRSPrangeattr : $("#COMMONTECHRSIRANGE").val(),
                     commontechWRPrangeattr : $("#COMMONTECHWRRANGE").val(),
                     commontechskrangeattr : $("#COMMONTECHSKRANGE").val(),
                     commontechsdrangeattr : $("#COMMONTECHSDRANGE").val(),
                     commontechprevzeroattr : $(Commontechprevkzerocheck).is(':checked'),
                     commontechuptrendattr :  $(Commontechuptrendcheck).is(':checked'),
                     commontechweekprevzeroattr : $(Commontechweekprevkzerocheck).is(':checked'),
                     commontechweekuptrendattr :  $(Commontechweekuptrendcheck).is(':checked')
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	 $('#content').html("<div>Loading Data... Updating Config</div>");
        	  
        }
        
        
        
        $('#dataTables-Commontech').DataTable({
            responsive: true
    });
        
        
     	 
</script>





<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Common Technical Indicators</p>
                                
                                <br>
                                
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-Commontech">
       
<thead>

<tr>
       <td>Stocksymbol</td>
       <td>Day-WPR</td>
       <td>Day-RSI</td>
       <td>Week-WPR</td>
       <td>Week-RSI</td>
       <td>Day-PrevK</td>
       <td>Day-Prevd</td>
        <td>Day-CurrK</td>
         <td>Day-CurrD</td>
         <td>Week-PrevK</td>
         <td>Week-Prevd</td>
         <td>Week-K</td>
         <td>Week-D</td>
       
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<td>${entry.value.getDaywilliamsr()}</td>
<td>${entry.value.getDayrsi()}</td>
<td>${entry.value.getWeekwilliamsr()}</td>
<td>${entry.value.getWeekrsi()}</td>
<td>${entry.value.getPrevdayk()}</td>
<td>${entry.value.getPrevdayd()}</td>
<td>${entry.value.getDayk()}</td>
<td>${entry.value.getDayd()}</td>
<td>${entry.value.getWeekprevk()}</td>
<td>${entry.value.getWeekprevd()}</td>
<td>${entry.value.getWeekk()}</td>
<td>${entry.value.getWeekd()}</td>

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
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">          
<input id="Commontechnse200check" type="checkbox"     
name="nse200"      
onchange = "OthertechFormChange()" <c:if test="${Commontechnse eq true}">
checked
</c:if>>nse200       
<input id="Commontechothercheck" type="checkbox"     
name="others"     
onchange = "OthertechFormChange()" <c:if test="${Commontechothers eq true}">
checked
</c:if>>Others <br>

<input id="Commontechprevkzerocheck" type="checkbox"     
name="others"     
onchange = "OthertechFormChange()" <c:if test="${Commontechprevkzero eq true}">
checked
</c:if>>Day-PrevK=0 

<input id="Commontechuptrendcheck" type="checkbox"     
name="others"     
onchange = "OthertechFormChange()" <c:if test="${Commontechuptrend eq true}">
checked
</c:if>>Raising-day D <br>
<br>


<input id="Commontechweekprevkzerocheck" type="checkbox"     
name="others"     
onchange = "OthertechFormChange()" <c:if test="${Commontechweekprevkzero eq true}">
checked
</c:if>>Week PrevK=0 

<input id="Commontechweekuptrendcheck" type="checkbox"     
name="others"     
onchange = "OthertechFormChange()" <c:if test="${Commontechweekuptrend eq true}">
checked
</c:if>>Raising-day Week <br>
<br>


          
RSI-Range<input id="COMMONTECHRSIRANGEslider" type="range" value="<c:out value="${Commontechrsirange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHRSIRANGE.value =COMMONTECHRSIRANGEslider.value">
<output name="COMMONTECHRSIRANGEoname" id="COMMONTECHRSIRANGE"><c:out value="${Commontechrsirange}"/></output> 
<br>
WPR-Range<input id="COMMONTECHWRRANGEslider" type="range" value="<c:out value="${Commontechwprrange}"/>" min="-100" max="0" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHWRRANGE.value =COMMONTECHWRRANGEslider.value">
<output name="COMMONTECHWRRANGEoname" id="COMMONTECHWRRANGE"><c:out value="${Commontechwprrange}"/></output> 
<br>
Curr-k<input id="COMMONTECHSKRANGEslider" type="range" value="<c:out value="${Commontechskrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSKRANGE.value =COMMONTECHSKRANGEslider.value">
<output name="COMMONTECHSKRANGEoname" id="COMMONTECHSKRANGE"><c:out value="${Commontechskrange}"/></output> 
<br>
Curr-D<input id="COMMONTECHSDRANGEslider" type="range" value="<c:out value="${Commontechsdrange}"/>" min="0" max="100" step="5"  onchange="OthertechFormChange()" oninput="COMMONTECHSDRANGE.value =COMMONTECHSDRANGEslider.value">
<output name="COMMONTECHSDRANGEoname" id="COMMONTECHSDRANGE"><c:out value="${Commontechsdrange}"/></output> 
<br>



 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Period</p>
                            </div>
                             <div class="panel-body">



DayWPR-Period<input id="COMMONTECHmaxdiffslider" type="range" value="<c:out value="${Commontechdaywprp}"/>" min="0" max="50" step="1"  onchange="OthertechFormreloadChange()" oninput="COMMONTECHDWRPERIOD.value =COMMONTECHmaxdiffslider.value">
<output name="COMMONTECHmaxdiffOutputName" id="COMMONTECHDWRPERIOD"><c:out value="${Commontechdaywprp}"/></output> 
<br>
DayRSI-Period<input id="COMMONTECHmindiffslider" type="range" value="<c:out value="${Commontechdayrsip}"/>" min="0" max="50" step="1"  onchange="OthertechFormreloadChange()" oninput="COMMONTECHDRSIPERIOD.value =COMMONTECHmindiffslider.value">
<output name="COMMONTECHmindiffOutputName" id="COMMONTECHDRSIPERIOD"><c:out value="${Commontechdayrsip}"/></output> 
<br>
WeekWPR-Period<input id="COMMONTECHStochKslider" type="range" value="<c:out value="${Commontechweekwprp}"/>" min="0" max="50" step="1"  onchange="OthertechFormreloadChange()" oninput="COMMONTECHWWRPERIOD.value =COMMONTECHStochKslider.value">
<output name="COMMONTECHStochKOutputName" id="COMMONTECHWWRPERIOD"><c:out value="${Commontechweekwprp}"/></output> 
<br>
WeekRSI-Period<input id="COMMONTECHStochDslider" type="range" value="<c:out value="${Commontechweekrsip}"/>" min="0" max="50" step="1"  onchange="OthertechFormreloadChange()" oninput="COMMONTECHWRSIPERIOD.value =COMMONTECHStochDslider.value">
<output name="COMMONTECHStochDOutputName" id="COMMONTECHWRSIPERIOD"><c:out value="${Commontechweekrsip}"/></output> 
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