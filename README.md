# WireTap/Detour: 
Using [Cloud Events](https://github.com/cloudevents/spec/blob/v0.2/json-format.md) as messages

## Use Cases/Definition:
* WireTap: Inspect a Message on a channel without modifying the producer/consumer
	
* Detour: Route a message through additional intermediary steps dynamically include and exclude additional steps
	* Context-based router with additional output channels:
		* First channel passes unmodified message directly to original destination
		* Other channels passes unmodified message to different destination (Will be routed back to orginal destination afterwards)

* Mostly used for debugging/validation
* In theory, producer/consumer of messages shouldnt be touched

## Discussion:

* Both of these patterns are designed for MOMs that support queues. Since we use Kafka, they have limited application.
	* Messages in Kafka can not be consumed without deserializing them first. This means that messages must be consumed and deserialized and then produced again. 
		* Re-publishing of messages will change header-fields like message-id -> may be problematic in the future
		* Should be part of another pattern to avoid unnecessary transformations and traffic
	
* Both patterns require a Dynamic Router (See Router) that requires a configuration that can be changed externally
	* A Configuration should include:
		* See [Cloud Events Format](https://github.com/cloudevents/spec/blob/v0.2/json-format.md) 
* The actual implementation should be part of another service/pattern. It doesn't make much sense to just implement a service that basically just duplicates a message. Depending on further decisions.

### Usage: Not advised
The Prototype is only kept as part of the current documentation for the prototyping phase and will not be updated any further.

