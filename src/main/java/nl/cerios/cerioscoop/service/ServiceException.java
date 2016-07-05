package nl.cerios.cerioscoop.service;

public class ServiceException extends RuntimeException {

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}
}