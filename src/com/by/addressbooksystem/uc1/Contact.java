package com.by.addressbooksystem.uc1;

import java.util.Comparator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contact{
	
	@EqualsAndHashCode.Include
	private String firstName;
	
	@EqualsAndHashCode.Include
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	private String phoneNumber;
	
	private String email;
	
	private String cmpVal;
}
