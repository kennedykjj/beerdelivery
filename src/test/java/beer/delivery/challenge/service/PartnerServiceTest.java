package beer.delivery.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beer.delivery.challenge.model.GeoJson;
import beer.delivery.challenge.repository.PartnerRepositoryI;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PartnerServiceTest {

	@Autowired
	private PartnerService targetClass;
	
	@MockBean
	private PartnerRepositoryI repo;

	@Test
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		GeoJson obj = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		doReturn(Optional.of(obj)).when(repo).findById(1);
		GeoJson ret = targetClass.findById(1);
		
		assertEquals(obj, ret);
		
	}
	
	@Test
	public void testFindByIdNull() throws JsonMappingException, JsonProcessingException {
		GeoJson ret = targetClass.findById(2);
		assertNull(ret);
		
	}
	
	@Test
	public void testSaveGeoJson() throws JsonMappingException, JsonProcessingException{
		GeoJson obj = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		doReturn(Optional.of(obj)).when(repo).findById(1);

		targetClass.saveGeoJson(obj);
		
	}
	
	@Test
	public void testFindNearby() throws JsonMappingException, JsonProcessingException {
		List<GeoJson> listOfGeoJson = new ArrayList<>();
		GeoJson obj1 = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		GeoJson obj2 = new ObjectMapper().readValue(jsonGeoJson2, GeoJson.class);
		GeoJson obj3 = new ObjectMapper().readValue(jsonGeoJson3, GeoJson.class);
		listOfGeoJson.add(obj1);
		listOfGeoJson.add(obj2);
		listOfGeoJson.add(obj3);
		doReturn(listOfGeoJson).when(repo).findAll();
		
		
		GeoJson ret = targetClass.findNearby(new Point(29d, 21d));
		
		assertEquals(obj2, ret);
	}
	
	@Test
	public void testConvertFromArrayToPolygon() {
		Double[][] arrayOfArrays = { { 30d, 20d }, { 45d, 40d }, { 10d, 40d }, { 30d, 20d } };
		
		assertNotNull(targetClass.convertFromArrayToPolygon(arrayOfArrays));
	}
	
	@Test
	public void testCoverageCheckerTrue() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		GeoJson obj = mapper.readValue(jsonGeoJson, GeoJson.class);
		
		assertTrue(targetClass.coverageChecker(obj, new Point(29d, 21d)));
	}
	
	@Test
	public void testCoverageCheckerFalse() throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		GeoJson obj = mapper.readValue(jsonGeoJson, GeoJson.class);
		
		assertFalse(targetClass.coverageChecker(obj, new Point(-46.57421d, -21.785741d)));
	}
	
	@Test
	public void testOrderByDistance() throws JsonMappingException, JsonProcessingException {
		List<GeoJson> listOfGeoJson = new ArrayList<>();
		GeoJson obj1 = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		GeoJson obj2 = new ObjectMapper().readValue(jsonGeoJson2, GeoJson.class);
		GeoJson obj3 = new ObjectMapper().readValue(jsonGeoJson3, GeoJson.class);
		listOfGeoJson.add(obj1);
		listOfGeoJson.add(obj2);
		listOfGeoJson.add(obj3);
		
		HashMap<GeoJson, Double> ret = targetClass.orderByDistance(listOfGeoJson, new Point(29d, 21d));
		
		
		HashMap<GeoJson, Double> aux = new HashMap<>();
		aux.put(obj2, 877.801702988487d);
		aux.put(obj1, 3155.8475625183d);
		aux.put(obj3, 3767.072243323097d);
		
		assertEquals(ret, aux);

	}
	
	private String jsonGeoJson = "{\r\n"
			+ "  \"id\": 2, \r\n"
			+ "  \"tradingName\": \"Casa do Jão\",\r\n"
			+ "  \"ownerName\": \"João Bombeirão\",\r\n"
			+ "  \"document\": \"1432132123891/0001\",\r\n"
			+ "  \"coverageArea\": { \r\n"
			+ "    \"type\": \"MultiPolygon\", \r\n"
			+ "    \"coordinates\": [\r\n"
			+ "      [[[30, 20], [45, 40], [10, 40], [30, 20]]], \r\n"
			+ "      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]\r\n"
			+ "    ]\r\n"
			+ "  },\r\n"
			+ "  \"address\": { \r\n"
			+ "    \"type\": \"Point\",\r\n"
			+ "    \"coordinates\": [5, 10]\r\n"
			+ "  }\r\n"
			+ "}";
	
	private String jsonGeoJson2 = "{\r\n"
			+ "  \"id\": 2, \r\n"
			+ "  \"tradingName\": \"Casa do Jão\",\r\n"
			+ "  \"ownerName\": \"João Bombeirão\",\r\n"
			+ "  \"document\": \"1432132123891/0001\",\r\n"
			+ "  \"coverageArea\": { \r\n"
			+ "    \"type\": \"MultiPolygon\", \r\n"
			+ "    \"coordinates\": [\r\n"
			+ "      [[[30, 20], [45, 40], [10, 40], [30, 20]]], \r\n"
			+ "      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]\r\n"
			+ "    ]\r\n"
			+ "  },\r\n"
			+ "  \"address\": { \r\n"
			+ "    \"type\": \"Point\",\r\n"
			+ "    \"coordinates\": [30, 20]\r\n"
			+ "  }\r\n"
			+ "}";
	
	private String jsonGeoJson3 = "{\r\n"
			+ "  \"id\": 2, \r\n"
			+ "  \"tradingName\": \"Casa do Jão\",\r\n"
			+ "  \"ownerName\": \"João Bombeirão\",\r\n"
			+ "  \"document\": \"1432132123891/0001\",\r\n"
			+ "  \"coverageArea\": { \r\n"
			+ "    \"type\": \"MultiPolygon\", \r\n"
			+ "    \"coordinates\": [\r\n"
			+ "      [[[30, 20], [45, 40], [10, 40], [30, 20]]], \r\n"
			+ "      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]\r\n"
			+ "    ]\r\n"
			+ "  },\r\n"
			+ "  \"address\": { \r\n"
			+ "    \"type\": \"Point\",\r\n"
			+ "    \"coordinates\": [1, 1]\r\n"
			+ "  }\r\n"
			+ "}";
	
}
