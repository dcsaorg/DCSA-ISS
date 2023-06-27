package org.dcsa.issuance.service.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import org.dcsa.issuance.domain.persistence.entity.IssuanceRequest;
import org.dcsa.issuance.transferobjects.IssuanceRequestTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring"
)
public abstract class IssuanceRequestMapper {

  @Mapping(target = "transportDocumentReference", source = "transportDocumentReference")
  @Mapping(target = "transportDocumentJson", expression = "java(mapTDToJson(issuanceRequestTO.document()))")
  public abstract IssuanceRequest toDAO(IssuanceRequestTO issuanceRequestTO, String transportDocumentReference);

  protected String mapTDToJson(JsonNode document) {
    return document.toString();
  }



}

