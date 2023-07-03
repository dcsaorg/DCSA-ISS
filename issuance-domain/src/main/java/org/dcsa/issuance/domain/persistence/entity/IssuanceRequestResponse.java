package org.dcsa.issuance.domain.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceResponseCode;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "issuance_request_response")
public class IssuanceRequestResponse {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transport_document_reference", length = 20, nullable = false)
  private String transportDocumentReference;

  @Column(name = "issuance_response_code", nullable = false)
  @Enumerated(EnumType.STRING)
  private IssuanceResponseCode issuanceResponseCode;

  @Column(name = "reason", columnDefinition = "TEXT", nullable = false, length = 255)
  private String reason;

  @CreationTimestamp
  @Column(name = "created_date_time", updatable = false)
  private ZonedDateTime createdDateTime;

  public boolean response(@NotNull IssuanceResponseCode issuanceResponseCode, String reason) {
    if (this.issuanceResponseCode != null || this.reason != null) {
      if (this.issuanceResponseCode != issuanceResponseCode || !Objects.equals(this.reason, reason)) {
        throw new IllegalStateException("Cannot change verdict that has already been given");
      }
      return false;
    }
    this.issuanceResponseCode = issuanceResponseCode;
    this.reason = reason;
    return true;
  }

}
