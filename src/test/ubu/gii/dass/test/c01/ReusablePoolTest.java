/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.*;

/**
 * @author Marcos Orive Izarra
 *
 */
public class ReusablePoolTest {

	ReusablePool pool;
	ReusablePool pool2;
	ReusablePool pool3;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool=ReusablePool.getInstance();
		pool2=ReusablePool.getInstance();
		pool3=ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool=null;
		pool2=null;
		pool3=null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertTrue(pool instanceof ReusablePool);
		assertTrue(pool2 instanceof ReusablePool);
		assertEquals(pool, pool2);
		assertEquals(pool, pool3);
		assertEquals(pool2, pool3);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		try {
			assertTrue(pool.acquireReusable() instanceof Reusable);
		} catch (NotFreeInstanceException e) {
			fail("Deberian quedar 2 reusables");
		}
		try {
			assertTrue(pool.acquireReusable() instanceof Reusable);
		} catch (NotFreeInstanceException e) {
			fail("Deberian quedar 1 reusables");
		}
		
		try {
				pool.acquireReusable();
				fail("No deberian quedar reusables.");
		} catch (NotFreeInstanceException e) {
			assertEquals(e.getMessage(),"No hay más instancias reutilizables disponibles. Reintentalo más tarde");
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {
		Reusable r1 = pool.acquireReusable();
		Reusable r2 = pool.acquireReusable();
		
		try {
			pool.releaseReusable(r1);
		} catch (DuplicatedInstanceException e) {
			fail("El reusable deberia poder devolverse");
		}
		
		try {
			pool.releaseReusable(r2);
		} catch (DuplicatedInstanceException e) {
			fail("El reusable deberia poder devolverse");
		}
		
		try {
			pool.releaseReusable(r1);
			fail("No deberia poder devolverse el objeto");
		} catch (DuplicatedInstanceException e) {
			assertEquals(e.getMessage(),"Ya existe esa instancia en el pool.");
		}
		
		try {
			pool.releaseReusable(r2);
			fail("No deberia poder devolverse el objeto");
		} catch (DuplicatedInstanceException e) {
			assertEquals(e.getMessage(),"Ya existe esa instancia en el pool.");
		}
	}

}
