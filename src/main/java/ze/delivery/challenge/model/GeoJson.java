package ze.delivery.challenge.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "geo_json")
public class GeoJson {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@JsonProperty("id")
	public Integer id;

	@Column(name = "trading_name")
	@JsonProperty("tradingName")
	public String tradingName;

	@Column(name = "owner_name")
	@JsonProperty("ownerName")
	public String ownerName;

	@Column(name = "document")
	@JsonProperty("document")
	public String document;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "coverage_area", referencedColumnName = "id")
	@JsonProperty("coverageArea")
	public CoverageArea covarageArea;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address", referencedColumnName = "id")
	@JsonProperty("address")
	public Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public CoverageArea getCovarageArea() {
		return covarageArea;
	}

	public void setCovarageArea(CoverageArea covarageArea) {
		this.covarageArea = covarageArea;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "GeoJson [id=" + id + ", tradingName=" + tradingName + ", ownerName=" + ownerName + ", document="
				+ document + ", covarageArea=" + covarageArea.toString() + ", address=" + address.toString() + "]";
	}

}
