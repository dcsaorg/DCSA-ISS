package org.dcsa.issuance.domain.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

import lombok.*;
import org.dcsa.issuance.domain.persistence.entity.enums.IssuanceRequestState;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "issuance_request")
public class IssuanceRequest {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transport_document_reference", length = 20, nullable = false)
  private String transportDocumentReference;

  @Column(name = "issuance_request_state", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private IssuanceRequestState issuanceRequestState = IssuanceRequestState.PEND;

  @Column(name = "transport_document_json", columnDefinition = "TEXT", nullable = false)
  private String transportDocumentJson;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "issue_to")
  private IssueParty issueTo;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "ebl_visualization")
  private EBLVisualization eBLVisualisationByCarrier;

  public void changeStateTo(IssuanceRequestState issuanceRequestState) {
    if (!this.issuanceRequestState.canTransitionTo(issuanceRequestState)) {
      throw new IllegalArgumentException("Invalid transition from " + this.issuanceRequestState
        + " to " + issuanceRequestState);
    }
    this.issuanceRequestState = issuanceRequestState;
  }
}
