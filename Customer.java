/**
 * Represents the Customer Thread
 * @author Pragya Bajoria
 *
 */
public class Customer extends Thread {
	
	/* Unique identifier for each customer. */
	private int id;
	
	/**
	 * Constructor to initialize the Customer with unique id.
	 */
	public Customer(int id) {
		this.id = id;
	}
	
	/**
	 * Run method for the Customer
	 */
	public void run() {
		
		while(true) {
		 	try {
		 		
		 		System.out.println("SeatCountWriteAccess TRYING to enter Critical Section in Patient" + id);
		 		 // Try to get access to the waiting room chairs
				LazyDentist.seatCountWriteAccess.acquire();
				System.out.println("SeatCountWriteAccess ENTERED Critical Section in Patient " + id);
				
				if (LazyDentist.numberOfFreeWRSeats > 0) {
					
					// Customer sits on a chair, decreasing the number of free seats
			 		LazyDentist.numberOfFreeWRSeats--; 
			 		
			 		System.out.println("PatientReady signalled by the Patient " + id);
			 		// Notifies the dentist, who is waiting until there is a patient
			 		LazyDentist.patientReady.release(); 
			 		
			 		System.out.println("SeatCountWriteAccess signalled by the Patient " + id);
			 		// No need to lock the chairs anymore
			 		LazyDentist.seatCountWriteAccess.release(); 
			 		
			 		try {
			 			System.out.println("DentistReady TRYING to enter Critical Section in Patient " + id);
			 			// Wait until the dentist is ready
			 			LazyDentist.dentistReady.acquire();
			 			System.out.println("DentistReady ENTERED Critical Section in Patient " + id);
			 			
			 			// Consult the dentist
			 			consultDentist();
			 			
			 		} catch (InterruptedException e) {
			 			e.printStackTrace();
			 		}
				} else { // If there are no free seats
					System.out.println("No free seats available. Patient " + id + " is leaving the dentist");
					
					// Release lock on seats
					System.out.println("SeatCountWriteAccess signalled by the Patient " + id);
					LazyDentist.seatCountWriteAccess.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Shows the customer consulting with the dentist
	 */
	public void consultDentist() {
		System.out.println("Patient " + id + " is consulting the dentist."); 
	}
}