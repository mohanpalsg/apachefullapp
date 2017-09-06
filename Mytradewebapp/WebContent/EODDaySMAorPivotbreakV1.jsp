<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Hashtable"%> 
<%@page import="java.util.Map"%> 
<%@page import="java.util.HashMap"%>
<%@ page session="true" %>


<script type="text/javascript">
        function DayEPSbreakFormChange() {
          	
        	var smachecked=Leveloptionselectedpg2("SMA");
        	var PPchecked= Leveloptionselectedpg2("PP");
            var S1checked = Leveloptionselectedpg2("S1");
            var S2checked = Leveloptionselectedpg2("S2");
            var S3checked = Leveloptionselectedpg2("S3");
            var S4checked = Leveloptionselectedpg2("S4");
            var R1checked = Leveloptionselectedpg2("R1");
            var R2checked = Leveloptionselectedpg2("R2");
            var R3checked = Leveloptionselectedpg2("R3");
            var R4checked = Leveloptionselectedpg2("R4");
            
        	$.post(
                    "DayEPSbreakservlet", 
                    {DayEPSbreaknse200attr : $(DayEPSbreaknse200check).is(':checked'),
                     DayEPSbreakotherattr : $(DayEPSbreakothercheck).is(':checked'),
                     DayEPSbreakdiffattr: $("#DayEPSbreakoutputval").val(),
                     DayEPSbreakoptionSMA : smachecked,
                     DayEPSbreakoptionPP : PPchecked,
                     DayEPSbreakoptionS1 : S1checked,
                     DayEPSbreakoptionS2 : S2checked,
                     DayEPSbreakoptionS3 : S3checked,
                     DayEPSbreakoptionS4 : S4checked,
                     DayEPSbreakoptionR1 : R1checked,
                     DayEPSbreakoptionR2 : R2checked,
                     DayEPSbreakoptionR3 : R3checked,
                     DayEPSbreakoptionR4 : R4checked
                     
                     //meaasge you want to send
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 
        }
    	
  	  $('#dataTables-WEEKEODSMAPV').DataTable({
            responsive: true
    });
  	  
  	  
  	$('#W2LevelSelect').multiselect();
  	 
  	 function Leveloptionselectedpg2 (level)
  		{
  			
  			var selected = "false";
  			 $( "#W2LevelSelect option:selected" ).each(
  				
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
                                <p>EOD- daily Pivot/SMA Breaking Stock</p>
                            </div>
                             <div class="panel-body">

<div class="row">
                            <!-- /.panel-heading -->
                           
                                <div class="dataTable_wrapper col-lg-9">
<table class="table table-striped table-bordered table-hover "  id="dataTables-WEEKEODSMAPV">

<thead>
<tr>
       <td>Stock</td>
       <td>B-Level</td>
       <td>L-Val</td>
       <td>P-close</td>
       <td>C-close</td>
       <td>diff</td>
       
      
    </tr>
 </thead>
 <tbody>  
<c:forEach var="entry" items="${EODdaypivotsmabreak}" >
      
 <tr>
 
<td>${entry.value.getStocksymbol()}</td>
<c:if test="${entry.value.isBreaksma() eq true}">
<td>SMA50</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotlevel()}</td>
</c:if>
<c:if test="${entry.value.isBreaksma() eq true}">
<td>${entry.value.getSmavalue()}</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotval()}</td>
</c:if>

       <td>${entry.value.getPrevclose()}</td>
       <td>${entry.value.getCurrclose()}</td>
       
<c:if test="${entry.value.isBreaksma() eq true}">
<td>${entry.value.getSmadiff()}</td>
</c:if>
<c:if test="${entry.value.isBreakpivot() eq true}">
<td>${entry.value.getPivotdiff()}</td>
</c:if>

      
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
                             
 <select id="W2LevelSelect" onchange="DayEPSbreakFormChange()" multiple="multiple">
 

  <option value="SMA"  <c:if test="${DayEPSbreakSMA eq true}">
selected
</c:if>>SMA50</option>

    <option value="PP"  <c:if test="${DayEPSbreakPP eq true}">
selected
</c:if>>Pivot PP</option>

    <option value="S1" <c:if test="${DayEPSbreakS1 eq true}">
selected
</c:if>>Pivot S1</option>
    <option value="S2" <c:if test="${DayEPSbreakS2 eq true}">
selected
</c:if>>Pivot S2</option>
    <option value="S3" <c:if test="${DayEPSbreakS3 eq true}">
selected
</c:if>>Pivot S3</option>
    <option value="S4" <c:if test="${DayEPSbreakS4 eq true}">
selected
</c:if>>Pivot S4</option>
    <option value="R1" <c:if test="${DayEPSbreakR1 eq true}">
selected
</c:if>>Pivot R1</option>
     <option value="R2" <c:if test="${DayEPSbreakR2 eq true}">
selected
</c:if>>Pivot R2</option>
      <option value="R3" <c:if test="${DayEPSbreakR3 eq true}">
selected
</c:if>>Pivot R3</option>
       <option value="R4" <c:if test="${DayEPSbreakR4 eq true}">
selected
</c:if>>Pivot R4</option>
</select>

  
<br>
</div>
</div>
<br>                     
 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Stock Group</p>
                            </div>
                             <div class="panel-body">                           
<input id="DayEPSbreaknse200check" type="checkbox"     
name="nse200"      
onchange = "DayEPSbreakFormChange()" <c:if test="${Dayepsbreaknse eq true}">
checked
</c:if>>nse200       
<input id="DayEPSbreakothercheck" type="checkbox"     
name="others"     
onchange = "DayEPSbreakFormChange()" <c:if test="${Dayepsbreakothers eq true}">
checked
</c:if>>Others 
&nbsp;&nbsp;
<br><br>

 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Percentage</p>
                            </div>
                             <div class="panel-body">
Percent<input id="DayEPSbreakslider" type="range" value="<c:out value="${Dayepsperslide}"/>" min="0" max="10" step="0.5"  onchange="DayEPSbreakFormChange()" oninput="DayEPSbreakoutputval.value =DayEPSbreakslider.value">
<output name="DayEPSbreakOutputName" id="DayEPSbreakoutputval"><c:out value="${Dayepsperslide}"/></output> 
&nbsp;&nbsp;

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
    