package ze.delivery.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ze.delivery.challenge.controller.PartnerController;
import ze.delivery.challenge.model.GeoJson;
import ze.delivery.challenge.repository.PartnerRepositoryI;
import ze.delivery.challenge.util.GeoCalculation;

@Service
public class PartnerService implements PartnerServiceI{

	@Autowired
	public PartnerRepositoryI partnerRepo;
	
	private static Logger LOG = LoggerFactory.getLogger(PartnerController.class);


	public GeoJson findById(Integer partnerId) {
		try {
			Optional<GeoJson> ret = partnerRepo.findById(partnerId);
			if (ret.isEmpty())
				return null;
			else
				return ret.get();
		} catch (Exception e) {
			LOG.error("Error while finding partner by id {}.", partnerId);
			throw e;
		}
	}

	public void saveGeoJson(GeoJson geoJson) {
		try {
			partnerRepo.saveAndFlush(geoJson);			
		} catch (Exception e) {
			LOG.error("Error while saving new partner. Data: {}", geoJson);
			throw e;
		}
	}

	public GeoJson findNearby(Point point) {
		List<GeoJson> listOfAll = new ArrayList<>();			
		try {
			listOfAll = partnerRepo.findAll();			
		} catch (Exception e) {
			LOG.error("Error while finding nearby partners. Coordinates: {}, {}", point.getX(), point.getY());
			throw e;
		}
		
		Map<GeoJson, Double> calculatedDistances = orderByDistance(listOfAll, point);
		
		Map<GeoJson, Double> orderedByDistances = calculatedDistances.entrySet()
				  .stream()
				  .sorted(Map.Entry.comparingByValue())
				  .collect(Collectors.toMap(
						    Map.Entry::getKey, 
						    Map.Entry::getValue, 
						    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		 for (Map.Entry<GeoJson, Double> entry : orderedByDistances.entrySet()) {
			 GeoJson nearbyGeoJson = entry.getKey();
			 
			 if(coverageChecker(nearbyGeoJson, point)) {
				 return entry.getKey();
			 } 
		 }
		
		return null;
	}
	
	public Boolean coverageChecker(GeoJson geoJson, Point point) {
		Double[][][][] ListMultiPolygon = geoJson.getCovarageArea().getCoordinate();
		for (Double[][][] multiPolygon : ListMultiPolygon) {
			for (Double[][] polygon : multiPolygon) {
				Point[] listOfPoint = convertFromArrayToPolygon(polygon);

				Boolean isInside = GeoCalculation.isInside(listOfPoint, listOfPoint.length, point);
				if (isInside) {
					return isInside;
				}
			}
		}
		return false;
	}

	public HashMap<GeoJson, Double> orderByDistance(List<GeoJson> listOfAll, Point point) {
		HashMap<GeoJson, Double> mapOfPartners = new HashMap<GeoJson, Double>();
		for (GeoJson geoJson : listOfAll) {
			Double[] coordinates = geoJson.getAddress().getCoordinates();
			double distance = GeoCalculation.distance(coordinates[0], coordinates[0], point.getX(), point.getY());
			mapOfPartners.put(geoJson, distance);
			
		}
		return mapOfPartners;
	}

	public Point[] convertFromArrayToPolygon(Double[][] arrayOfDoubles) {
		List<Point> listOfPoints = new ArrayList<Point>();
		for (Double[] doubles : arrayOfDoubles) {
			listOfPoints.add(new Point(doubles[0], doubles[1]));
		}
		Point[] arrayOfPoints = listOfPoints.toArray(new Point[listOfPoints.size()]);

		return arrayOfPoints;
	}

}
