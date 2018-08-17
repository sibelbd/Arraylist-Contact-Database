/**
 * ContactDatabase.java
 *
 * This class implements a database of contacts that can be
 * added to, searched, displayed, or items removed.  An ArrayList
 * is used to store the database.
 *
 * @author
 * @version
 */

import java.util.ArrayList;
import java.util.Scanner;

//added iterator to step through arraylist in deletion method
import java.util.Iterator;

/**
 * The ContactDatabase class stores each contact in an arraylist.
 * Methods exist to add new contacts, search contacts, delete, and print contacts
 * to the console.
 */
public class ContactDatabase
{
    private ArrayList<Contact> contacts;		// ArrayList of contact
    private static final int QUIT = 0;			// Menu choices
    private static final int ADD = 1;
    private static final int LISTALL = 2;
    private static final int SEARCH = 3;
    private static final int DELETE = 4;
    //moved scanner here to be used across classes
    //because having multiple scanners is bad and doesn't work well
    //I close this later in the main class
    public static final Scanner scan = new Scanner(System.in);

    /**
     * Default constructor - make a new ArrayList object with parameter type Contact- Done
     */
    ContactDatabase()
    {
        contacts = new ArrayList<Contact>();
    }

    /**
     * inputContact inputs contact information from the keyboard.
     * It then stores this new contact in the contacts ArrayList.
     */
    public void inputContact()
    {
        //declaring strings necessary for contact constructor
        String first=null;
        String last=null;
        String phone=null;
        String email=null;

        //Ask user to enter data for contact
        System.out.println("Enter first name then hit enter:");
        first= scan.nextLine();

        System.out.println("Enter last name then hit enter:");
        last= scan.nextLine();

        System.out.println("Enter phone then hit enter:");
        phone= scan.nextLine();

        //using regular expression to validate phone number
        String regexStr = "^?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$" ;
        int validPhone=0;
        //if statement to test phone against regexStr (if -1 then not match, else if 0 then match)
        while (validPhone==0) {
            if (phone.matches(regexStr)) {
                validPhone = 1;
            } else {
                System.out.println("Invalid phone number, please re-enter and use format XXX-XXX-XXX or XXXXXXXXX.");
                phone=scan.nextLine();
            }
        }

        //use .indexof to validate email address by checking for presence of @
        System.out.println("Enter email then hit enter:");
        email= scan.nextLine();
        int validEmail=0;
        while (validEmail==0){
            if (email.indexOf('@')!=-1) {
                validEmail = 1;
            } else {
                System.out.println("Invalid email address, please re-enter using '@'");
                email=scan.nextLine();
            }
        }


        //notify user contact has been created and create contact
        System.out.println("Your contact is now being created");

        contacts.add(new Contact(first,last,phone,email));

    }

    /**
     * displayAll iterates through the ArrayList of contacts and outputs each one
     * to the screen.
     */
    public void displayAll()
    {
        //for loop to display contacts with a new line in between (to separate objects)
        for (Contact contact: contacts){
            System.out.println(contact);
        }
    }

    /**
     * displayMatch inputs a keyword from the user.
     * It then iterates through the ArrayList of contacts and outputs each one
     * to the screen if the contact information contains the keyword.
     */
    public void displayMatch()
    {
        System.out.println("Enter keyword to search:");
        String search= scan.nextLine();

        //for loop to step through array and check for string in contact objects
        //matches are added to arraylist results to be printed after for loop
        ArrayList <Contact> results= new ArrayList<Contact>();
        for (Contact contact:contacts){
            if (contact.getFirst().contains(search)||
                    contact.getLast().contains(search)||
                    contact.getPhone().contains(search)||
                    contact.getEmail().contains(search)){
                results.add(contact);
            }
        }
        System.out.println(results);

    }

