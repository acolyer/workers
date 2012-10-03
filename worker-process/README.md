Worker process
==========

A worker (message consumer) process for the workers sample.

Consumes messages from RabbitMQ, taking 3 seconds to process each message.
Deploy as a standalone application on cloud foundry and bind to the same
RabbitMQ service as the producer and queue monitor processes.
