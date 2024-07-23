package beer.delivery.challenge.service;

import org.springframework.data.geo.Point;

import beer.delivery.challenge.model.GeoJson;

public interface PartnerServiceI {
	GeoJson findById(Integer partnerId);

	void saveGeoJson(GeoJson geoJson);

	GeoJson findNearby(Point point);

}
