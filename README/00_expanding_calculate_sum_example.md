
### [![YouTube Play Icon](http://youtube.com/favicon.ico)](https://www.youtube.com/watch?v=1ObkXurqNs0&index=3&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv) **on [YouTube](https://www.youtube.com/watch?v=1ObkXurqNs0&index=3&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv)**

> **why?** because sometimes after you generate a service and start implementing its logic, your design evolves in a way that requires modifying/adding/removing controllers (api entry points)

# expand "calculate sum" to "calculator"

> 5-10 min: this tutorial explains how to add new features to a service before and after generation

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
      ...
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
+ optionally for eclipse users: open eclipse and import vos_calculate_sum project
  + file **>** import **>** general **>** existing projects into workspace **>** next **>** set "select root directory" to my_services **>** under projects make sure that vos_calculate_sum is selected **>** finish
  + double check the java version used for compiling the project: right click the project **>** properties **>** java compiler **>** enable project specific settings **>** compiler compliance level **>** 1.7 or 1.8
+ open class [HandlerCalculateSum.java](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java) under package `com.vangav.vos_calculate_sum.controllers.calculate_sum`, method [`processRequest`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L86) should be as follows in order to complete the request-to-response logic
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestCalculateSum requestCalculateSum =
      (RequestCalculateSum)request.getRequestJsonBody();
    
    // set response's value
    ((ResponseCalculateSum)request.getResponseBody() ).set(
      requestCalculateSum.a + requestCalculateSum.b);
  }
```
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

# expand "calculate sum" to "calculator" without regenerating the service

in this section we will get the same result from the [expand "calculate sum" to "calculator"](https://github.com/vangav/vos_backend/blob/master/README/00_expanding_calculate_sum_example.md#expand-calculate-sum-to-calculator) without regenerating the service; given the already generated [calculate sum](https://github.com/vangav/vos_calculate_sum) backend service, we will follow the following steps to add a multiplication feature

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

# exercise
> expand this service to a fully fledged calculator service

# next tutorial -> [project's contents](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md)
> explains the building blocks of vangav backend

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
