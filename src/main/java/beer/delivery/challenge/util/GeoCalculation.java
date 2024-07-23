package beer.delivery.challenge.util;

import org.springframework.data.geo.Point;

public class GeoCalculation {

	public static boolean onSegment(Point p, Point q, Point r) {
		if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX())
				&& q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
			return true;
		return false;
	}

	public static int orientation(Point p, Point q, Point r) {
		int val = (int) ((q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY()));

		if (val == 0)
			return 0;
		return (val > 0) ? 1 : 2;
	}

	public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {

		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		if (o1 != o2 && o3 != o4)
			return true;

		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false;
	}

	public static boolean isInside(Point polygon[], int n, Point p) {
		int INF = 10000;
		if (n < 3)
			return false;

		Point extreme = new Point(INF, p.getY());

		int count = 0, i = 0;
		do {
			int next = (i + 1) % n;
			if (doIntersect(polygon[i], polygon[next], p, extreme)) {
				if (orientation(polygon[i], p, polygon[next]) == 0)
					return onSegment(polygon[i], p, polygon[next]);

				count++;
			}
			i = next;
		} while (i != 0);

		return (count & 1) == 1 ? true : false;
	}

	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return (dist);
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
