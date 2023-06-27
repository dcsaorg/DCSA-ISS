package org.dcsa.issuance.domain.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.dcsa.issuance.domain.persistence.entity.IssuanceRequest;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceRequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRequestRepository extends JpaRepository<IssuanceRequest, UUID> {

  Optional<IssuanceRequest> findFirstByTransportDocumentReferenceAndIssuanceRequestStateIn(
    String transportDocumentReference,
    List<IssuanceRequestState> issuanceRequestStates
  );
}
