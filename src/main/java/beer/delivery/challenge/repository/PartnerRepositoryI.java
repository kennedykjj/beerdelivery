package beer.delivery.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import beer.delivery.challenge.model.GeoJson;

public interface PartnerRepositoryI extends JpaRepository<GeoJson, Integer> {

}