    /**
     * deleteMatch inputs a keyword from the user.
     * It then iterates through the ArrayList of contacts and asks the user
     * if the contact should be deleted, if the contact information contains the keyword.
     */
    public void deleteMatch()
    {
        System.out.println("Enter keyword to search:");
        String search= scan.nextLine();

        ArrayList <Contact> results= new ArrayList<Contact>();

        //using iterator for loop to step through contacts arraylist
        //using same process as displayMatch() to search contacts for matching phrases
        //then adds matching objects to results arraylist
        for (Iterator<Contact> itr = contacts.iterator(); itr.hasNext();){
            Contact contact= itr.next();
            if (contact.getFirst().contains(search)||
                    contact.getLast().contains(search)||
                    contact.getPhone().contains(search)||
                    contact.getEmail().contains(search)){
                results.add(contact);
            }

        }
        //asks user to confirm deletion of contacts in results(printed out)
        System.out.println("Delete the following contact(s)? (Y/N): ");
        for (Contact contact: results){
            System.out.println(contact);
        }
        String delete= scan.nextLine();

        //execute search again this time executing deletion of contacts
        if (delete.equalsIgnoreCase("Y")) {
            for (Iterator<Contact> itr2 = contacts.iterator(); itr2.hasNext(); ) {
                Contact contact = itr2.next();
                if (contact.getFirst().contains(search) ||
                        contact.getLast().contains(search) ||
                        contact.getPhone().contains(search) ||
                        contact.getEmail().contains(search)) {
                    itr2.remove();
                }

            }
        }


    }

    // Main class
    public static void main(String[] args)
    {
        ContactDatabase cdb = new ContactDatabase();

        int choice = ADD;

        // Main menu
        while (choice != QUIT)
        {
            System.out.println();
            System.out.println("Choose from the following:");
            System.out.println("0) Quit");
            System.out.println("1) Add new contact");
            System.out.println("2) List all contacts");
            System.out.println("3) Search contacts by keyword and display");
            System.out.println("4) Search contacts by keyword and remove");
            choice = scan.nextInt();

            //added this because nextInt does not move scanner to next line automatically
            scan.nextLine();

            switch (choice)
            {
                case ADD: 	cdb.inputContact();
                    break;
                case LISTALL: cdb.displayAll();
                    break;
                case SEARCH: cdb.displayMatch();
                    break;
                case DELETE: cdb.deleteMatch();
                    break;
            }
        }
        ContactDatabase.scan.close();
    }

    /**
     * The inner class, Contact, stores the details for a single contact.
     *  There is no error checking on any of the input.  Whatever string is
     *  passed in for a given attribute is accepted.
     */
    class Contact
    {
        private String first, last, phone, email;

        /**
         * Constructors.
         */
        public Contact()
        {
        }

        public Contact(String first, String last, String phone, String email)
        {
            this.first = first;
            this.last = last;
            this.phone = phone;
            this.email = email;
        }

	   /*
	    * Accessor Methods
	    */

        public String getFirst()
        {
            return first;
        }

        public String getLast()
        {
            return last;
        }

        public String getPhone()
        {
            return phone;
        }

        public String getEmail()
        {
            return email;
        }

        /*
         * Mutator Methods
         */
        public void setFirst(String first)
        {
            this.first = first;
        }

        public void setLast(String last)
        {
            this.last = last;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public void setEmail(String em)
        {
            this.email = em;
        }



        /*
         * Return all fields concatenated into a string
         */
        public String toString()
        {
            return last + ", " + first + ". " + phone + ", " + email;
        }


        public boolean equals(Object otherObject)
        {
            if (otherObject ==null)
            {
                return false;
            }
            else if (getClass() != otherObject.getClass())
            {
                return false;
            }
            else
            {
                Contact otherContact = (Contact)otherObject;
                return (first.equals(otherContact.first) &&
                        last.equals(otherContact.last)&&
                        phone.equals(otherContact.phone)&&
                        email.equals(otherContact.email));
            }
        }

    } // end inner class, Contact
} // end class, ContactDatabase