Workers Autoscaling Sample
==========================

A collection of apps to demonstrate scaling of worker processes serving a 
RabbitMQ queue.

* 'producer' is a simple app that generates workload by publishing one message
a second to a 'work.queue'.

* 'worker-process' is a worker process that reads messages from 'work.queue'.
Processing of each message takes 3 seconds. 

* 'autoscaler' is an app which monitors the #messages and # available
consumers for the queue and adds and removes worker instances accordingly.

All of these apps are built using Spring Integration and designed to be
deployed to Cloud Foundry as standalone apps.

So that you can observe the process in action, there is also a simple web
app that connects to monitoring data published to a topic exchange by the
autoscaler and renders it in the browser. This also serves to give an
example of handling websocket connections with sock.js and rabbit.js.

* 'ws-relay' is a websocket based relay that accepts incoming browser
subscriptions over websockets and pushes any messages from the monitoring
topic exchange to the browser. Deploy as a Cloud Foundry node app.

* 'worker-monitor' is a simple web app that connects over websockets
(sock.js) to show real time information on the status of the work queue and
workers.

