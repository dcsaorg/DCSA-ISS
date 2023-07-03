package org.dcsa.issuance.domain.persistence.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IssuanceResponseCode {
  ISSU("Issued"),
  BREQ("Bad request"),
  REFU("Refused"),
  ;

  @Getter
  private final String value;

}
