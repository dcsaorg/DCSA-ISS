CREATE TABLE issue_party (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  ebl_platform_identifier varchar(100) NOT NULL,
  legal_name varchar(100) NOT NULL,
  registration_number varchar(100) NULL,
  location_of_registration varchar(2) NULL,
  tax_reference varchar(100) NULL
);

CREATE TABLE issue_party_supporting_code(
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  issue_party_id uuid NOT NULL REFERENCES issue_party(id),
  party_code varchar(100) NOT NULL,
  party_code_list_provider varchar(4) NOT NULL CHECK (party_code_list_provider IN ('LEI', 'DID'))
);

CREATE TABLE ebl_visualization (
  ebl_visualization_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  name varchar(100) NOT NULL,
  content bytea NOT NULL
);

CREATE TABLE issuance_request (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  transport_document_reference varchar(20) NOT NULL,
  issuance_request_state varchar(4) NOT NULL CHECK ( issuance_request_state IN ('PEND', 'ISSU', 'BREQ', 'REFU', 'SURR')),
  issue_to uuid NOT NULL REFERENCES issue_party(id),
  ebl_visualization uuid NULL REFERENCES ebl_visualization(ebl_visualization_id),
  transport_document_json text NOT NULL
);

CREATE UNIQUE INDEX ON issuance_request (transport_document_reference)
  WHERE issuance_request_state IN ('PEND');

CREATE TABLE pending_issuance_request (
  id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
  transport_document_reference varchar(20) NOT NULL,
  issuance_response_code varchar(4) NOT NULL DEFAULT 'PEND' CHECK (issuance_response_code IN ('PEND', 'ISSU', 'BREQ', 'REFU')),
  reason varchar(255) NULL
);

CREATE UNIQUE INDEX ON pending_issuance_request (transport_document_reference)
  WHERE issuance_response_code IN ('PEND');
