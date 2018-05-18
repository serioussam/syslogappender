Syslog appender for logback with full RFC-5424 support


## syslogappender

A [Logback][] appender that leverages [syslog4j][] to send log messages to
remote systems via syslog compatible to RFC-5424 format.

### Why?

The [existing syslog appender for Logback][logback-syslog-appender] only
provides the ability to send messages via UDP. Using syslog-java-client allows us to
send messages via TCP and optionally to encrypt them by sending over TCP with
TLS. This library also take care of adding message length as per RFC-5424 so log lines containing
new lines work correctly.

### How?

Setup using https://bintray.com/serioussam/oss/com.github.serioussam%3Asyslogappender
Add this to your `pom.xml`:

``` xml
    <dependency>
      <groupId>com.github.serioussam</groupId>
      <artifactId>syslogappender</artifactId>
      <version>1.0.0</version>
    </dependency>
```

Then add the appender to your `logback.xml`.

If not using Maven, download [logback-syslog4j-1.0.0.jar][] and the latest
[syslog4j][] JAR.  Place these files in the classpath, in addition to Logback
itself.

#### Logging via TCP with TLS (recommended)

``` xml
  <appender name="SYSLOG-TLS" class="com.github.serioussam.syslogappender.SyslogAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <pattern>%date %-5level %logger{35} - %message%n</pattern>
    </layout>

    <syslogConfig class="com.github.serioussam.syslogappender.SyslogConfig">
      <!-- remote system to log to -->
      <host>localhost</host>
      <!-- remote port to log to -->
      <port>514</port>
      <!-- program name to log as -->
      <programName>java-app</programName>
      <!-- protocol TCP_TLS -->
      <protocol>TCP_TLS</protocol>
    </syslogConfig>
  </appender> 

  <root level="DEBUG">
    <appender-ref ref="SYSLOG-TLS" />
  </root>
```

#### Logging via TCP

``` xml
  <appender name="SYSLOG-TCP" class="com.github.serioussam.syslogappender.SyslogAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <pattern>%date %-5level %logger{35} - %message%n</pattern>
    </layout>

    <syslogConfig class="com.github.serioussam.syslogappender.SyslogConfig">
      <!-- remote system to log to -->
      <host>localhost</host>
      <!-- remote port to log to -->
      <port>514</port>
      <!-- program name to log as -->
      <programName>java-app</programName>
      <!-- protocol TCP -->
      <protocol>TCP</protocol>
    </syslogConfig>
  </appender> 

  <root level="DEBUG">
    <appender-ref ref="SYSLOG-TCP" />
  </root>
```

#### Logging via UDP

``` xml
  <appender name="SYSLOG-UDP" class="com.github.serioussam.syslogappender.SyslogAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      <pattern>%date %-5level %logger{35} - %message%n</pattern>
    </layout>

    <syslogConfig class="com.github.serioussam.syslogappender.SyslogConfig">
      <!-- remote system to log to -->
      <host>localhost</host>
      <!-- remote port to log to -->
      <port>514</port>
      <!-- program name to log as -->
      <programName>java-app</programName>
      <!-- protocol UDP -->
      <protocol>UDP</protocol>
    </syslogConfig>
  </appender> 

  <root level="DEBUG">
    <appender-ref ref="SYSLOG-UDP" />
  </root>
```


[Logback]: http://logback.qos.ch/
[syslog-java-client]: https://github.com/CloudBees-community/syslog-java-client
[logback-syslog-appender]: http://logback.qos.ch/manual/appenders.html#SyslogAppender
[RFC-5424]: https://tools.ietf.org/html/rfc5424

