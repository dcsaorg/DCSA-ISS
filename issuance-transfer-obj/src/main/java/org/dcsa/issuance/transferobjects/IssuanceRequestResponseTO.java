package org.dcsa.issuance.transferobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.dcsa.issuance.transferobjects.enums.IssuanceResponseCodeTO;

public record IssuanceRequestResponseTO(
  @NotNull
  @Size(max = 20)
  String transportDocumentReference,
  @NotNull
  IssuanceResponseCodeTO issuanceResponseCode,
  @Size(max = 255)
  String reason

) {
  @Builder(toBuilder = true)
  public IssuanceRequestResponseTO {}
}
