package beer.delivery.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import beer.delivery.challenge.model.GeoJson;
import beer.delivery.challenge.service.PartnerServiceI;

@RestController
@Component
public class PartnerController implements PartnerControllerI {

	@Autowired
	private PartnerServiceI partnerService;

	private static Logger LOG = LoggerFactory.getLogger(PartnerController.class);

	@RequestMapping(value = "/partner/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<GeoJson> getPartnerById(@PathVariable("id") Integer partnerId)
			throws JsonProcessingException {
		GeoJson ret = partnerService.findById(partnerId);

		ResponseEntity<GeoJson> responseEntity = null;
		if (ret != null) {
			LOG.info("Partner {} found. Data: {}", partnerId, ret.toString());
			responseEntity = new ResponseEntity<>(ret, HttpStatus.OK);

		} else {
			LOG.info("Partner {} not found.", partnerId);
			responseEntity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

		}
		return responseEntity;
	}

	@RequestMapping(value = "/partner", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GeoJson> PostPartner(@RequestBody GeoJson geoJson) {
		partnerService.saveGeoJson(geoJson);
		LOG.info("Partner saved. Data: {}", geoJson.toString());

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/partner/nearby/{lon}/{lat}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<GeoJson> getPartnerByCoordinate(@PathVariable("lon") Double lon,
			@PathVariable("lat") Double lat) {
		GeoJson ret = partnerService.findNearby(new Point(lon, lat));

		ResponseEntity<GeoJson> responseEntity = null;
		if (ret != null) {
			LOG.info("Nearby partner {} found. Data: {}", ret.getId(), ret.toString());
			responseEntity = new ResponseEntity<>(ret, HttpStatus.OK);
		} else {
			LOG.info("Nearby partner not found for coordinates: {}, {}.", lon, lat);
			responseEntity = new ResponseEntity<>(ret, HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}
}
