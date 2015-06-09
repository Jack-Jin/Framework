package eceep.milestone.test;

import org.junit.Test;

import eceep.milestone.*;
import eceep.milestone.impl.MilestoneService;
import eceep.milestone.impl.StepDefault;

public class MilestoneTest {
	/**
	 * Milestone: Step00 --- Step01 --- Step02 --- Step03              
	 * Milestone: Step00 --- Step04 --- Step06 --- Step07              
	 * ----------------------------------------------------------------
	 * Steps tree: Step00 -+- Step01 --- Step02 --- Step03             
	 *                     +- Step04 -+- Step05                        
	 *                     |          +- Step06 --- Step07             
	 *                     +- Step08 --- Step09                        
	 */

	@Test
	public void myTest() {
		Milestone<StepDefault> milestone = MilestoneService.getInstance();

		StepDefault step00 = new StepDefault(0, "Step00", "Step00 Title", "Step00 URI", true);
		StepDefault step01 = new StepDefault(0, "Step01", "Step01 Title", "Step01 URI", true);
		StepDefault step02 = new StepDefault(0, "Step02", "Step02 Title", "Step02 URI", true);
		StepDefault step03 = new StepDefault(0, "Step03", "Step03 Title", "Step03 URI", true);
		StepDefault step04 = new StepDefault(0, "Step04", "Step04 Title", "Step04 URI", true);
		StepDefault step05 = new StepDefault(0, "Step05", "Step05 Title", "Step05 URI", true);
		StepDefault step06 = new StepDefault(0, "Step06", "Step06 Title", "Step06 URI", true);
		StepDefault step07 = new StepDefault(0, "Step07", "Step07 Title", "Step07 URI", true);
		StepDefault step08 = new StepDefault(0, "Step08", "Step08 Title", "Step08 URI", true);
		StepDefault step09 = new StepDefault(0, "Step09", "Step09 Title", "Step09 URI", true);

		milestone.addFirst(step00);

		milestone.addChild(step00, step01).addChild(step01, step02).addChild(step02, step03);

		milestone.addChild(step00, step04).addChild(step04, step05);

		milestone.addChild(step04, step06).addChild(step06, step07);

		milestone.addChild(step00, step08).addChild(step08, step09);

		milestone.goTop();

	}

}
