package org.dcsa.issuance.controller.unofficial;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceRequestState;
import org.dcsa.issuance.service.IssueRequestService;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

record ChangeStateTO(
  @NotNull
  @Size(max = 20)
  String transportDocumentReference,

  @NotNull
  IssuanceRequestState issuanceRequestState
  ) {
}

@Validated
@RestController
@RequiredArgsConstructor
public class UnofficialIssuanceRequestController {

  private final IssueRequestService issueRequestService;

  @PutMapping(path = "${spring.application.iss-context-path}" + "/unofficial/change-state/{transportDocumentReference}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newIssuanceRequest(
      @PathVariable String transportDocumentReference,
      @Valid @RequestBody ChangeStateTO changeStateTO
  ) {
    if (!transportDocumentReference.equals(changeStateTO.transportDocumentReference())) {
      throw ConcreteRequestErrorMessageException.invalidInput("Transport Document Reference differs between request"
        + " body and path");
    }
    var issuanceRequest = issueRequestService.attemptStateChange(transportDocumentReference, changeStateTO.issuanceRequestState())
      .orElse(null);
    if (issuanceRequest == null) {
      throw ConcreteRequestErrorMessageException.notFound("No issue request with reference: "
        + transportDocumentReference);
    }
    if (!issuanceRequest.getIssuanceRequestState().equals(changeStateTO.issuanceRequestState())) {
      throw ConcreteRequestErrorMessageException.conflict(
        "Cannot transition " + transportDocumentReference
        + " from " + issuanceRequest.getIssuanceRequestState().name()
        + " to " + changeStateTO.issuanceRequestState().name(), null);
    }
  }
}
