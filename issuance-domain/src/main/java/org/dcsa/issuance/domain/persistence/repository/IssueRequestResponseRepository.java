package org.dcsa.issuance.domain.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.dcsa.issuance.domain.persistence.entity.IssuanceRequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRequestResponseRepository extends JpaRepository<IssuanceRequestResponse, UUID> {

  Optional<IssuanceRequestResponse> findFirstByTransportDocumentReferenceOrderByCreatedDateTimeDesc(String transportDocumentReference);
}
