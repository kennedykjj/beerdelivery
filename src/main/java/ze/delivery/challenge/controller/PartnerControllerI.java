package ze.delivery.challenge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import ze.delivery.challenge.model.GeoJson;

public interface PartnerControllerI {
	public ResponseEntity<GeoJson> getPartnerById(@PathVariable("id") Integer partnerId) throws JsonProcessingException;

	public ResponseEntity<GeoJson> PostPartner(@RequestBody GeoJson geoJson);

	public ResponseEntity<GeoJson> getPartnerByCoordinate(@PathVariable("lon") Double lon,
			@PathVariable("lat") Double lat);
}
