package org.dcsa.issuance.transferobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.dcsa.issuance.transferobjects.enums.PartyCodeListProviderTO;

public record SupportingPartyCodeTO(
  @NotNull
  @Size(max = 100)
  @Pattern(regexp = "^\\S+(\\s+\\S+)*")
  String partyCode,

  @NotNull
  PartyCodeListProviderTO partyCodeListProvider
) {
  @Builder(toBuilder = true)
  public SupportingPartyCodeTO {}
}
