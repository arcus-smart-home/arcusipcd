// Copyright (c) 2007-2010 Motorola, Inc.  All rights reserved.

package com.arcussmarthome.ipcd.server.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Based loosely on the DefaultThreadFactory implementation in 
 * edu.emory.mathcs.backport.java.util.concurrent.Executors
 * 
 */
public class DaemonThreadFactory implements ThreadFactory
{
    static final AtomicInteger poolNumber = new AtomicInteger(1);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;
    final int priority;

    public DaemonThreadFactory() {
    	this.priority = Thread.NORM_PRIORITY;
    	SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }
    
    public DaemonThreadFactory(String name) {
    	this(name, Thread.NORM_PRIORITY);
    }
    
    public DaemonThreadFactory(String name, int priority) {
    	this.priority = priority;
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + name + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                              namePrefix + threadNumber.getAndIncrement(),
                              0);
        if (!t.isDaemon()) t.setDaemon(true);
        if (t.getPriority() != priority) t.setPriority(priority);
        return t;
    }

    public Thread newThread(String name, Runnable r) {
        Thread t = new Thread(group, r,
                              namePrefix + name,
                              0);
        if (!t.isDaemon()) t.setDaemon(true);
        if (t.getPriority() != priority) t.setPriority(priority);
        return t;
    }
}
