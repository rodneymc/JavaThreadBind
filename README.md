# JavaThreadBind
Library to override the default IP address on a per-thread basis

**Supported platforms:** Linux. Maybe others that support LD_PRELOAD

**Prerequesits**
 * JDK
 * Make
 * GCC

**Compiling**

 * Ensure that JAVA_HOME is set, alternativly set JNI_INCLUDE as a space-separated paths to the JNI header files
 * make
 * (optional): EXTRA_CFLAGS=-whatever make
 * make test
 
**Using in your code**

```java
 ThreadBind.setThreadBindVal("192.0.10.2");
 
 // We can save and restore too
 
 String savedAddr =  ThreadBind.getThreadBindVal();
 ThreadBind.setThreadBindVal("192.0.10.3");
 Foo.connectToDatabase();
 ThreadBind.setThreadBindVal(savedAddr);
 ```
 **Launching your app**
 
 ```
 LD_PRELOAD=bind.so java -Djava.net.preferIPv4Stack=true -Djava.library.path=/path/to/shared/objects com.example.Program args
 ```
 
 **TODO**
  * Support IPv6
  * Support IPv6-mapped IPv4
  
 Webpage coming soon.
