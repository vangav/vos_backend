# debugging

1. execute the [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) script; in a terminal session execute `./_debug.sh`
2. in eclipse: right-click the project **>** debug as **>** debug configurations... **>** double-click 'remote java application' **>** change the port number to `9999` **>** ok
3. add the needed break-points in the code
4. in the terminal session where you started the debugger execute `run` to run on port 9000 or `run different_port_number` 
5. from the browser/client send the problematic request that will trigger your first break point and you go from there
6. to stop the service from the terminal press `control + d`, then execute `exit` to stop the debugger

## next tutorial -> [deploy on a production server](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md)
> once your service is ready for release, this tutorial has the step-by-step process till your service is up and running on a production server - as well as how to scale it up
