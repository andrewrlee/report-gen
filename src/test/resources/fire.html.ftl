<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>My document</title>
    <style type="text/css">
      @page {
	 	size: A4;
		margin: 0;
	  }
      #map1 { width: 80%; height: 10%; padding-left: 10%; padding-top: 30px }
      .chart { width: 80%; height: 10%; padding-left: 10%; padding-top: 30px }
      .new-page{page-break-before:always}
    </style>
  </head>
  <body>
    <h1>${title}</h1>
    <p><strong>ID:</strong> ${id} <strong>Date:</strong> ${date?datetime}</p>
    <p>Source of fire estimated: <i>Lat:</i> ${location.latitude} <i>Long:</i> ${location.longitude}</p>
    <div id="map1" class="map" data-src="${map}" />
	<p><i>${mapDescription}</i></p> 	
    <p>The blue markers(1) are water drops made by the fire 'copter. The green marker(2) is the wildfire station. The Arrow points in the direction of the prevailing wind. The Red line is the fire line.</p>
    <div class="new-page"/>
      <h2>Temperature At Water Drop 1</h2>
	  <div data-chart-type="water-drop-1" class="chart"/>
      <h2>Temperature At Water Drop 2</h2>
	  <div data-chart-type="water-drop-2" class="chart"/>
  </body>
</html>