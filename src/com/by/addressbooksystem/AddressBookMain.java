package com.by.addressbooksystem;

import static java.lang.System.out;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;

import com.by.addressbooksystem.uc1.AddressBook;
import com.by.addressbooksystem.uc1.Contact;

public class AddressBookMain {

	public static void main(String[] args) {
		out.println("WELCOME TO ADDRESS BOOK");
		Set<Contact> setContact = new HashSet<>();

		Scanner scanValue = new Scanner(System.in);

		out.println("Enter count");
		int iCount = scanValue.nextInt();

		scanValue.nextLine();
		// Below for loop takes user input, creates Contacts, adds them to
		// ArrayList, then set it in AddressBook object
		for (int i = 0; i < iCount; i++) {
			addContact(setContact, scanValue);
		}

		AddressBook addressBook = AddressBook.builder().addressBook(setContact).build();
		scanValue.nextLine();
		while (true) {
			out.println("Enter Operation to be performed \n" + "1.Add New Contact \n" + "2.Edit Contact \n"
					+ "3.Delete Contact \n");
			int iVal = scanValue.nextInt();
			scanValue.nextLine();
			switch (iVal) {
			case 1:
				addContact(setContact, scanValue);
				break;
			case 2:
				out.println(
						"Enter fieldnumber and existing fieldValue and new fieldValue of contact you want to modify \n"
								+ "1.FirstName \n" + "2.LastName \n" + "3.Address \n" + "4.City \n" + "5.State \n"
								+ "6.Zip \n" + "7.Phonenumber \n" + "8.EmailID \n");

				int iFieldNo = scanValue.nextInt();
				scanValue.nextLine();
				String strOldVal = scanValue.nextLine();
				Optional<String> optOldVal = ofNullable(strOldVal);
				String strNewVal = scanValue.nextLine();

				editContact(setContact, optOldVal, iFieldNo, strNewVal);
				break;
			case 3:
				out.println("Enter fieldValue of contact to be deleted");
				String strVal = scanValue.nextLine();
				Optional<String> optVal = ofNullable(strVal);
				deleteContact(setContact, optVal);
				break;
			}
			out.println("setContact is:" + setContact);

			addressBook.setAddressBook(setContact);
		}
	}

	private static void editContact(Set<Contact> contactSet, Optional<String> strVal, int iFieldModified,
			String newVal) {
		Optional<Contact> contact = findContact(contactSet, strVal);
		if (contact.isPresent()) {
			Contact contactToBeModified = contact.get();
			switch (iFieldModified) {
			case 1:
				contactToBeModified.setFirstName(newVal);
				break;
			case 2:
				contactToBeModified.setLastName(newVal);
				break;
			case 3:
				contactToBeModified.setAddress(newVal);
				break;
			case 4:
				contactToBeModified.setCity(newVal);
				break;
			case 5:
				contactToBeModified.setState(newVal);
				break;
			case 6:
				contactToBeModified.setZip(newVal);
				break;
			case 7:
				contactToBeModified.setPhoneNumber(newVal);
				break;
			case 8:
				contactToBeModified.setEmail(newVal);
				break;
			}
		}
	}

	private static void deleteContact(Set<Contact> contactSet, Optional<String> strVal) {
		Optional<Contact> contact = findContact(contactSet, strVal);
		if (contact.isPresent())
			contactSet.remove(contact.get());
	}

	private static Optional<Contact> findContact(Set<Contact> contactSet, Optional<String> strVal) {
		Optional<Contact> contact = null;
		if (strVal.isPresent()) {
			out.println(":Value is:" + strVal);
			String strCmpVal = strVal.get();
			contact = ofNullable(contactSet.stream().peek(x -> out.println(x))
					.filter(x -> strCmpVal.equals(x.getFirstName()) || strCmpVal.equals(x.getLastName())
							|| strCmpVal.equals(x.getAddress()) || strCmpVal.equals(x.getCity())
							|| strCmpVal.equals(x.getState()) || strCmpVal.equals(x.getZip())
							|| strCmpVal.equals(x.getPhoneNumber()) || strCmpVal.equals(x.getEmail()))
					.collect(Collectors.toList()).get(0));
			out.println("contact after search:" + contact.toString());
		}

		return contact;
	}

	private static void addContact(Set<Contact> contactSet, Scanner scanValue) {
		System.out.println("Enter firstname, lastname, address, city, state, zip, phonenumber, email in that order");

		String strFirstName = scanValue.nextLine();
		String strLastName = scanValue.nextLine();
		String strAddress = scanValue.nextLine();
		String strCity = scanValue.nextLine();
		String strState = scanValue.nextLine();
		String strZip = scanValue.nextLine();
		String strPhNumber = scanValue.nextLine();
		String strEmail = scanValue.nextLine();

		/*
		 * String strFirstName = "Kranthi"; String strLastName = "Kumar"; String
		 * strAddress = "Kammanahalli"; String strCity = "Bangalore"; String
		 * strState = "Karnataka"; String strZip = "560076"; String strPhNumber
		 * = "9550161212"; String strEmail = "kranthi.chamarthi@by.com";
		 */

		Contact contct = Contact.builder().firstName(strFirstName).lastName(strLastName).address(strAddress)
				.city(strCity).state(strState).zip(strZip).phoneNumber(strPhNumber).email(strEmail).build();
		out.println(contct.toString());
		contactSet.add(contct);
	}

}
