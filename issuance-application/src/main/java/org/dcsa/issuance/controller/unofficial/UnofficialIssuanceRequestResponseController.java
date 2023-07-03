package org.dcsa.issuance.controller.unofficial;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.dcsa.issuance.service.IssueRequestResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

record PendingIssuanceRequestResponseTO(
  @NotNull
  @Size(max = 20)
  String transportDocumentReference
) {
}

@Validated
@RestController
@RequiredArgsConstructor
public class UnofficialIssuanceRequestResponseController {

  private final IssueRequestResponseService issueRequestResponseService;

  @PostMapping(path = "${spring.application.iss-rsp-context-path}" + "/unofficial/ensure-is-pending-response")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void newIssuanceRequest(
    @RequestBody PendingIssuanceRequestResponseTO pendingIssuanceRequestResponseTO
  ) {
    issueRequestResponseService.ensureIsPendingResponse(
        pendingIssuanceRequestResponseTO.transportDocumentReference());
  }
}
