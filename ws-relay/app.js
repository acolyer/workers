// A simple application that pushes messages published on a rabbitmq
// topic exchange to browser-based subscribers over websockets.
// 
// We use sock.js and amqp to achieve this feat.
//
// The http and amqp connections will be auto-configured by Cloud Foundry
// when the app is pushed, overriding the local values in this file.

var http = require('http');
var sockjs = require('sockjs');
var amqp = require('amqp');

var amqpConnection = amqp.createConnection({url:'amqp://guest:guest@localhost:5672'});
var httpServer = http.createServer(httpHandler);

// Configure sockjs for websockets support
var sockjs_opts = { sockjs_url: "http://cdn.sockjs.org/sockjs-0.3.min.js" };
var sockjsServer = sockjs.createServer(sockjs_opts);
sockjsServer.installHandlers(httpServer, {prefix: '[/]socks'});

// Configure amqpContext to push messages from topic exchange
amqpConnection.on('ready',connectExchange);

// Start listening for incoming connections
httpServer.listen(8080,'127.0.0.1');
console.log('Ready for Action!');

function httpHandler(req, res) {
	res.writeHead(200, {'Content-Type' : 'text/plain', 'Access-Control-Allow-Origin' : '*'});
	res.end('Hello World\n');
}

function connectExchange() {
	console.log('Here we connect rabbitmq to the websocket...');

	// when a sockjs connection is made, create a new private queue
	// subscribing to the monitoring.exchange, and pipe all messages
	// delivered to it to the browser.
	sockjsServer.on('connection', function(connection) {
		console.log('Connection request from browser... piping messages');
		amqpConnection.queue('', function(q) {
		  q.bind('monitoring.exchange','#');
		  q.subscribe(function(msg) {
			console.log(msg.data);
			connection.write(msg.data);
		  });
		  connection.on('close', function() { q.destroy(); });
		});
	});
}
