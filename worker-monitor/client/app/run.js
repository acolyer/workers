/*global curl:true*/
(function(curl) {

	var config = {
		baseUrl: '',
		pluginPath: 'curl/plugin',
		paths: {},
		packages: [
			{ name: 'highcharts', location: 'lib/highcharts', main: 'highcharts'},
			{ name: 'highcharts-more', location: 'lib/highcharts', main: 'highcharts-more'},
			{ name: 'bootstrap', location: 'lib/bootstrap', main: 'bootstrap'},
			{ name: 'jquery', location: 'lib/jquery', main: 'jquery.min'},
			{ name: 'sockjs', location: 'lib/sockjs', main: 'sockjs-0.3.min'},
			
			// cujojs modules...
			{ name: 'curl', location: 'lib/curl/src/curl', main: '../curl' },
			{ name: 'meld', location: 'lib/meld', main: 'meld' },
			{ name: 'poly', location: 'lib/poly', main: './poly' },
			{ name: 'when', location: 'lib/when', main: 'when' },
			{ name: 'wire', location: 'lib/wire', main: './wire' }
		],
		preloads: ['jquery','poly/all']
	};

	curl(config, ['wire!app/main']);

}(curl));