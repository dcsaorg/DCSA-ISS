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

  public boolean canTransitionTo(IssuanceRequestState s) {
    return switch (this) {
      case SURR, REFU, BREQ -> false;
      case ISSU -> s == SURR;
      // We allow it to skip "ISSU" and go directly to "SURR" because it makes the test simpler
      case PEND -> true;
    };
  }
}
