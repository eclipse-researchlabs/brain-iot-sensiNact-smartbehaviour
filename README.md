# brain-iot-sensiNact-smartbehaviour

brain-iot-sensiNact-smartbehaviour's modules allow to use sensiNact IoT gateway as a Brain-IoT Service, to be automatically deployed by the Brain-IoT Service Fabric when the appropiate `BrainIoTEvents` are propagated through the `EventBus`.


### brain-iot-service-api

In this module, the extended `BraintIoTEvents` and the intermediate data structures allowing to translate them into the appropriate sensiNact's `AccessMethods` are defined

### brain-iot-service-impl

In this module, the mechanism allowing to translate `BraintIoTEvents` into sensiNact's `AccessMethod` requests, and sensiNact's `AccessMethod` responses into `BraintIoTEvents`is implemented

### brain-iot-service-app

This module provides a standalone executable jar archive allowing to launch a sensiNact IoT gateway instance integrated to a Brain-IoT `EventBus` through brain-iot-sensiNact-smartbehaviour api and implementation for a local execution.
