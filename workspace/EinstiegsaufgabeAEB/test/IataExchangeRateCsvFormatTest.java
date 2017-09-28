import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class IataExchangeRateCsvFormatTest {
	private IataExchangeRateCsvFormat objectToTest;

	@Before
	public void setUp() {
		objectToTest = new IataExchangeRateCsvFormat();
	}

	@Rule
	public ErrorCollector errors = new ErrorCollector();

	@Test
	public void testCheckCurrencyValue_With_AllValidInputs() {
		String[] validInputs = { "0,00000", "1000,999999", "2,131151" };

		for (int i = 0; i < validInputs.length; i++) {
			try {
				assertTrue(objectToTest.checkCurrencyValue(validInputs[i]));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

	@Test
	public void testCheckCurrencyValue_With_AllInvalidInputs() {
		String[] invalidInputs = { "0.2222", "35,242.352", "123,235,4" };

		for (int i = 0; i < invalidInputs.length; i++) {
			try {
				assertFalse(objectToTest.checkCurrencyValue(invalidInputs[i]));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

	@Test
	public void testCheckIsoCode_With_AllValidInputs() {
		String[] validInputs = { "USD", "XAF", "GBP", "HUF", "TND", "TRY", "THB", "TWD", "SYP", "SZL", "ZAR" };

		for (int i = 0; i < validInputs.length; i++) {
			try {
				assertTrue(objectToTest.checkIsoCode((validInputs[i])));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

	@Test
	public void testCheckIsoCode_With_AllInvalidInputs() {
		String[] invalidInputs = { "DAD", "OMG", "LIG" };

		for (int i = 0; i < invalidInputs.length; i++) {
			try {
				assertFalse(objectToTest.checkIsoCode(invalidInputs[i]));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

	@Test
	public void testCheckDateFormat_With_AllValidInputs() {
		String[] validInputs = { "01.01.1900", "30.12.2099", "23.04.2014" };

		for (int i = 0; i < validInputs.length; i++) {
			try {
				assertTrue(objectToTest.checkDateFormat(validInputs[i]));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

	@Test
	public void testCheckDateFormat_With_AllInvalidInputs() {
		String[] invalidInputs = { "32.01.1900", "30.13.2099", "01.01.3014" };

		for (int i = 0; i < invalidInputs.length; i++) {
			try {
				assertFalse(objectToTest.checkDateFormat(invalidInputs[i]));
			} catch (final IllegalArgumentException e) {
				errors.addError(e);
			}
		}
	}

}
