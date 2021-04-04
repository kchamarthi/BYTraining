package com.by.addressbooksystem.uc1;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

@Builder
@Setter
@Getter
@ToString
public class Contact {
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	private String phoneNumber;
	
	private String email;

}
