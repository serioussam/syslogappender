package com.github.serioussam.syslogappender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.SyslogMessage;
import com.cloudbees.syslog.sender.SyslogMessageSender;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by peeyushaggarwal on 3/20/17.
 */
public class SyslogAppender<E> extends AppenderBase<E> {
  private Layout<E> layout;
  private SyslogConfig syslogConfig;
  private SyslogMessageSender syslogMessageSender;


  @Override
  protected void append(E loggingEvent) {
    try {
      SyslogMessage syslogMessage =
          syslogConfig.getProtocol().getSyslogMessage(syslogConfig).withSeverity(Severity.fromNumericalCode(getSeverityForEvent(loggingEvent)))
              .withMsg(layout.doLayout(loggingEvent)).withFacility(Facility.USER).withAppName(syslogConfig.getProgramName())
              .withHostname(InetAddress.getLocalHost().getHostName());
      syslogMessageSender.sendMessage(syslogMessage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void start() {
    super.start();

    synchronized (this) {
      syslogMessageSender = syslogConfig.getProtocol().getMessageSender(syslogConfig);
    }
  }

  public int getSeverityForEvent(Object eventObject) {
    if (eventObject instanceof ILoggingEvent) {
      ILoggingEvent event = (ILoggingEvent) eventObject;
      return LevelToSyslogSeverity.convert(event);
    } else {
      return Level.INFO_INT;
    }
  }

  public Layout<E> getLayout() {
    return layout;
  }

  public void setLayout(Layout<E> layout) {
    this.layout = layout;
  }

  /**
   * Getter for property 'syslogConfig'.
   *
   * @return Value for property 'syslogConfig'.
   */
  public SyslogConfig getSyslogConfig() {
    return syslogConfig;
  }

  /**
   * Setter for property 'syslogConfig'.
   *
   * @param syslogConfig Value to set for property 'syslogConfig'.
   */
  public void setSyslogConfig(SyslogConfig syslogConfig) {
    this.syslogConfig = syslogConfig;
  }
}
