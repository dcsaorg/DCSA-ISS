package org.dcsa.issuance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dcsa.issuance.service.IssueRequestService;
import org.dcsa.issuance.transferobjects.IssuanceRequestTO;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
public class IssueRequestController {

  private final IssueRequestService issueRequestService;

  @PutMapping(path = "${spring.application.iss-context-path}" + "/issue-ebls")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newIssuanceRequest(@Valid @RequestBody IssuanceRequestTO issuanceRequestTO) {
    if (issueRequestService.hasPendingReferenceForTransportDocument(issuanceRequestTO)) {
      // Do a check here to provide a nice error message.
      //
      // This might be subject to race-conditions where we say there is no conflict but then
      // there is one when we insert. We rely on a database constraint to get that right, but
      // when that happens, you get the boring generic error message.
      throw ConcreteRequestErrorMessageException.conflict(
        "There is already a pending issuance request for this document",
        null
      );
    }
    issueRequestService.createIssuanceRequest(issuanceRequestTO);
  }
}
