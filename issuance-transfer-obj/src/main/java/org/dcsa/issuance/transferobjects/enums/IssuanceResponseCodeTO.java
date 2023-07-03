package org.dcsa.issuance.transferobjects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IssuanceResponseCodeTO {
  ISSU("Issued"),
  BREQ("Bad request"),
  REFU("Refused"),
  ;

  @Getter
  private final String value;

}
