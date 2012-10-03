Workers Autoscaling Sample
==========================

A collection of apps to demonstrate scaling of worker processes serving a 
RabbitMQ queue.

'producer' is a simple app that generates workload by publishing one message
a second to a 'work.queue'.

'worker-process' is a worker process that reads messages from 'work.queue'.
Processing of each message takes 3 seconds. 

'autoscaler' is an app which monitors the #messages and # available
consumers for the queue and adds and removes worker instances accordingly.

All of these apps are built using Spring Integration and designed to be
deployed to Cloud Foundry as standalone apps.
