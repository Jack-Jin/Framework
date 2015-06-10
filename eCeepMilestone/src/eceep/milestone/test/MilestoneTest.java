package eceep.milestone.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eceep.milestone.*;
import eceep.milestone.impl.MilestoneService;
import eceep.milestone.impl.StepDefault;

public class MilestoneTest {
	/**
	 * ----------------------------------------------------------------
	 * Steps tree: Step00 -+- Step01 --- Step02 --- Step03             
	 *                     +- Step04 -+- Step05                        
	 *                     |          +- Step06 --- Step07             
	 *                     +- Step08 --- Step09                        
	 * ----------------------------------------------------------------
	 */
	Milestone<StepDefault> milestone;

	StepDefault step00;
	StepDefault step01;
	StepDefault step02;
	StepDefault step03;
	StepDefault step04;
	StepDefault step05;
	StepDefault step06;
	StepDefault step07;
	StepDefault step08;
	StepDefault step09;

	@Before
	public void prepare() {
		milestone = MilestoneService.getInstance();
		
		step00 = new StepDefault("Step00", "Step00 Title", "Step00 URI", true);
		step01 = new StepDefault("Step01", "Step01 Title", "Step01 URI", true);
		step02 = new StepDefault("Step02", "Step02 Title", "Step02 URI", true);
		step03 = new StepDefault("Step03", "Step03 Title", "Step03 URI", true);
		step04 = new StepDefault("Step04", "Step04 Title", "Step04 URI", true);
		step05 = new StepDefault("Step05", "Step05 Title", "Step05 URI", true);
		step06 = new StepDefault("Step06", "Step06 Title", "Step06 URI", true);
		step07 = new StepDefault("Step07", "Step07 Title", "Step07 URI", true);
		step08 = new StepDefault("Step08", "Step08 Title", "Step08 URI", true);
		step09 = new StepDefault("Step09", "Step09 Title", "Step09 URI", true);

		milestone.addFirst(step00);

		milestone.addChild(step00, step01).addChild(step01, step02).addChild(step02, step03);

		milestone.addChild(step00, step04).addChild(step04, step05);

		milestone.addChild(step04, step06).addChild(step06, step07);

		milestone.addChild(step00, step08).addChild(step08, step09);

		milestone.goTop();

	}

