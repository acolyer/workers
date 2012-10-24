define({

	theme : { module: 'css!theme/bootstrap.css'},
	responsive_theme : { module: 'css!theme/bootstrap-responsive.css'},
	bootstrap: { module: 'bootstrap' },

	gauges: { 
		create: {
			module: 'app/gauges/gauges',
			args: [ {module: 'highcharts'},
			        'msgGauge',
			        'consumerGauge',
			        'workersGauge'],
			ready: 'createGauges'
		},
		// how to manage the highcharts-more dependency???
		more: { module: 'highcharts-more' }
	},
	
	data_feed: { 
		create: {
			module: 'app/data_feed/data_feed',
			args: [{module: 'sockjs'},
				   'http://ws-relay.p02.rbconsvcs.com/socks',
				   {$ref: 'dom!workersCount'},
				   {$ref: 'dom!msgCount'},
				   {$ref: 'dom!consumerCount'},
				   {$ref: 'gauges.workerChart'},
				   {$ref: 'gauges.msgChart'},
				   {$ref: 'gauges.consumerChart'}]
		}
	},
	
	plugins: [
		{ module: 'wire/debug', verbose : true },
		{ module: 'wire/dom', classes: { init: 'loading' }},
		{ module: 'wire/dom/render' },
		{ module: 'wire/on' },
		{ module: 'wire/aop' }
	]
});