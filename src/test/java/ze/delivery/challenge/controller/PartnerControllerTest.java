package ze.delivery.challenge.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ze.delivery.challenge.model.GeoJson;
import ze.delivery.challenge.service.PartnerServiceI;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PartnerController.class)
@ActiveProfiles("test")
public class PartnerControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private PartnerController partnerController;

	@MockBean
	private PartnerServiceI partnerServiceMock;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(partnerController).build();
	}

	@Test
	public void testGetPartner() throws Exception {
		GeoJson obj = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		doReturn(obj).when(partnerServiceMock).findById(1);
		mockMvc.perform(get("/partner/1")).andExpect(status().isOk());
	}

	@Test
	public void testGetPartnerNotFound() throws Exception {
		GeoJson obj = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		doReturn(obj).when(partnerServiceMock).findById(2);
		mockMvc.perform(get("/partner/1")).andExpect(status().isNoContent());
	}

	@Test
	public void testPostPartner() throws Exception {
		Mockito.doNothing().when(partnerServiceMock).saveGeoJson(Mockito.any());
		mockMvc.perform(post("/partner")
			    .contentType("application/json")
				.content(jsonGeoJson)).andExpect(status().isCreated());
	}
	
	@Test
	public void testGetPartnerNearby() throws Exception {
		GeoJson obj = new ObjectMapper().readValue(jsonGeoJson, GeoJson.class);
		doReturn(obj).when(partnerServiceMock).findNearby(new Point(29, 21));
		mockMvc.perform(get("/partner/nearby/29/21")).andExpect(status().isOk());
	}

	private String jsonGeoJson = "{\r\n" + "  \"id\": 2, \r\n" + "  \"tradingName\": \"Casa do Jão\",\r\n"
			+ "  \"ownerName\": \"João Bombeirão\",\r\n" + "  \"document\": \"1432132123891/0001\",\r\n"
			+ "  \"coverageArea\": { \r\n" + "    \"type\": \"MultiPolygon\", \r\n" + "    \"coordinates\": [\r\n"
			+ "      [[[30, 20], [45, 40], [10, 40], [30, 20]]], \r\n"
			+ "      [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]\r\n" + "    ]\r\n" + "  },\r\n"
			+ "  \"address\": { \r\n" + "    \"type\": \"Point\",\r\n" + "    \"coordinates\": [5, 10]\r\n" + "  }\r\n"
			+ "}";

}