	@Test
	public void myTest() {

		// 1. step00 --> step01 --> step02 --> step03 
		// -------------------------------------------------------
		System.out.println("1. step00 --> step01 --> step02 --> step03");
		System.out.println("---------------------------------------");
		StepDefault step = milestone.next(step00);
		Assert.assertEquals(step.getURI(), step00.getURI());
		System.out.println("Step00: " + step.getURI());

		step = milestone.next(step01);
		Assert.assertEquals(step.getURI(), step01.getURI());
		System.out.println("Step01: " + step.getURI());

		step = milestone.next(step02);
		Assert.assertEquals(step.getURI(), step02.getURI());
		System.out.println("Step02: " + step.getURI());

		step = milestone.next(step03);
		Assert.assertEquals(step.getURI(), step03.getURI());
		System.out.println("Step03: " + step.getURI());

		System.out.println("");
		// -------------------------------------------------------

		// 2. step02 --> step01 --> step00
		// -------------------------------------------------------
		System.out.println("2. step02 --> step01 --> step00");
		System.out.println("----------------------------");
		step = milestone.prev();
		Assert.assertEquals(step.getURI(), step02.getURI());
		System.out.println("Step02: " + step.getURI());

		step = milestone.prev();
		Assert.assertEquals(step.getURI(), step01.getURI());
		System.out.println("Step01: " + step.getURI());

		step = milestone.prev();
		Assert.assertEquals(step.getURI(), step00.getURI());
		System.out.println("Step00: " + step.getURI());

		System.out.println("");
		// -------------------------------------------------------

		// 3. step04 --> step05
		// -------------------------------------------------------
		System.out.println("3. step04 --> step05");
		System.out.println("----------------------------");
		step = milestone.next(step04);
		Assert.assertEquals(step.getURI(), step04.getURI());
		System.out.println("Step04: " + step.getURI());

		step = milestone.next(step05);
		Assert.assertEquals(step.getURI(), step05.getURI());
		System.out.println("Step05: " + step.getURI());

		System.out.println("");

		// 4. step04 --> step00
		// -------------------------------------------------------
		System.out.println("4. step04 --> step00");
		System.out.println("----------------------------");
		step = milestone.prev();
		Assert.assertEquals(step.getURI(), step04.getURI());
		System.out.println("Step04: " + step.getURI());

		step = milestone.prev();
		Assert.assertEquals(step.getURI(), step00.getURI());
		System.out.println("Step00: " + step.getURI());

		System.out.println("");

		// 5. step04 --> step05
		// -------------------------------------------------------
		System.out.println("5. step04 --> step05");
		System.out.println("----------------------------");
		step = milestone.next();
		Assert.assertEquals(step.getURI(), step04.getURI());
		System.out.println("Step04: " + step.getURI());

		step = milestone.next();
		Assert.assertEquals(step.getURI(), step05.getURI());
		System.out.println("Step05: " + step.getURI());

		System.out.println("");

		// Go step06
		// 6. step00 --> step04 --> step06 --> step07
		// -------------------------------------------------------
		System.out.println("6. step00 --> step04 --> step06 --> step07");
		System.out.println("----------------------------");
		step = milestone.go(step06);
		//Assert.assertEquals(step.getURI(), step06.getURI());
		System.out.println("Step06: " + step.getURI());

		step = milestone.next();
		Assert.assertEquals(step.getURI(), step07.getURI());
		System.out.println("Step07: " + step.getURI());

		// Go top.
		System.out.println("Go top ...");
		step = milestone.goTop();
		Assert.assertEquals(step.getURI(), step00.getURI());
		System.out.println("Step00: " + step.getURI());

		step = milestone.next();
		Assert.assertEquals(step.getURI(), step04.getURI());
		System.out.println("Step04: " + step.getURI());

		step = milestone.next();
		Assert.assertEquals(step.getURI(), step06.getURI());
		System.out.println("Step06: " + step.getURI());

		step = milestone.next();
		Assert.assertEquals(step.getURI(), step07.getURI());
		System.out.println("Step07: " + step.getURI());

		System.out.println("");
	}

	@Test
	public void serializableTest() throws IOException, ClassNotFoundException {
		System.out.println("Serialize ...");
		byte[] binary = milestone.serialize();
		System.out.println("Serialize length: " + binary.length);

		System.out.println();

		System.out.println("Deserialize ...");
		Milestone<StepDefault> milestone1 = milestone.deserialize(binary);

		// Go step06
		// 6. step00 --> step04 --> step06 --> step07
		// -------------------------------------------------------
		System.out.println("6. step00 --> step04 --> step06 --> step07");
		System.out.println("----------------------------");
		StepDefault step = milestone1.go(step06);
		//Assert.assertEquals(step.getURI(), step06.getURI());
		System.out.println("Step06: " + step.getURI());

		step = milestone1.next();
		Assert.assertEquals(step.getURI(), step07.getURI());
		System.out.println("Step07: " + step.getURI());

		// Go top.
		System.out.println("Go top ...");
		step = milestone1.goTop();
		Assert.assertEquals(step.getURI(), step00.getURI());
		System.out.println("Step00: " + step.getURI());

		step = milestone1.next();
		Assert.assertEquals(step.getURI(), step04.getURI());
		System.out.println("Step04: " + step.getURI());

		step = milestone1.next();
		Assert.assertEquals(step.getURI(), step06.getURI());
		System.out.println("Step06: " + step.getURI());

		step = milestone1.next();
		Assert.assertEquals(step.getURI(), step07.getURI());
		System.out.println("Step07: " + step.getURI());

		System.out.println("");
	}
}
