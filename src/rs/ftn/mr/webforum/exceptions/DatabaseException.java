package rs.ftn.mr.webforum.exceptions;

public class DatabaseException extends RuntimeException  {
	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(String message, Throwable t) {
		super(message, t);
		this.setStackTrace(t.getStackTrace());
	}
	
	public DatabaseException(Throwable t) {
		super(t);
		this.setStackTrace(t.getStackTrace());
	}
}



