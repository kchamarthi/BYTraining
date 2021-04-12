package com.by.addressbooksystem;

import static java.lang.System.out;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.Comparator;

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
		printSortedList(setContact);
		
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
				printSortedList(setContact);
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
				printSortedList(setContact);
				break;
			case 3:
				out.println("Enter fieldValue of contact to be deleted");
				String strVal = scanValue.nextLine();
				Optional<String> optVal = ofNullable(strVal);
				deleteContact(setContact, optVal);
				printSortedList(setContact);
				break;
			}
			out.println("setContact is:" + setContact);

			addressBook.setAddressBook(setContact);
		}
	}
	
	private static void printSortedList(Set<Contact> setContact) {
		out.println("After sorting based on city, state and zip:");
		out.println(sortContact(setContact,"city"));
		out.println(sortContact(setContact,"state"));
		out.println(sortContact(setContact,"zip"));
		out.println(sortContact(setContact,"fname"));
	}

	private static void editContact(Set<Contact> contactSet, Optional<String> strVal, int iFieldModified,
			String newVal) {
		Optional<Contact> contact = findContact(contactSet, strVal,"Y");
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
	
	private static List<Contact> sortContact(Set<Contact> contactSet, String fieldName) {

		switch (fieldName) {
		case "city":
			return ofNullable(
					contactSet.stream().sorted(Comparator.comparing(Contact::getCity)).collect(Collectors.toList()))
							.orElse(new ArrayList<Contact>());
		case "state":
			return ofNullable(
					contactSet.stream().sorted(Comparator.comparing(Contact::getState)).collect(Collectors.toList()))
							.orElse(new ArrayList<Contact>());
		case "zip":
			return ofNullable(
					contactSet.stream().sorted(Comparator.comparing(Contact::getZip)).collect(Collectors.toList()))
							.orElse(new ArrayList<Contact>());
		case "fname":
			return ofNullable(
					contactSet.stream().sorted(Comparator.comparing(Contact::getFirstName)).collect(Collectors.toList()))
							.orElse(new ArrayList<Contact>());
		default:
			return new ArrayList<Contact>();
		}

	}
	
	private static List<Contact> sortContactByState(Set<Contact> contactSet) {
		return ofNullable(contactSet.stream().sorted(Comparator.comparing(Contact::getState)).collect(Collectors.toList())).orElse(new ArrayList<Contact>());
	}
	
	private static List<Contact> sortContactByZip(Set<Contact> contactSet) {
		return ofNullable(contactSet.stream().sorted(Comparator.comparing(Contact::getZip)).collect(Collectors.toList())).orElse(new ArrayList<Contact>());
	}

	private static void deleteContact(Set<Contact> contactSet, Optional<String> strVal) {
		Optional<Contact> contact = findContact(contactSet, strVal,"Y");
		if (contact.isPresent())
			contactSet.remove(contact.get());
	}

	private static Optional<Contact> findContact(Set<Contact> contactSet, Optional<String> strVal, String strCityOrState) {
		Optional<Contact> contact = null;
		if (strVal.isPresent()) {
			out.println(":Value is:" + strVal);
			String strCmpVal = strVal.get();
			contact = ofNullable(contactSet.stream().map(x -> {
				x.setCmpVal(strCmpVal);
				return x;
			}).filter(AddressBookMain::isMatch).collect(Collectors.toList()).get(0));
			
			Optional<Long> lCityCount = ofNullable(contactSet.stream().filter(x->x.getCity().equals(strVal.get())).peek(System.out::println).count());
			Optional<Long> lStateCount = ofNullable(contactSet.stream().filter(x->x.getState().equals(strVal.get())).peek(System.out::println).count());
			if(lCityCount.isPresent())
				out.println(lCityCount.get());
			if(lStateCount.isPresent())
				out.println(lStateCount.get());
			
			out.println("contact after search:" + contact.toString());
		}

		return contact;
	}

	private static boolean isMatch(Contact cntct) { 
		String strCmpVal = cntct.getCmpVal();
		if (strCmpVal.equals(cntct.getFirstName()) || strCmpVal.equals(cntct.getLastName())
				|| strCmpVal.equals(cntct.getAddress()) || strCmpVal.equals(cntct.getCity())
				|| strCmpVal.equals(cntct.getState()) || strCmpVal.equals(cntct.getZip())
				|| strCmpVal.equals(cntct.getPhoneNumber()) || strCmpVal.equals(cntct.getEmail())) {
			return true;
		}
		return false;
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

		
		/*String strFirstName = "Kranthi";
		String strLastName = "Kumarr";
		String strAddress = "dfghjkl";
		String strCity = "sdfghjkl";
		String strState = "cvbnm";
		String strZip = "2345678";
		String strPhNumber = "123456788";
		String strEmail = "kr.c@b.com";
		
		String strFirstNamee = "Kranthi";
		String strLastNamee = "Kumar";
		String strAddresss = "ghjkl";
		String strCityy = "xcvbnm";
		String strStatee = "cvbnm";
		String strZipp = "234567890";
		String strPhNumberr = "123456788";
		String strEmaill = "kr.c@b.com";*/
		 

		Contact contct = Contact.builder().firstName(strFirstName).lastName(strLastName).address(strAddress)
				.city(strCity).state(strState).zip(strZip).phoneNumber(strPhNumber).email(strEmail).build();
		/*Contact contctNew = Contact.builder().firstName(strFirstNamee).lastName(strLastNamee).address(strAddresss)
				.city(strCityy).state(strStatee).zip(strZipp).phoneNumber(strPhNumberr).email(strEmaill).build();*/
		//out.println(contct.toString());
		boolean isContactPresent = contactSet.stream().anyMatch(contct::equals);
		if(!isContactPresent)
			contactSet.add(contct);
			
	}

}
