package com.fbs.totalbeginner.tutorial;

import junit.framework.TestCase;

public class BookTest extends TestCase {
	public void testBook(){
		Book book = new Book ("Great Expectation");
		Book b1 = book;
		assertEquals("Great Expectation", b1.title);
		assertEquals ("unknown author", b1.author);
	}

	public void testGetPerson(){
		Book b2 = new Book ("War And Peace");
		Person p2 = new Person();
		p2.setName("Elvis");
		
		b2.setPerson (p2);
//		Person testPerson = b2.getPerson();
//		String testName = testPerson.getName();
		String testName = b2.getPerson().getName();
		assertEquals ("Elvis", testName);
	}
	
	public void testToString() {
		Book b2 = new Book ("War And Peace");
		Person p2 = new Person();
		p2.setName("Elvis");
		
		assertEquals("War And Peace by unknown author; Available", b2.toString());
		
		b2.setPerson(p2);
		assertEquals("War And Peace by unknown author; Checked out to Elvis", b2.toString());
		
	}
}
