<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Battle Report</title>
    <style type="text/css">
      @page {
	 	size: A4;
		margin: 0;
	  }
      #map1 { width: 100%; height: 10%;
              border-style: solid;
    		  border-width: 5px;
    		  border-color: #FFFFFF }
      body { font-family: "Courier New", Courier, monospace;
             background-color: #f1e9d2; }
      .text {padding-left: 20px; padding-right: 20px}       
      .new-page{page-break-before:always}
    </style>
  </head>
  <body>
    <h1>${title}</h1>
    <p>In ye old days of ${date?datetime}</p>
    <div id="map1" class="map" data-src="${map}" />
	<p><i>${mapDescription}</i></p>
	<p class="text">
	The Battle of Prague, which occurred between 25 July and 1 November 1648 was the last action of the Thirty Years' War. General Hans Christoff von Königsmarck, commanding Sweden's flying column, entered the city, which was defended by the Governor Feldmarshall Rudolf von Colloredo, a veteran of the siege of Mantua and of the battle of Lutzen, where he served under Albrecht von Wallenstein. The Swedes captured Prague Castle on the western bank of the Vltava river and attempted to enter the Old Town on the eastern bank of the river, but were repulsed on the Charles Bridge by Colloredo's men. When a third Swedish army commanded by Prince Carl Gustaf came close to Prague, all three Swedish armies launched a number of attacks against the city. These attacks were resisted, largely thanks to the skill of the Feldmarshal and the energy of his troops. When in November Gustaf received a report about the signed Peace, he ordered his troops to leave.
    </p><p class="text">
Unable to enter the city, the Swedes settled for looting the Castle. Many of the treasures collected by Emperor Rudolf II (such as the Codex Gigas and Codex Argenteus) were taken to Sweden, whereof some can be found in Drottningholm Palace.
	</p> 	
  </body>
</html>