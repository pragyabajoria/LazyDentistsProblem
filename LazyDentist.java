import java.util.concurrent.*;

/**
 * Main class simulating the Lazy Dentist problem
 * @author pragyabajoria
 *
 */
public class LazyDentist {
	
	/* Tracks the availability of the dentist. */
	protected static Semaphore dentistReady = new Semaphore(0);
	
	/* The number of seats in the waiting room that can be incremented or decremented */
	protected static Semaphore seatCountWriteAccess  = new Semaphore(1);
	
	/* Number of patients currently in the waiting room, ready to be served */
	protected static Semaphore patientReady = new Semaphore(0); 
	
	/* Number of seats at the lazy dentist's office */
	protected static final int SEATS = 3;
	
	/* Total number of seats in the waiting room */
	protected static int numberOfFreeWRSeats = SEATS;
	
	/**
	 * Main method for running the application
	 */
	public static void main(String args[]) {
		// Starts the dentist thread
		Dentist dentist = new Dentist();
		dentist.start();
				
		// Test with one dentist thread and one patient thread
//		Customer customer = new Customer(1);
//		customer.start();
		
		// Test with one dentist thread and two patient threads
//		Customer customer1 = new Customer(1);
//		customer1.start();
//		Customer customer2 = new Customer(2);
//		customer2.start();
			
		/* Test with one dentist thread and 3 patient threads. */
		// Comment the line with n = 5;
//		int n = 3; // number of patients 	
		
		/* Test with one dentist thread and 5 patient threads. */
		int n = 5;
		// Creates 'n' new patient threads
		for (int i = 1; i <= n; i++) {
			Customer customer = new Customer(i);
			customer.start();
			
			// Sleeps for some time to allow threads to get created.
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
		}	
	}
}