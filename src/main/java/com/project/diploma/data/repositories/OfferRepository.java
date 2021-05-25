package com.project.diploma.data.repositories;

import com.project.diploma.data.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Offer getOfferByName(String name);

    boolean existsOfferByName(String name);
}
