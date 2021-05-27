package ze.delivery.challenge.service;

import org.springframework.data.geo.Point;

import ze.delivery.challenge.model.GeoJson;

public interface PartnerServiceI {
	public GeoJson findById(Integer partnerId);

	public void saveGeoJson(GeoJson geoJson);

	public GeoJson findNearby(Point point);

}
