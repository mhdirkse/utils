# utils
Common utilities for my projects. The following utilities are present:
* `AbstractStatusCode`: To be used as super interface for an enum of status codes. These enum values inherit a `format` method and thus link status codes to formatted log messages. Application code can log statusses instead of formatted messages, making testing easier. Test class `AbstractStatusCodeTest` shows how to use `AbstractStatusCode`.
