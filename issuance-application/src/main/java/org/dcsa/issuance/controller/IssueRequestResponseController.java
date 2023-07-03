package org.dcsa.issuance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dcsa.issuance.service.IssueRequestResponseService;
import org.dcsa.issuance.transferobjects.IssuanceRequestResponseTO;
import org.dcsa.issuance.transferobjects.enums.IssuanceResponseCodeTO;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
public class IssueRequestResponseController {

  private final IssueRequestResponseService issueRequestResponseService;

  @PutMapping(path = "${spring.application.iss-rsp-context-path}" + "/issuance-responses")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newIssuanceRequestResponse(@Valid @RequestBody IssuanceRequestResponseTO issuanceRequestResponseTO) {
    var reason = issuanceRequestResponseTO.reason();
    if (issuanceRequestResponseTO.issuanceResponseCode() == IssuanceResponseCodeTO.ISSU) {
      if (!isEmpty(reason) && !issuanceRequestResponseTO.reason().equalsIgnoreCase("issued")) {
        // Preferably, people would omit the reason field for issuance.
        throw ConcreteRequestErrorMessageException.invalidInput("The ISSU code should not have a reason (or if it does, use a canned \"Issued\")");
      }
    } else if (isEmpty(reason)) {
      throw ConcreteRequestErrorMessageException.invalidInput("Reason is mandatory for rejections");

    }
    try {
      issueRequestResponseService.processIssuanceResponse(issuanceRequestResponseTO);
    } catch (IllegalStateException e) {
      throw ConcreteRequestErrorMessageException.conflict(e.getLocalizedMessage(), e);
    }
  }

  private boolean isEmpty(String s) {
    return s == null || s.trim().equals("");
  }
}
