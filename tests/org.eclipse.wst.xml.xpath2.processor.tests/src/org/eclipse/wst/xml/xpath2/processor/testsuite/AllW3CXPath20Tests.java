package org.eclipse.wst.xml.xpath2.processor.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllW3CXPath20Tests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for W3C XPath 2.0 test suite.");
		//$JUnit-BEGIN$
		suite.addTestSuite(NumericUnaryPlusTest.class);
		suite.addTestSuite(SequenceTypeSyntaxTest.class);
		suite.addTestSuite(NumericGTTest.class);
		suite.addTestSuite(DateTimeAddDTDTest.class);
		suite.addTestSuite(TimeSubtractTest.class);
		suite.addTestSuite(CombNodeSeqTest.class);
		suite.addTestSuite(DayTimeDurationDivideDTDTest.class);
		suite.addTestSuite(BooleanLTTest.class);
		suite.addTestSuite(NumericMultiplyTest.class);
		suite.addTestSuite(AxesTest.class);
		suite.addTestSuite(DateSubtractDTDTest.class);
		suite.addTestSuite(NodeTestTest.class);
		suite.addTestSuite(TimeAddDTDTest.class);
		suite.addTestSuite(BooleanEqualTest.class);
		suite.addTestSuite(YearMonthDurationAddTest.class);
		suite.addTestSuite(DayTimeDurationAddTest.class);
		suite.addTestSuite(dateTimesSubtractTest.class);
		suite.addTestSuite(InternalContextExprTest.class);
		suite.addTestSuite(DayTimeDurationMultiplyTest.class);
		suite.addTestSuite(AbbrAxesTest.class);
		suite.addTestSuite(DateTimeSubtractDTDTest.class);
		suite.addTestSuite(NumericSubtractTest.class);
		suite.addTestSuite(DayTimeDurationDivideTest.class);
		suite.addTestSuite(NameTestTest.class);
		suite.addTestSuite(NumericLTTest.class);
		suite.addTestSuite(NumericDivideTest.class);
		suite.addTestSuite(YearMonthDurationSubtractTest.class);
		suite.addTestSuite(RangeExprTest.class);
		suite.addTestSuite(BooleanGTTest.class);
		suite.addTestSuite(DateSubtractYMDTest.class);
		suite.addTestSuite(LiteralsTest.class);
		suite.addTestSuite(TimeSubtractDTDTest.class);
		suite.addTestSuite(YearMonthDurationMultiplyTest.class);
		suite.addTestSuite(YearMonthDurationDivideTest.class);
		suite.addTestSuite(NumericAddTest.class);
		suite.addTestSuite(commaOpTest.class);
		suite.addTestSuite(NumericIntegerDivideTest.class);
		suite.addTestSuite(FilterExprTest.class);
		suite.addTestSuite(YearMonthDurationDivideYMDTest.class);
		suite.addTestSuite(PredicatesTest.class);
		suite.addTestSuite(ExternalContextExprTest.class);
		suite.addTestSuite(NumericModTest.class);
		suite.addTestSuite(DateTimeSubtractYMDTest.class);
		suite.addTestSuite(NumericEqualTest.class);
		suite.addTestSuite(DatesSubtractTest.class);
		suite.addTestSuite(DateAddDTDTest.class);
		suite.addTestSuite(DayTimeDurationSubtractTest.class);
		suite.addTestSuite(NumericUnaryMinusTest.class);
		suite.addTestSuite(YearMonthDurationAddDTTest.class);
		suite.addTestSuite(DateAddYMDTest.class);
		suite.addTestSuite(ParenExprTest.class);
		suite.addTestSuite(UnabbrAxesTest.class);
		//$JUnit-END$
		return suite;
	}

}
