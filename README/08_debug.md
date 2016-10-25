# Debugging

1. Execute the [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) script. From a terminal session execute **`./_debug.sh`**.
2. In eclipse: right-click the project > Debug As > Debug Configurations... > double-click 'Remote Java Application' > change the port number to `9999` > Ok.
3. Add the needed break-points in the code.
4. In the terminal session where you started the debugger execute **`run`**.
5. From the browser/client send the problematic request; that will trigger your first break point and you go from there.
6. To stop the service from the terminal execute **`control + d`**, then execute **`exit`** to stop the debugger.
