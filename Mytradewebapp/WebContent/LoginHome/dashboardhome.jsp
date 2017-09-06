<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>MyTrade</title>

    <!-- Bootstrap Core CSS -->
    <link href="./LoginHome/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="./LoginHome/css/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="./LoginHome/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="./LoginHome/css/startmin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="./LoginHome/css/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="./LoginHome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <!-- DataTables CSS -->
     <link href="./LoginHome/css/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="./LoginHome/css/dataTables/dataTables.responsive.css" rel="stylesheet">
    
  
    
	<!-- my style sheet -->
	 <link href="./LoginHome/css/mycustomcss.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  

</head>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">PivotMaster</a>
        </div>

        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                
        <!-- Top Navigation: Left Menu -->
        <ul class="nav navbar-nav navbar-left navbar-top-links">
            <li id="homeb"><a href="#"><i class="fa fa-home fa-fw"></i> Home</a></li>
        </ul>
		
		<ul class="nav navbar-nav navbar-left navbar-top-links">
            <li id="min15live"><a href="#"><i class="fa fa-download fa-fw"></i> LiveData</a></li>
        </ul>
		
		
		
            <div class="onoffswitch nav navbar-nav navbar-left navbar-top-links">
              <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" >
              <label class="onoffswitch-label" for="myonoffswitch">
                 <span class="onoffswitch-inner"></span>
                 <span class="onoffswitch-switch"></span>
              </label>
        
        </div>
		
		<ul class="nav navbar-nav  navbar-left navbar-top-links">
            <li><a id="Livesclicktatusval" href="#"><i class="fa fa-hourglass-o fa-fw"></i>LastUpdate: Unknown </a></li>
        </ul> 
		
		

        <!-- Top Navigation: Right Menu -->
        <ul class="nav navbar-right navbar-top-links">
            
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> Mohan <b class="caret"></b>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>
                    <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li><a id="signout" href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                    </li>
                </ul>
            </li>
        </ul>
		


        <!-- Sidebar -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">

                <ul class="nav" id="side-menu">
                    
                    <li>
                        <a id="dashb" href="#" class="active"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Week Stats Pivot &amp; SMA<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">EOD Price Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="EPSbreak" href="#">Price Break SMA/Pivot</a>
                                    </li>
									 <li>
                                        <a id="EPSdiff" href="#">Price Diff  SMA/Pivot</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
						 <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Current Price Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="CPSbreak" href="#">Price Break SMA/Pivot</a>
                                    </li>
									 <li>
                                        <a id="CPSdiff" href="#">Price Diff  SMA/Pivot</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                     <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Day Stats Pivot &amp; SMA<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">EOD Price Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="dayEPSbreak" href="#">Price Break SMA/Pivot</a>
                                    </li>
									 <li>
                                        <a id="dayEPSdiff" href="#">Price Diff  SMA/Pivot</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
						 <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Current Price Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="dayCPSbreak" href="#">Price Break SMA/Pivot</a>
                                    </li>
									 <li>
                                        <a id="dayCPSdiff" href="#">Price Diff  SMA/Pivot</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                      <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Candle Pattern Analysis<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Week Pattern Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="WeekCandlediv" href="#">All Bullish Pattern</a>
                                    </li>
									 <li>
                                        <a id="WeekCandleopenlowdiv" href="#">(0.25% adjusted) KeyMatch</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
						 <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Month Pattern Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="MonthCandlediv1" href="#">All Bullish Pattern</a>
                                    </li>
									 <li>
                                        <a id="MonthCandleopenlowdiv1" href="#">(0.25% adjusted) KeyMatch</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
					 <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Historic Data Analysis<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                             <li>
                                <a href="#">HighLowDiff Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                            <li>
                                <a id="ALLHIGHLOW" href="#">All time HighLow diff </a>
                                
                            </li>
							<li>
                                <a id="HIGHLOW52" href="#">52week HighLow diff </a>
                                
                            </li>
							</ul>
                            </li>
                        </ul>
                       
                        <ul class="nav nav-second-level">
                             <li>
                                <a href="#">Technical Analysis <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                            <li>
                                <a id="OTHERTECH" href="#">Common Technicals - EOD </a>
                                
                            </li>
							<li>
                                <a id="OTLIVE" href="#">Common Technicals - LIVE  </a>
                                
                            </li>
                            <li>
                                <a id="WKFILTLIVE" href="#">Technicals - Weekly Diff Filtered LIVE  </a>
                                
                            </li>
                            <li>
                                <a id="EODTCHFILTLIVE" href="#">Technicals - EODTECH Filtered LIVE  </a>
                                
                            </li>
                             <li>
                                <a id="LIVEALERT" href="#">LIVE ALERT </a>
                                
                            </li>
                            
                            
							</ul>
                            </li>
                        </ul>
				
                    </li>
                  
                </ul>
                

            </div>
        </div>
    </nav>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">

            <div class="row">
                <div class="col-lg-12">
                    <h1 class=" page-header "><a id="dasbimg" class=" fa  fa-dashboard"></a>&nbsp;&nbsp;<i>DashBoard</i></h1>
                    
                </div>
            </div>

            <!-- /summary row starts-->
                <div class="row">
                
				<div  class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-briefcase fa-2x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= request.getAttribute("WeekSM50Pvsize") %></div>
                                        <div>Week EOD Break</div>
                                    </div>
                                </div>
                            </div>
                            <a id="Weeksp" href="#">
                                <div  class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    
                    <div  class="col-lg-3 col-md-6">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-briefcase fa-2x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="Livecount" class="huge"><i class="fa fa-power-off"></i></div>
                                        <div>Week Live Break  </div>
                                    </div>
                                </div>
                            </div>
                            <a id="Daysp" href="#">
                                <div class="panel-footer">
                                    <span  class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-briefcase fa-2x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div class="huge"><%= request.getAttribute("DaySM50Pvsize") %></div>
                                        <div>Day EOD Break</div>
                                    </div>
                                </div>
                            </div>
                            <a id="Dayeodsp" href="#">
                                <div  class="panel-footer">
                                    <span class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                    
                    <div class="col-lg-3 col-md-6">
                        <div class="panel panel-green">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <i class="fa fa-briefcase fa-2x"></i>
                                    </div>
                                    <div class="col-xs-9 text-right">
                                        <div id="DayLivecount" class="huge"><i class="fa fa-power-off"></i></div>
                                        <div>Day Live Break  </div>
                                    </div>
                                </div>
                            </div>
                            <a id="DayCurrentsp" href="#">
                                <div class="panel-footer">
                                    <span  class="pull-left">View Details</span>
                                    <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>

                                    <div class="clearfix"></div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                   
<div class="row">
<!--  Tables starts -->
 <div id="content" class="col-lg-14">


</div>

                                <!--  Tables ends -->
                                <!-- notification panel starts -->
                                
 
    </div>

</div>

  <!-- jQuery -->
<script src="./LoginHome/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="./LoginHome/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="./LoginHome/js/metisMenu.min.js"></script>

 <!-- DataTables JavaScript -->
        <script src="./LoginHome/js/dataTables/jquery.dataTables.min.js"></script>
        <script src="./LoginHome/js/dataTables/dataTables.bootstrap.min.js"></script>
        
<!-- Custom Theme JavaScript -->
<script src="./LoginHome/js/startmin.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.js"></script>


      
<!-- My customized Javascript --->
<script src="./LoginHome/js/mycustomjs.js"></script>



</body>
</html>
