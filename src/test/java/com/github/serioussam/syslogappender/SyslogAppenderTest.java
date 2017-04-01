package com.github.serioussam.syslogappender;

import static org.junit.Assert.assertTrue;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.productivity.java.syslog4j.server.SyslogServer;
import org.productivity.java.syslog4j.server.impl.event.printstream.PrintStreamSyslogServerEventHandler;
import org.productivity.java.syslog4j.server.impl.net.tcp.TCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.tcp.ssl.SSLTCPNetSyslogServerConfig;
import org.productivity.java.syslog4j.server.impl.net.udp.UDPNetSyslogServerConfig;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by peeyushaggarwal on 3/20/17.
 */
public class SyslogAppenderTest {
  private ByteArrayOutputStream serverStream;

  @Before
  public void setUp() throws Exception {
    serverStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(serverStream);

    final TCPNetSyslogServerConfig tcpNetSyslogServerConfig = new TCPNetSyslogServerConfig(45553);
    tcpNetSyslogServerConfig.addEventHandler(new PrintStreamSyslogServerEventHandler(ps));

    final UDPNetSyslogServerConfig udpNetSyslogServerConfig = new UDPNetSyslogServerConfig(45553);
    udpNetSyslogServerConfig.addEventHandler(new PrintStreamSyslogServerEventHandler(ps));

    final SSLTCPNetSyslogServerConfig ssltcpNetSyslogServerConfig = new SSLTCPNetSyslogServerConfig();
    ssltcpNetSyslogServerConfig.setPort(45554);
    ssltcpNetSyslogServerConfig.addEventHandler(new PrintStreamSyslogServerEventHandler(ps));
    final String keyStore = this.getClass().getClassLoader().getResource("test-keystore.jks").getFile();
    ssltcpNetSyslogServerConfig.setKeyStore(keyStore);
    ssltcpNetSyslogServerConfig.setKeyStorePassword("password");
    ssltcpNetSyslogServerConfig.setTrustStore(keyStore);
    ssltcpNetSyslogServerConfig.setTrustStorePassword("password");

    SyslogServer.createThreadedInstance("testTcp", tcpNetSyslogServerConfig);
    SyslogServer.createThreadedInstance("testUdp", udpNetSyslogServerConfig);
    SyslogServer.createThreadedInstance("testTls", ssltcpNetSyslogServerConfig);
  }

  @After
  public void tearDown() throws Exception {
    SyslogServer.shutdown();
  }

  @Test
  public void testUdpSender() throws JoranException, InterruptedException {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);
    context.reset();
    configurator.doConfigure(this.getClass().getClassLoader().getResourceAsStream("logback-syslog-udp.xml"));

    Logger logger = context.getLogger("test-udp");
    logger.info("test message over udp");

    context.stop();
    Thread.sleep(100);

    final String serverData = serverStream.toString();
    assertTrue("Server received: " + serverData, serverData.contains("test message over udp"));
  }

  @Test
  public void testTcpSender() throws JoranException, InterruptedException {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);
    context.reset();
    configurator.doConfigure(this.getClass().getClassLoader().getResourceAsStream("logback-syslog-tcp.xml"));

    Logger logger = context.getLogger("test-tcp");
    logger.info("test message over tcp");

    context.stop();
    Thread.sleep(100);

    final String serverData = serverStream.toString();
    assertTrue("Server received: " + serverData, serverData.contains("test message over tcp"));
  }

  @Test
  public void testTlsSender() throws JoranException, InterruptedException {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);
    context.reset();
    configurator.doConfigure(this.getClass().getClassLoader().getResourceAsStream("logback-syslog-tls.xml"));

    Logger logger = context.getLogger("test-tls");
    logger.info("test message over tls");

    context.stop();
    Thread.sleep(100);

    final String serverData = serverStream.toString();
    assertTrue("Server received: " + serverData, serverData.contains("test message over tls"));
  }

}
