package com.by.addressbooksystem.uc1;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
public class AddressBook {

	private Set<Contact> addressBook;

}
