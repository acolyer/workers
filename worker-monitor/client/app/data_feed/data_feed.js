(function(define) {
    define(function(require) {
		
		return function(sockjs,uri,
			workerCount,msgCount,consumerCount,
			workerChart,msgChart,consumerChart) {
		
          var sock = new sockjs.SockJS(uri);
         
          sock.onmessage = function(msg) {
            var msgData = $.parseJSON(msg.data);
            if (typeof msgData.workers !== "undefined") {
                workerCount.innerHTML = msgData.workers;
                workerChart.series[0].points[0].update(msgData.workers);
            }
            if (typeof msgData.msgs !== "undefined") {
                msgCount.innerHTML = msgData.msgs;
                msgChart.series[0].points[0].update(msgData.msgs);
            }
            if (typeof msgData.consumers !== "undefined") {
                consumerCount.innerHTML = msgData.consumers;
                consumerChart.series[0].points[0].update(msgData.consumers);
            }
          };
        };
        
    });
}(typeof define === 'function' && define.amd ? 
	define : 
	function(factory) { module.exports = factory(require);}));