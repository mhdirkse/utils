# utils
Common utilities for my projects. The following utilities are present:
* `AbstractStatusCode`: To be used as super interface for an enum of status codes. These enum values inherit a `format` method and thus link status codes to formatted log messages. Application code can log statusses instead of formatted messages, making testing easier. Test class `AbstractStatusCodeTest` shows how to use `AbstractStatusCode`.
* `Imperative`: A utility class that is a workaround in Java 8 for Java 9's takeWhile.
Runs a list of functions returning boolean. Method
`runWhileTrue` stops executing after the first one that returns false. Method `runWhileFalse`
stops executing after the first one that returns true. Both methods can also be invoked with
a second argument that is a Runnable. This one is executed in case all the boolean functions
have executed.  
