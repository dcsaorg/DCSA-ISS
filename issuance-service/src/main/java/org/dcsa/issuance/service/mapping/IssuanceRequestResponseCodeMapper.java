package org.dcsa.issuance.service.mapping;

import org.dcsa.issuance.domain.persistence.entity.IssuanceRequestResponse;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceResponseCode;
import org.dcsa.issuance.transferobjects.IssuanceRequestResponseTO;
import org.dcsa.issuance.transferobjects.enums.IssuanceResponseCodeTO;
import org.mapstruct.Mapper;

@Mapper(
  componentModel = "spring"
)
public interface IssuanceRequestResponseCodeMapper {
  IssuanceResponseCode toDAO(IssuanceResponseCodeTO issuanceResponseCodeTO);

}
