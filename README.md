# trade-enricher

## Quick Start 

### Running 
* `mvn spring-boot:run`

The service will run on a localhost:8080 

### Testing with HTTP request
`curl --request POST --data-binary @trade.csv --header 'Content-Type: text/csv' --header 'Accept: text/csv' http://localhost:8080/api/v1/trades/enrich`


### Comments / Known Issues
* It's just a microservice embryo, with a very limited time there's the non-blocking Rx concept, there is very basic interface segregation concept, there is working happy-path functionality
* Testing - just a happy path 'full-stack' test, there should and would be more, integration and unit ones, performance ones
* There should be more validation for DTOs and parsing, currencies white-list, is price non-negative, is it in the right format - I am well aware, I had just cut the scope
* There should be security, filters and interceptors - no matching requestId etc.
* CI/CD stuff, service architecture - no different profiles now, no service discovery, configuration of other services/clients (there are just Observables in interfaces so should be easy to adjust to e.g. Feign clients)
* no branching, no release (on purpose, as the CD would set the release version)
