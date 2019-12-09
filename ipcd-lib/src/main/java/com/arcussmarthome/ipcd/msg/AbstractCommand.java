package com.arcussmarthome.ipcd.msg;

public abstract class AbstractCommand implements ServerMessage {

	protected CommandType command;
	protected String txnid;
	
	protected AbstractCommand(CommandType command) {
		this(command, null);
	}
	
	public AbstractCommand(CommandType command, String txnId) {
		this.command = command;
		this.txnid = txnId;
	}

	public CommandType getCommand() {
		return command;
	}

	public String getTxnid() {
		return txnid;
	}
	
	public void setTrxnId(String txnId) {
		this.txnid = txnId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + ((txnid == null) ? 0 : txnid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractCommand other = (AbstractCommand) obj;
		if (command != other.command)
			return false;
		if (txnid == null) {
			if (other.txnid != null)
				return false;
		} else if (!txnid.equals(other.txnid))
			return false;
		return true;
	}

	
	
}
