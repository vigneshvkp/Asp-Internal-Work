package com.aspire.thi.domain;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>ProductTest</code> contains tests for the class <code>{@link Product}</code>.
 *
 * @generatedBy CodePro at 22/12/10 12:14 PM
 * @author muthu.velappan
 * @version $Revision: 1.0 $
 */
public class ProductTests {
	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture1;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture2;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture3;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture4;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture5;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture6;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture7;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture8;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture9;

	/**
	 * An instance of the class being tested.
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	private Product fixture10;

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture1()
		throws Exception {
		if (fixture1 == null) {
			fixture1 = new Product();
		}
		return fixture1;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture2()
		throws Exception {
		if (fixture2 == null) {
			fixture2 = new Product();
			fixture2.setDescription("");
			fixture2.setPrice(new Double(-1.0));
		}
		return fixture2;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture3()
		throws Exception {
		if (fixture3 == null) {
			fixture3 = new Product();
			fixture3.setDescription("");
			fixture3.setPrice(new Double(0.0));
		}
		return fixture3;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture4()
		throws Exception {
		if (fixture4 == null) {
			fixture4 = new Product();
			fixture4.setDescription("");
			fixture4.setPrice(new Double(1.0));
		}
		return fixture4;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture5()
		throws Exception {
		if (fixture5 == null) {
			fixture5 = new Product();
			fixture5.setDescription("0123456789");
			fixture5.setPrice(new Double(-1.0));
		}
		return fixture5;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture6()
		throws Exception {
		if (fixture6 == null) {
			fixture6 = new Product();
			fixture6.setDescription("0123456789");
			fixture6.setPrice(new Double(0.0));
		}
		return fixture6;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture7()
		throws Exception {
		if (fixture7 == null) {
			fixture7 = new Product();
			fixture7.setDescription("0123456789");
			fixture7.setPrice(new Double(1.0));
		}
		return fixture7;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture8()
		throws Exception {
		if (fixture8 == null) {
			fixture8 = new Product();
			fixture8.setDescription("An??t-1.0.txt");
			fixture8.setPrice(new Double(-1.0));
		}
		return fixture8;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture9()
		throws Exception {
		if (fixture9 == null) {
			fixture9 = new Product();
			fixture9.setDescription("An??t-1.0.txt");
			fixture9.setPrice(new Double(0.0));
		}
		return fixture9;
	}

	/**
	 * Return an instance of the class being tested.
	 *
	 * @return an instance of the class being tested
	 *
	 * @see Product
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public Product getFixture10()
		throws Exception {
		if (fixture10 == null) {
			fixture10 = new Product();
			fixture10.setDescription("An??t-1.0.txt");
			fixture10.setPrice(new Double(1.0));
		}
		return fixture10;
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture1_1()
		throws Exception {
		Product fixture = getFixture1();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture2_1()
		throws Exception {
		Product fixture = getFixture2();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture3_1()
		throws Exception {
		Product fixture = getFixture3();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture4_1()
		throws Exception {
		Product fixture = getFixture4();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture5_1()
		throws Exception {
		Product fixture = getFixture5();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("0123456789", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture6_1()
		throws Exception {
		Product fixture = getFixture6();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("0123456789", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture7_1()
		throws Exception {
		Product fixture = getFixture7();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("0123456789", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture8_1()
		throws Exception {
		Product fixture = getFixture8();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("An??t-1.0.txt", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture9_1()
		throws Exception {
		Product fixture = getFixture9();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("An??t-1.0.txt", result);
	}

	/**
	 * Run the String getDescription() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetDescription_fixture10_1()
		throws Exception {
		Product fixture = getFixture10();

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("An??t-1.0.txt", result);
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture1_1()
		throws Exception {
		Product fixture = getFixture1();

		Double result = fixture.getPrice();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture2_1()
		throws Exception {
		Product fixture = getFixture2();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("-1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture3_1()
		throws Exception {
		Product fixture = getFixture3();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("0.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture4_1()
		throws Exception {
		Product fixture = getFixture4();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture5_1()
		throws Exception {
		Product fixture = getFixture5();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("-1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture6_1()
		throws Exception {
		Product fixture = getFixture6();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("0.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture7_1()
		throws Exception {
		Product fixture = getFixture7();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture8_1()
		throws Exception {
		Product fixture = getFixture8();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("-1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) -1, result.byteValue());
		assertEquals(-1.0, result.doubleValue(), 1.0);
		assertEquals(-1.0f, result.floatValue(), 1.0f);
		assertEquals(-1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(-1L, result.longValue());
		assertEquals((short) -1, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture9_1()
		throws Exception {
		Product fixture = getFixture9();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("0.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 0, result.byteValue());
		assertEquals(0.0, result.doubleValue(), 1.0);
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(0L, result.longValue());
		assertEquals((short) 0, result.shortValue());
	}

	/**
	 * Run the Double getPrice() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testGetPrice_fixture10_1()
		throws Exception {
		Product fixture = getFixture10();

		Double result = fixture.getPrice();

		// add additional test code here
		assertNotNull(result);
		assertEquals("1.0", result.toString());
		assertEquals(false, result.isNaN());
		assertEquals((byte) 1, result.byteValue());
		assertEquals(1.0, result.doubleValue(), 1.0);
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1, result.intValue());
		assertEquals(false, result.isInfinite());
		assertEquals(1L, result.longValue());
		assertEquals((short) 1, result.shortValue());
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture1_1()
		throws Exception {
		Product fixture = getFixture1();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture2_1()
		throws Exception {
		Product fixture = getFixture2();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture3_1()
		throws Exception {
		Product fixture = getFixture3();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture4_1()
		throws Exception {
		Product fixture = getFixture4();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture5_1()
		throws Exception {
		Product fixture = getFixture5();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture6_1()
		throws Exception {
		Product fixture = getFixture6();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture7_1()
		throws Exception {
		Product fixture = getFixture7();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture8_1()
		throws Exception {
		Product fixture = getFixture8();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture9_1()
		throws Exception {
		Product fixture = getFixture9();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture10_1()
		throws Exception {
		Product fixture = getFixture10();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture2_2()
		throws Exception {
		Product fixture = getFixture2();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture3_2()
		throws Exception {
		Product fixture = getFixture3();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture4_2()
		throws Exception {
		Product fixture = getFixture4();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture5_2()
		throws Exception {
		Product fixture = getFixture5();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture6_2()
		throws Exception {
		Product fixture = getFixture6();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture7_2()
		throws Exception {
		Product fixture = getFixture7();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture8_2()
		throws Exception {
		Product fixture = getFixture8();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture9_2()
		throws Exception {
		Product fixture = getFixture9();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture10_2()
		throws Exception {
		Product fixture = getFixture10();
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetDescription_fixture1_2()
		throws Exception {
		Product fixture = getFixture1();
		String description = "0123456789";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture1_1()
		throws Exception {
		Product fixture = getFixture1();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture2_1()
		throws Exception {
		Product fixture = getFixture2();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture3_1()
		throws Exception {
		Product fixture = getFixture3();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture4_1()
		throws Exception {
		Product fixture = getFixture4();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture5_1()
		throws Exception {
		Product fixture = getFixture5();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture6_1()
		throws Exception {
		Product fixture = getFixture6();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture7_1()
		throws Exception {
		Product fixture = getFixture7();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture8_1()
		throws Exception {
		Product fixture = getFixture8();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture9_1()
		throws Exception {
		Product fixture = getFixture9();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture10_1()
		throws Exception {
		Product fixture = getFixture10();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture2_2()
		throws Exception {
		Product fixture = getFixture2();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture3_2()
		throws Exception {
		Product fixture = getFixture3();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture4_2()
		throws Exception {
		Product fixture = getFixture4();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture5_2()
		throws Exception {
		Product fixture = getFixture5();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture6_2()
		throws Exception {
		Product fixture = getFixture6();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture7_2()
		throws Exception {
		Product fixture = getFixture7();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture8_2()
		throws Exception {
		Product fixture = getFixture8();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture9_2()
		throws Exception {
		Product fixture = getFixture9();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture10_2()
		throws Exception {
		Product fixture = getFixture10();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture1_2()
		throws Exception {
		Product fixture = getFixture1();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture3_3()
		throws Exception {
		Product fixture = getFixture3();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture4_3()
		throws Exception {
		Product fixture = getFixture4();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture5_3()
		throws Exception {
		Product fixture = getFixture5();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture6_3()
		throws Exception {
		Product fixture = getFixture6();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture7_3()
		throws Exception {
		Product fixture = getFixture7();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture8_3()
		throws Exception {
		Product fixture = getFixture8();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture9_3()
		throws Exception {
		Product fixture = getFixture9();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture10_3()
		throws Exception {
		Product fixture = getFixture10();
		Double price = new Double(-1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture1_3()
		throws Exception {
		Product fixture = getFixture1();
		Double price = new Double(0.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the void setPrice(Double) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testSetPrice_fixture2_3()
		throws Exception {
		Product fixture = getFixture2();
		Double price = new Double(1.0);

		fixture.setPrice(price);

		// add additional test code here
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture1_1()
		throws Exception {
		Product fixture = getFixture1();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: null;Price: null", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture2_1()
		throws Exception {
		Product fixture = getFixture2();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: ;Price: -1.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture3_1()
		throws Exception {
		Product fixture = getFixture3();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: ;Price: 0.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture4_1()
		throws Exception {
		Product fixture = getFixture4();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: ;Price: 1.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture5_1()
		throws Exception {
		Product fixture = getFixture5();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: 0123456789;Price: -1.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture6_1()
		throws Exception {
		Product fixture = getFixture6();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: 0123456789;Price: 0.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture7_1()
		throws Exception {
		Product fixture = getFixture7();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: 0123456789;Price: 1.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture8_1()
		throws Exception {
		Product fixture = getFixture8();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: An??t-1.0.txt;Price: -1.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture9_1()
		throws Exception {
		Product fixture = getFixture9();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: An??t-1.0.txt;Price: 0.0", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Test
	public void testToString_fixture10_1()
		throws Exception {
		Product fixture = getFixture10();

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Description: An??t-1.0.txt;Price: 1.0", result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 22/12/10 12:14 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ProductTests.class);
	}
}