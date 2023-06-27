package org.dcsa.issuance.transferobjects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PartyCodeListProviderTO {
  LEI("Legal Entity Identifier (LEI)"),
  DID("Decentralized Identifier"),
  ;

  private final String value;
}
