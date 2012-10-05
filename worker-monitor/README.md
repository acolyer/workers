Worker Monitor Single Page App
=============

No server-side logic here! A minimal express app that simply serves static
content from the 'client' directory. 

This app depends on the [highcharts](http://www.highcharts.com) library
which is free for non-commercial use. After cloning the repo you will need
to download highcharts and copy the contents of the 'js' folder in the
distribution to the 'client/assets/js/highcharts' directory in this project.

The app has been tested with Highcharts-2.3.2.

This is a quick 'hack' page to get something up in the browser. Not to be
treated as an example of a well-structured client-side app!!

The main action of interest is in the 'client/index.html' file where you can
see the sock.js client code for making a websockets connection back to the
ws-relay exchange. 
