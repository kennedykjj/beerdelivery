package ze.delivery.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ze.delivery.challenge.model.GeoJson;

public interface PartnerRepositoryI extends JpaRepository<GeoJson, Integer> {

}
