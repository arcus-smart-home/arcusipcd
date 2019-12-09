package com.arcussmarthome.ipcd.client.commands;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CommandQueue {
	private final BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<Command>(20);
	
	public void insertCommand(Command command) throws InterruptedException {
		commandQueue.put(command);
	}
	
	public Command poll() throws InterruptedException {
		return commandQueue.take();
	}
}
