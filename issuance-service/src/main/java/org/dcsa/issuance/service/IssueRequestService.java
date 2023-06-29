package org.dcsa.issuance.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dcsa.issuance.domain.persistence.entity.IssuanceRequest;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceRequestState;
import org.dcsa.issuance.domain.persistence.repository.IssueRequestRepository;
import org.dcsa.issuance.service.mapping.IssuanceRequestMapper;
import org.dcsa.issuance.transferobjects.IssuanceRequestTO;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.dcsa.issuance.domain.persistence.entity.enums.IssuanceRequestState.PENDING_STATES;

@Service
@AllArgsConstructor
public class IssueRequestService {

  private final IssuanceRequestMapper issuanceRequestMapper;
  private final IssueRequestRepository issueRequestRepository;

  @Transactional(Transactional.TxType.REQUIRED)
  public void createIssuanceRequest(IssuanceRequestTO issuanceRequestTO) {
    var reference = extractTDReference(issuanceRequestTO.document());
    var issuanceRequest = issuanceRequestMapper.toDAO(issuanceRequestTO, reference);
    issueRequestRepository.save(issuanceRequest);
  }

  public boolean hasPendingReferenceForTransportDocument(IssuanceRequestTO issuanceRequestTO) {
    var reference = extractTDReference(issuanceRequestTO.document());
    var existingRequest = getPendingRequestFor(reference);
    return existingRequest.isPresent();
  }

  public Optional<IssuanceRequest> getPendingRequestFor(String transportDocumentReference) {
    return issueRequestRepository.findFirstByTransportDocumentReferenceAndIssuanceRequestStateIn(
      transportDocumentReference,
      PENDING_STATES
    );
  }

  public Optional<IssuanceRequest> attemptStateChange(String transportDocumentReference, IssuanceRequestState desiredIssuanceRequestState) {
    return getPendingRequestFor(transportDocumentReference).map(
      issuanceRequest -> {
        if (issuanceRequest.getIssuanceRequestState().canTransitionTo(desiredIssuanceRequestState)) {
          issuanceRequest.changeStateTo(desiredIssuanceRequestState);
          return this.issueRequestRepository.save(issuanceRequest);
        }
        return issuanceRequest;
      }
    );
  }


  private static String extractTDReference(JsonNode document) {
    final var transportDocumentReference = document.get("transportDocumentReference");
    if (transportDocumentReference == null || !transportDocumentReference.isTextual()) {
      throw ConcreteRequestErrorMessageException.invalidInput("The transport document must have"
        + " \"transportDocumentReference\" and that attribute must be a string");
    }
    return transportDocumentReference.asText();
  }
}
