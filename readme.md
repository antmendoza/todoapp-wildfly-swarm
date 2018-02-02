[![Build Status](https://travis-ci.org/antmendoza/todoapp-wildfly-swarm.svg?branch=master)](https://travis-ci.org/antmendoza/todoapp-wildfly-swarm)
[![Coverage Status](https://coveralls.io/repos/github/antmendoza/wildfly-swarm-examples/badge.svg?branch=master)](https://coveralls.io/github/antmendoza/wildfly-swarm-examples?branch=master)

TODO app
====

TODO application as a POC using `Wildfly Swarm` and `arquillian` for integration test. 

The application expose a REST endpoint to manage TODOs.

- `http://localhost:8080/tasks/{:taskId}`

To run the app execute `mvn wildfly-swarm:run`.
