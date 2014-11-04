/**
 * Represents the Dentist Thread
 * 
 * @author pragyabajoria
 *
 */
public class Dentist extends Thread {
	
	// Default Constructor
	public Dentist() {}
	
	/**
	 * Run Method for the thread
	 */
	public void run() {
		
		while(true) { // Run in an infinite loop.	
			try {
				System.out.println("PatientReady TRYING to enter Critical Section in Dentist");
				// Try to acquire a patient - if none is available, go to sleep.
				LazyDentist.patientReady.acquire();
				System.out.println("PatientReady ENTERED Critical Section in Dentist");
				
				System.out.println("SeatCountWriteAccess TRYING to enter Critical Section");
				// To modify the number of available seats
				LazyDentist.seatCountWriteAccess.acquire();
				System.out.println("SeatCountWriteAccess ENTERED Critical Section in Dentist");
				
				// One waiting room chair becomes free.
				LazyDentist.numberOfFreeWRSeats+= 1; 
				
				System.out.println("DentistReady signalled by the Dentist");
				// Dentist ready to consult.
				LazyDentist.dentistReady.release(); 
				
				System.out.println("SeatCountWriteAccess signalled by the Dentist");
				// Don't need the lock on the chairs anymore.
				LazyDentist.seatCountWriteAccess.release(); 
				
				// Dentist talks to the patient
				talkToPatient();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Represents the dentist talking to the patient.
	 */
	public void talkToPatient() {
		System.out.println("The dentist is talking to the patient.");
	}
}		