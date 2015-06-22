package eceep.quotation.test;

import java.util.UUID;

import org.junit.Test;

public class QuotationTest {
	@Test
	public void myTest(){
		UUID id = UUID.randomUUID();
		System.out.println("Random ID: " + id.toString());
	}
}
