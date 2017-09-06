//LiveData on off button -- start


$('#toggle_event_editing button').click(function(){

	/* reverse locking status */
	$('#toggle_event_editing button').eq(0).toggleClass('locked_inactive locked_active btn-default btn-info');
	$('#toggle_event_editing button').eq(1).toggleClass('unlocked_inactive unlocked_active btn-info btn-default');
});

//LiveData on off button --end
$("#dashb").click(function(){
    event.preventDefault(); 
   
            $('#content').html("<div></div>");         

});
$("#dasbimg").click(function(){
    event.preventDefault(); 
 
            $('#content').html("<div></div>");         
       
});
$("#homeb").click(function(){
    event.preventDefault(); 
 
            $('#content').html("<div></div>");         
       
});
$("#signout").click(function(){
    
 
    $.get('Logoutservlet',{request:"form"},function(responseText) { 
		  
  });
    location.reload(true);
       
});


var timerID
$(document).ready(function(){
   
	
	 
   $("#myonoffswitch").click(function(){
	  
        if($(myonoffswitch).is(':checked') == true) 
		{
        	 $("#Livecount").html("<i class=\"fa fa-refresh fa-spin\"></i>");
        	  $("#DayLivecount").html("<i class=\"fa fa-refresh fa-spin\"></i>");
        	 $("#Livesclicktatusval").text("Updating");
        	 
  		   $.get('LiveupdateServlet',{request:"form"},function(responseText) { 
                  $("#Livesclicktatusval").text("LastUpdate:"+responseText); 
                  
                  $.get('DayweeksmapvServlet',{request:"form"},function(responseText) { 
            			
            			$.get('Livestockcount',{request:"form"},function(responseText) { 
           			   $("#Livecount").html(responseText);
                   });
            			
            			
            			
              });
  		   
  		
                  $.get('DaydaysmapvServlet',{request:"form"},function(responseText) { 
        			  
           		   $.get('DayLivestockcount',{request:"form"},function(responseText) { 
           			   $("#DayLivecount").html(responseText);
                      });
           		   
                  });
  			 
                  
  			
         });
  		
  	//	 $.get('Technicals15minupdateservlet',{request:"form"},function(responseText) { 
			  
     		   
     //    });
		  
  		   
		  timerID = setInterval(function() {
			  $("#Livesclicktatusval").text("Updating");
			  $("#Livecount").html("<i class=\"fa fa-refresh fa-spin\"></i>");
			   $("#DayLivecount").html("<i class=\"fa fa-refresh fa-spin\"></i>");
			   
		   $.get('LiveupdateServlet',{request:"form"},function(responseText) { 
                $("#Livesclicktatusval").text("LastUpdate:"+responseText); 
                
                
                
                $.get('DayweeksmapvServlet',{request:"form"},function(responseText) { 
      			  
     			   $.get('Livestockcount',{request:"form"},function(responseText) { 
     				   $("#Livecount").html(responseText);
     	           });
     			   
                });
                
                
                $.get('DaydaysmapvServlet',{request:"form"},function(responseText) { 
  				  
     			   $.get('DayLivestockcount',{request:"form"},function(responseText) { 
     				   $("#DayLivecount").html(responseText);
     	           });
     			   
                });
                
                
            });
		   
		  
		//   $.get('Technicals15minupdateservlet',{request:"form"},function(responseText) { 
	 			  
       		   
        //   });
		  
		   
		  
		  
		  }, 290 * 1000);
		}
        else
		{
		 
		  clearInterval(timerID); 
		
		  
		  }
		});
    $("#Weeksp").click(function(){
	   event.preventDefault(); 
	  
	   $('#content').html("<div>Loading Data.....</div>");    
       $.get('WeeksmapvServlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
    
    
    $("#Dayeodsp").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('DaysmapvServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
    
    $("#WKFILTLIVE").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('EodfiltershowliveServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    $("#ALLHIGHLOW").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('AllTimeHighLowServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    
    $("#HIGHLOW52").click(function(){
   	   event.preventDefault(); 
   	   $('#content').html("<div>Loading Data.....</div>");    
          $.get('Week52HighLow',{request:"form"},function(responseText) { 
                   $('#content').html(responseText);         
               });
       });
    
    $("#WeekCandlediv").click(function(){
 	   event.preventDefault(); 
 	   $('#content').html("<div>Loading Data.....</div>");    
        $.get('CandlePatternServlet',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
     });
    
    $("#DayCurrentsp").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('DaydaysmapvServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    
    
    
    $("#WeekCandleopenlowdiv").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('WeekCandleOpenlowServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
    
    $("#OTHERTECH").click(function(){
   	   event.preventDefault(); 
   	$('#content').html("<div>Loading Data.....</div>");    
    $.get('CommonTechnicalsServlet',{request:"form"},function(responseText) { 
             $('#content').html(responseText);         
         });
       });
    
    $("#min15live").click(function(){
    	   event.preventDefault(); 
    	$('#content').html("<div>Loading Data.....</div>");    
     $.get('Technicals15min',{request:"form"},function(responseText) { 
              $('#content').html(responseText);         
          });
        });
    
    
    
    $("#MonthCandlediv1").click(function(){
  	   event.preventDefault(); 
  	   $('#content').html("<div>Loading Data.....</div>");    
         $.get('CandlePatternMonthServlet',{request:"form"},function(responseText) { 
                  $('#content').html(responseText);         
              });
      });
     
     $("#MonthCandleopenlowdiv1").click(function(){
   	   event.preventDefault(); 
   	   $('#content').html("<div>Loading Data.....</div>");    
          $.get('MonthCandleOpenlowServlet',{request:"form"},function(responseText) { 
                   $('#content').html(responseText);         
               });
       });
     
     $("#EODTCHFILTLIVE").click(function(){
     	   event.preventDefault(); 
     	   $('#content').html("<div>Loading Data.....</div>");    
            $.get('EODtechnicalfilershowliveServlet',{request:"form"},function(responseText) { 
                     $('#content').html(responseText);         
                 });
         });
     
     $("#LIVEALERT").click(function(){
   	   event.preventDefault(); 
   	   $('#content').html("<div>Loading Data.....</div>");    
          $.get('LiveAlertServlet',{request:"form"},function(responseText) { 
                   $('#content').html(responseText);         
               });
       });
     
     
    
    
    
	$("#Daysp").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('DayweeksmapvServlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	$("#protofolio").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('ProtfolioServlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	$("#EPSbreak").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('EPSbreakservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	$("#dayEPSbreak").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('DayEPSbreakservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	$("#EPSdiff").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('EPSdiffservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	
	$("#dayEPSdiff").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('DayEPSdiffservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	
	$("#CPSbreak").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('CPSbreakservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	
	$("#OTLIVE").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
        $.get('Technicals15min',{request:"form"},function(responseText) { 
                 $('#content').html(responseText);         
             });
    });
	
	$("#CPSdiff").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('CPSdiffservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	
	$("#dayCPSbreak").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('DayCPSbreakservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	$("#dayCPSdiff").click(function(){
        event.preventDefault(); 
        $('#content').html("<div>Loading Data.....</div>");    
       $.get('DayCPSdiffservlet',{request:"form"},function(responseText) { 
                $('#content').html(responseText);         
            });
    });
	
	
});


