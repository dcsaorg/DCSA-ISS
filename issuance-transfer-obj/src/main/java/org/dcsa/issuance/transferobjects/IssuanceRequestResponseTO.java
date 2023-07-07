package org.dcsa.issuance.transferobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.dcsa.issuance.transferobjects.enums.IssuanceResponseCodeTO;

public record IssuanceRequestResponseTO(
  @NotNull
  @Size(max = 20)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String transportDocumentReference,
  @NotNull
  IssuanceResponseCodeTO issuanceResponseCode,
  @Size(max = 255)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*$")
  String reason

) {
  @Builder(toBuilder = true)
  public IssuanceRequestResponseTO {}
}
