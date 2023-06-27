package org.dcsa.issuance.transferobjects;

import jakarta.validation.constraints.Size;
import lombok.Builder;

public record SupportingDocumentTO(
  @Size(max = 100)
  String name,
  byte[] content
) {
  @Builder(toBuilder = true)
  public SupportingDocumentTO {}
}
