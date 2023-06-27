package org.dcsa.issuance.transferobjects;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record IssuanceRequestTO(
  @NotNull  // TODO: replace with the real TD
  JsonNode document,
  @NotNull
  IssuePartyTO issueTo,
  SupportingDocumentTO eBLVisualisationByCarrier

) {
  @Builder(toBuilder = true)
  public IssuanceRequestTO {}
}
