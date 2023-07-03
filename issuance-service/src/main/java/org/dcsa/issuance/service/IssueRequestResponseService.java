package org.dcsa.issuance.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.dcsa.issuance.domain.persistence.entity.IssuanceRequestResponse;
import org.dcsa.issuance.domain.persistence.repository.IssueRequestResponseRepository;
import org.dcsa.issuance.service.mapping.IssuanceRequestResponseCodeMapper;
import org.dcsa.issuance.transferobjects.IssuanceRequestResponseTO;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IssueRequestResponseService {

  private final IssuanceRequestResponseCodeMapper issuanceRequestResponseCodeMapper;
  private final IssueRequestResponseRepository issueRequestResponseRepository;

  @Transactional(Transactional.TxType.REQUIRED)
  public void processIssuanceResponse(IssuanceRequestResponseTO issuanceRequestResponseTO) {
    var pendingResponse = getPendingResponseFor(issuanceRequestResponseTO.transportDocumentReference()).orElse(null);
    if (pendingResponse == null) {
      throw ConcreteRequestErrorMessageException.invalidInput(
        "Not expecting an issuance response for "
        + issuanceRequestResponseTO.transportDocumentReference()
      );
    }
    var code = issuanceRequestResponseCodeMapper.toDAO(issuanceRequestResponseTO.issuanceResponseCode());
    if (pendingResponse.response(code, issuanceRequestResponseTO.reason())) {
      issueRequestResponseRepository.save(pendingResponse);
    }
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public void ensureIsPendingResponse(String transportDocumentReference) {
    var pendingResponse = getPendingResponseFor(transportDocumentReference).orElse(null);
    if (pendingResponse != null && pendingResponse.getIssuanceResponseCode() == null) {
      return;
    }
    var response = IssuanceRequestResponse.builder().transportDocumentReference(transportDocumentReference).build();
    issueRequestResponseRepository.save(response);
  }

  public Optional<IssuanceRequestResponse> getPendingResponseFor(String transportDocumentReference) {
    return issueRequestResponseRepository.findFirstByTransportDocumentReferenceOrderByCreatedDateTimeDesc(
      transportDocumentReference
    );
  }

}
