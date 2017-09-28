import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class IataExchangeRateEntryAdjustmentTest {
	private IataExchangeRateEntryAdjustment objectToTest;
	private IataExchangeRateDataSet exchangeRateDataSetActual = new IataExchangeRateDataSet();
	private Set<IataExchangeRateData> exchangeRateDataSetExpected = new HashSet<>();
	private SimpleDateFormatStringToDate formatter = new SimpleDateFormatStringToDate();

	// Exchange rate attributes for exchangeRateDataSetActual
	private Double[] exchangeRate;
	private String[] currencyIsoCode;
	private Date[] from;
	private Date[] to;

	// Exchange rate attributes for user input
	private Double userInputExchangeRate;
	private String userInputCurrencyIsoCode;
	private Date userInputFrom;
	private Date userInputTo;

	@Before
	public void setup() throws ParseException {
		exchangeRate = new Double[] { 1.0, 2.0, 3.0 };
		currencyIsoCode = new String[] { "USD", "YEN" };
		from = new Date[] { formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("01.02.2000"),
				formatter.parseStringToDate("01.03.2000") };
		to = new Date[] { formatter.parseStringToDate("30.01.2000"), formatter.parseStringToDate("28.02.2000"),
				formatter.parseStringToDate("30.03.2000") };

		fillExchangeRateDataSetForTest();
	}

	// Case1:
	// UserInput:-----------------------------------------------------------------01.04.===15.04.----
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput1_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.04.2000");
		userInputTo = formatter.parseStringToDate("15.04.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.04.2000"), formatter.parseStringToDate("15.04.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case2:
	// UserInput:01.01------------------------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput2_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.01.2000");
		userInputTo = formatter.parseStringToDate("01.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("01.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("02.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case3:
	// UserInput:--------15.01------------------------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput3_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.01.2000");
		userInputTo = formatter.parseStringToDate("15.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("14.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.01.2000"), formatter.parseStringToDate("15.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("16.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry5 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);
		exchangeRateDataSetExpected.add(expectedEntry5);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case4:
	// UserInput:---------------30.01-----------------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput4_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("30.01.2000");
		userInputTo = formatter.parseStringToDate("30.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("29.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("30.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case5:
	// UserInput:------------------------------------------------------------------01.04--------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput5_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.04.2000");
		userInputTo = formatter.parseStringToDate("01.04.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.04.2000"), formatter.parseStringToDate("01.04.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case6:
	// UserInput:01.01.=========30.01.----------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput6_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.01.2000");
		userInputTo = formatter.parseStringToDate("30.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case7:
	// UserInput:01.01.===============================28.02.------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput7_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.01.2000");
		userInputTo = formatter.parseStringToDate("28.02.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case8:
	// UserInput:01.01.====================================================30.03.---------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput8_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.01.2000");
		userInputTo = formatter.parseStringToDate("30.03.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();
		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case9:
	// UserInput:-15.12.===============================================================15.04.---------------------
	// DataSet:-----------01.01.=======30.01.--01.02=======28.02.--01.03=====30.03.------
	@Test
	public void testAdjustEntrys_WithValidInput9_ExchangeRateDataSetShouldContainCorrectEntrys() throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.12.1999");
		userInputTo = formatter.parseStringToDate("15.04.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.12.1999"), formatter.parseStringToDate("15.04.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case10:
	// UserInput:01.01===15.01.------------------------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput10_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("01.01.2000");
		userInputTo = formatter.parseStringToDate("15.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("15.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("16.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case11:
	// UserInput:-------10.01===15.01.------------------------------------------------------------------------------
	// DataSet:--01.01.=================30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput11_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("10.01.2000");
		userInputTo = formatter.parseStringToDate("15.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("09.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("10.01.2000"), formatter.parseStringToDate("15.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("16.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry5 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);
		exchangeRateDataSetExpected.add(expectedEntry5);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case12:
	// UserInput:------15.01.===30.01.--------------------------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput12_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.01.2000");
		userInputTo = formatter.parseStringToDate("30.01.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("14.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.01.2000"), formatter.parseStringToDate("30.01.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case13:
	// UserInput:------15.01.===========01.02.-------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput13_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.01.2000");
		userInputTo = formatter.parseStringToDate("01.02.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("14.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.01.2000"), formatter.parseStringToDate("01.02.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("02.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case14:
	// UserInput:------15.01.=================15.02.-------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput14_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.01.2000");
		userInputTo = formatter.parseStringToDate("15.02.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("14.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.01.2000"), formatter.parseStringToDate("15.02.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(2.0), currencyIsoCode[0],
				formatter.parseStringToDate("16.02.2000"), formatter.parseStringToDate("28.02.2000"));
		IataExchangeRateData expectedEntry4 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);
		exchangeRateDataSetExpected.add(expectedEntry4);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	// Case15:
	// UserInput:------15.01.======================================15.03.-------------------------------------------------------
	// DataSet:--01.01.=========30.01.--01.02=========28.02.--01.03========30.03.---------------------
	@Test
	public void testAdjustEntrys_WithValidInput15_ExchangeRateDataSetShouldContainCorrectEntrys()
			throws ParseException {
		// Given1: UserInput
		userInputExchangeRate = 5.0;
		userInputCurrencyIsoCode = "USD";
		userInputFrom = formatter.parseStringToDate("15.01.2000");
		userInputTo = formatter.parseStringToDate("15.03.2000");
		objectToTest = new IataExchangeRateEntryAdjustment(exchangeRateDataSetActual, userInputExchangeRate,
				userInputCurrencyIsoCode, userInputFrom, userInputTo);
		// Given2: Expected entries
		IataExchangeRateData expectedEntry1 = new IataExchangeRateData(new BigDecimal(1.0), currencyIsoCode[0],
				formatter.parseStringToDate("01.01.2000"), formatter.parseStringToDate("14.01.2000"));
		IataExchangeRateData expectedEntry2 = new IataExchangeRateData(new BigDecimal(5.0), currencyIsoCode[0],
				formatter.parseStringToDate("15.01.2000"), formatter.parseStringToDate("15.03.2000"));
		IataExchangeRateData expectedEntry3 = new IataExchangeRateData(new BigDecimal(3.0), currencyIsoCode[0],
				formatter.parseStringToDate("16.03.2000"), formatter.parseStringToDate("30.03.2000"));
		exchangeRateDataSetExpected.add(expectedEntry1);
		exchangeRateDataSetExpected.add(expectedEntry2);
		exchangeRateDataSetExpected.add(expectedEntry3);

		// When: UserInput is added to exchangeRateDataSet and existed entries are adjusted
		objectToTest.adjustEntries();

		// Then: exchangeRateDataSet and expected exchangeRateDataSet should be the same
		assertEquals(exchangeRateDataSetActual.getExchangeRateDataSet(), exchangeRateDataSetExpected);
	}

	private void fillExchangeRateDataSetForTest() throws ParseException {
		for (int i = 0; i < from.length && i < to.length && i < exchangeRate.length; i++) {
			IataExchangeRateData data = new IataExchangeRateData(new BigDecimal(exchangeRate[i]), currencyIsoCode[0],
					from[i], to[i]);

			exchangeRateDataSetActual.getExchangeRateDataSet().add(data);
		}
	}
}
