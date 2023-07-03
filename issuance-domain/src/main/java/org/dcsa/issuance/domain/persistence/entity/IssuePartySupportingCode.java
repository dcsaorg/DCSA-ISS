package org.dcsa.issuance.domain.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.dcsa.issuance.domain.persistence.entity.enums.PartyCodeListProvider;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "issue_party_supporting_code")
public class IssuePartySupportingCode {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "issue_party_id")
  private IssueParty party;

  @Column(name = "party_code", length = 100, nullable = false)
  private String partyCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "party_code_list_provider", nullable = false)
  private PartyCodeListProvider partyCodeListProvider;
}
