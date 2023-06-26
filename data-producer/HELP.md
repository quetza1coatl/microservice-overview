### Service description  
Aggregator for all black hole sensors. Service has just one purpose: receive information from sensors,
 validate it and push to kafka topic for further investigation.  
It has only one endpoint that returns 200 OK.
In case of errors it should be logged for further investigation by sensor engineers.
Sensors aren't able to do something with error responses, so details of errors aren't sent back and is only logged,
meanwhile for debug purposes for those cases we return error response code.  
Sensors duplicate their ID and put it to the header as well with name `Sensor-Id`.
   