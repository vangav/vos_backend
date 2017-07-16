
## expand "calculate sum" to "calculator"

based on [calculate sum](https://github.com/vangav/vos_calculate_sum), the following steps show how to expand the service to a more generic "calculator service"

### generate a new service
1. create a new directory `my_services/calculator`
2. copy `controllers.json` from `vos_backend/vangav_backend_templates/vos_calculate_sum/` to the directory `my_services/calculator` created in (1)
3. add as many features as desired by editing `my_services/calculator/controllers.json`; for example after adding a multiplication feature the `controllers` part of `my_services/calculator/controllers.json` will be as follows
> note: in vangav backend, inline comments (lines starting with a `#`) are supported in json files

```json
  "controllers": [

    # CalculateSum
    {
      "is_preset": false,
      "name": "CalculateSum",
      "type": "GET",
      "request_params": [
        {
          "name": "a",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        },
        {
          "name": "b",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "c",
          "type": "double",
          "is_array": false
        }
      ]
    },

    # CalculateMultiplication
    {
      "is_preset": false,
      "name": "CalculateMultiplication",
      "type": "GET",
      "request_params": [
        {
          "name": "a",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        },
        {
          "name": "b",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "c",
          "type": "double",
          "is_array": false
        }
      ]
    }

  ]
```

4. open a terminal session and `cd` to `my_services/vos_backend/tools_bin`
5. execute the command `java -jar backend_generator.jar new calculator` to generate the service
6. enter `y` for using the config directory in order to use `controllers.json` for generating
7. enter `n` for generating a worker service (using workers is explained in a separate section)

### writing the service's logic code
+ repeat all the steps in the [writing the service's logic code](https://github.com/vangav/vos_backend/blob/master/README.md#writing-the-services-logic-code) section above then add to them the following steps to implement the multiplication feature's logic
+ open class `HandlerCalculateMultiplication.java` under package `com.vangav.vos_calculate_sum.controllers.calculate_multiplication`, method `processRequest` should be as follows in order to complete the request-to-response logic
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestCalculateMultiplication requestCalculateMultiplication =
      (RequestCalculateMultiplication)request.getRequestJsonBody();
    
    // set response's value
    ((ResponseCalculateMultiplication)request.getResponseBody() ).set(
      requestCalculateMultiplication.a * requestCalculateMultiplication.b);
  }
```

### start the service
1. `cd` to `my_services/calculator`
2. execute the command `./_run.sh`

### try it out
1. test sum: open an internet browser page and type [`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **3.5**
2. test multiplication: open an internet browser page and type [`http://localhost:9000/calculate_multiplication?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **2.76**

### stop the service
in the terminal session where you started the service press `control + d`

## expand "calculate sum" to "calculator" without regenerating the service

in this section we will get the same result from the [expand "calculate sum" to "calculator"](https://github.com/vangav/vos_backend/blob/master/README.md#expand-calculate-sum-to-calculator) without regenerating the service; given the already generated [calculate sum](https://github.com/vangav/vos_calculate_sum) backend service, we will follow the following steps to add a multiplication feature

1. under the package [`com/vangav/vos_calculate_sum/controllers/`](https://github.com/vangav/vos_calculate_sum/tree/master/app/com/vangav/vos_calculate_sum/controllers) we will make a copy of `calculate_sum` and call it `calculate_multiplication`
2. inside `calculate_multiplication` we will do the following modifications
3. rename [`ControllerCalculateSum.java`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ControllerCalculateSum.java) to `ControllerCalculateMultiplication.java`
  + rename [`getCalculateSum`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ControllerCalculateSum.java#L63) to `getCalculateMultiplication`
  + rename [`HandlerCalculateSum`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ControllerCalculateSum.java#L65) and [`handlerCalculateSum`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ControllerCalculateSum.java#L65) to `HandlerCalculateMultiplication` and `handlerCalculateMultiplication` respectively
4. rename [`RequestCalculateSum.java`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/RequestCalculateSum.java) to `RequestCalculateMultiplication.java`
  + alter the [`getName return`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/RequestCalculateSum.java#L72) to `return "CalculateMultiplication";`
5. rename [`ResponseCalculateSum.java`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ResponseCalculateSum.java) to `ResponseCalculateMultiplication.java`
  + alter the [`getName return`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/ResponseCalculateSum.java#L68) to `return "CalculateMultiplication";`
6. rename [`HandlerCalculateSum.java`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java) to `HandlerCalculateMultiplication.java`
  + alter [`kName = "CalculateSum";`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L65) to `kName = "CalculateMultiplication";`
  + alter [`return new RequestCalculateSum();`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L76) to `return new RequestCalculateMultiplication();`
  + alter [`return new ResponseCalculateSum();`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L82) to `return new ResponseCalculateMultiplication();`
7.  open copied class `HandlerCalculateMultiplication.java` under package `com.vangav.vos_calculate_sum.controllers.calculate_multiplication`, method `processRequest` should be as follows in order to complete the request-to-response logic
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestCalculateMultiplication requestCalculateMultiplication =
      (RequestCalculateMultiplication)request.getRequestJsonBody();
    
    // set response's value
    ((ResponseCalculateMultiplication)request.getResponseBody() ).set(
      requestCalculateMultiplication.a * requestCalculateMultiplication.b);
  }
```
8. append the following line to the [`routes`](https://github.com/vangav/vos_calculate_sum/blob/master/conf/routes) conf file `GET   /calculate_multiplication                    com.vangav.vos_calculate_sum.controllers.calculate_multiplication.ControllerCalculateMultiplication.getCalculateMultiplication()`

### start the service
1. `cd` to `my_services/calculator`
2. execute the command `./_run.sh`

### try it out
1. test sum: open an internet browser page and type [`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **3.5**
2. test multiplication: open an internet browser page and type [`http://localhost:9000/calculate_multiplication?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **2.76**

### stop the service
in the terminal session where you started the service press `control + d`
