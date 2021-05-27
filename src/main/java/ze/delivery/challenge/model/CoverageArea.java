package ze.delivery.challenge.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "coverage_area")
public class CoverageArea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@JsonProperty("id")
	public Integer id;

	@Column(name = "type")
	@JsonProperty("type")
	public String type;

	@Column(name = "coordinates", columnDefinition="BLOB")
	@JsonProperty("coordinates")
	public Double[][][][] coordinates;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double[][][][] getCoordinate() {
		return coordinates;
	}

	public void setCoordinate(Double[][][][] coordinate) {
		this.coordinates = coordinate;
	}

	@Override
	public String toString() {
		return "CoverageArea [id=" + id + ", type=" + type + ", coordinates=" + Arrays.toString(coordinates) + "]";
	}

}
