package org.dcsa.issuance.domain.persistence.entity.enums;

import java.util.List;

public enum IssuanceRequestState {
  PEND,
  SURR,
  ISSU,
  BREQ,
  REFU,
  ;

  public static final List<IssuanceRequestState> PENDING_STATES = List.of(PEND);
}
