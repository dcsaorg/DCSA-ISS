package org.dcsa.issuance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dcsa.issuance.service.IssueRequestService;
import org.dcsa.issuance.transferobjects.IssuanceRequestTO;
import org.dcsa.issuance.transferobjects.IssuePartyTO;
import org.dcsa.issuance.transferobjects.enums.PartyCodeListProviderTO;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
public class IssueRequestController {

  private final IssueRequestService issueRequestService;

  @PutMapping(path = "${spring.application.iss-context-path}" + "/issue-ebls")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newIssuanceRequest(@Valid @RequestBody IssuanceRequestTO issuanceRequestTO) {
    validateIssueParty(issuanceRequestTO.issueTo());
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

  private void validateIssueParty(IssuePartyTO issuePartyTO) {
    var supportingPartyCodeTOs = issuePartyTO.supportingPartyCodes();
    if (supportingPartyCodeTOs == null) {
      return;
    }
    Set<PartyCodeListProviderTO> codeListProviderSet = EnumSet.noneOf(PartyCodeListProviderTO.class);
    for (var supportingPartyCode : supportingPartyCodeTOs) {
      if (!codeListProviderSet.add(supportingPartyCode.partyCodeListProvider())) {
        throw ConcreteRequestErrorMessageException.invalidInput("The \"issueTo\" party had two supporting party codes"
          + " with code list provider \"" + supportingPartyCode.partyCodeListProvider() + "\". That code list provider"
          + " must be used at most once.");
      }
    }
  }
}
