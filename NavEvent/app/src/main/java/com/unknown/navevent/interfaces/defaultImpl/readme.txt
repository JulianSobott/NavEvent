This file contains default/example implementations of the interfaces in interfaces.java
These classes should only be used for testing purposes. Later they will be replaced with the actual implementations.
By default this classes respond in 'best case', which means they don't call e. g. MainActivityUI.notSupported().

Lifecycle:
	1. First UI will be created (e. g. MainActivityUIDefault or MainActivity at all)
	2. UI creates logic(e. g. MainActivityLogicDefault).
	3. Logic-constructor will save referenze to UI to respond later.