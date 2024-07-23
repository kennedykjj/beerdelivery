package beer.delivery.challenge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class GeoCalculationTest {

	Point polygon1[] = { new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10) };
	Point polygon2[] = { new Point(0, 0), new Point(5, 5), new Point(5, 0) };
	Point polygon3[] = { new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10), new Point(5, 5) };

	@Test
	public void testNotInsidePolygon1() {
		Point point = new Point(20, 20);

		assertFalse(GeoCalculation.isInside(polygon1, 4, point));
	}

	@Test
	public void testInsidePolygon1() {
		Point point = new Point(5, 5);

		assertTrue(GeoCalculation.isInside(polygon1, 4, point));
	}
	
	@Test
	public void testInsidePolygon2() {
		Point point = new Point(3, 3);

		assertTrue(GeoCalculation.isInside(polygon2, 3, point));
	}

	@Test
	public void testInsidePolygon2_2() {
		Point point = new Point(5, 1);

		assertTrue(GeoCalculation.isInside(polygon2, 3, point));
	}
	
	@Test
	public void testNotInsidePolygon2() {
		Point point = new Point(8, 1);

		assertFalse(GeoCalculation.isInside(polygon2, 3, point));
	}
	
	@Test
	public void testNotInsidePolygon3() {
		Point point = new Point(-1, 10);

		assertFalse(GeoCalculation.isInside(polygon3, 5, point));
	}
	
	@Test
	public void testDistance() {
		Point point1 = new Point(1, 1);
		Point point2 = new Point(5, 5);
		
		double ret = GeoCalculation.distance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		assertEquals(628.4879299059282D, ret);
	}
	
	@Test
	public void testDistanceNegativeValues() {
		
		double ret = GeoCalculation.distance(32.9697, -96.80322, 29.46786, -98.53506);
		assertEquals(422.73893139401383D, ret);
	}
	

}
