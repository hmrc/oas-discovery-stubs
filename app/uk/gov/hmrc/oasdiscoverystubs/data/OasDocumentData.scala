/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.oasdiscoverystubs.data

import uk.gov.hmrc.oasdiscoverystubs.models.OasDocument

trait OasDocumentData {

  def allOasDocuments(): Seq[OasDocument] = Seq(emsIdentity(), emsKeyingService(), emsAddressWeightingService())

  private def emsIdentity(): OasDocument = {
    OasDocument(
      id = "ems-identity",
      content =
        """
          |---
          |openapi: 3.0.1
          |info:
          |  title: "Enterprise Matching Service: Identity Service"
          |  description:
          |    "This service provides two endpoints, enabling a match request to be\
          |        \ sent with details about a person. The response will include all associated tax\
          |        \ identifiers for a successfully matched person."
          |  version: 1.0.0
          |  x-integration-catalogue:
          |    reviewed-date: 2023-06-02T11:29:59.324995559Z
          |    publisher-reference: ems-identity
          |    short-description: EMS Identity Service
          |    status: ALPHA
          |    backends:
          |      - EMS
          |servers:
          |  - url: https://api.hip.prod.eis.ns2p.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Production
          |  - url: https://api.hip.test.eis.ns2n.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Test
          |  - url: https://hip.ws.hmrc.gov.uk/ems/
          |    description: MDTP - Production
          |  - url: https://hip.ws.ibt.hmrc.gov.uk/ems/
          |    description: MDTP - QA
          |paths:
          |  /v1/api/match-by-name:
          |    post:
          |      tags:
          |        - match-by-name
          |      summary:
          |        "Match a person based on their given name, family name, date of birth\
          |                \ and postcode."
          |      description: |
          |        Send a request with details about a person - given name, family name, date of birth and postcode. If a matched person is found, the response will include known identifiers from NPS and ETMP for that person's various tax accounts.
          |      operationId: matchByName
          |      parameters:
          |        - name: CorrelationId
          |          in: header
          |          description: Correlation ID for request traceability
          |          required: false
          |          schema:
          |            type: string
          |      requestBody:
          |        description: Match by name request object
          |        content:
          |          application/json:
          |            schema:
          |              $ref: "#/components/schemas/MatchByNameJsonRequest"
          |        required: true
          |      responses:
          |        "200":
          |          description: Matching details for supplied names and other details
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/MatchJsonResponse"
          |              examples:
          |                success:
          |                  $ref: "#/components/examples/MatchJsonResponseExample"
          |        "400":
          |          description: Invalid arguments supplied
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/Error"
          |              examples:
          |                error:
          |                  $ref: "#/components/examples/ErrorExample"
          |      security:
          |        - oAuth2:
          |            - read:match-person-by-name
          |  /v1/api/match-by-identifier:
          |    post:
          |      tags:
          |        - match-by-identifier
          |      summary:
          |        "Match a person based on their identifier and type, given name, family\
          |                \ name, date of birth and postcode."
          |      description: |
          |        Send a request with details about a person - identifier and type, given name, family name, date of birth and postcode. If a matched person is found, the response will include known identifiers from NPS and ETMP for that person's various tax accounts.
          |      operationId: matchByIdentifier
          |      parameters:
          |        - name: CorrelationId
          |          in: header
          |          description: Correlation ID for request traceability
          |          required: false
          |          schema:
          |            type: string
          |      requestBody:
          |        content:
          |          application/json:
          |            schema:
          |              $ref: "#/components/schemas/MatchByIdentifierJsonRequest"
          |        required: true
          |      responses:
          |        "200":
          |          description: Matching details for supplied identifier and other details
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/MatchJsonResponse"
          |              examples:
          |                success:
          |                  $ref: "#/components/examples/MatchJsonResponseExample"
          |        "400":
          |          description: Invalid arguments supplied
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/Error"
          |              examples:
          |                error:
          |                  $ref: "#/components/examples/ErrorExample"
          |      security:
          |        - oAuth2:
          |            - read:match-person-by-identifier
          |components:
          |  schemas:
          |    MatchByNameJsonRequest:
          |      required:
          |        - familyName
          |        - givenName
          |      type: object
          |      properties:
          |        givenName:
          |          description: Given name of person
          |          type: string
          |          example: martin
          |        aliasGivenName:
          |          description: Include alias of given name
          |          default: false
          |          type: boolean
          |          example: true
          |        includeGivenNameInitial:
          |          description: Include initial of given name
          |          default: false
          |          type: boolean
          |          example: true
          |        familyName:
          |          description: Family name of person
          |          type: string
          |          example: smith
          |        postcode:
          |          description: Postcode of person address
          |          type: string
          |          example: M20 1XX
          |        dateOfBirth:
          |          format: date
          |          description: Date of birth of person in ISO 8601 format (YYYY-MM-DD)
          |          type: string
          |          example: 1970-12-31
          |        excludeDeceased:
          |          description: Exclude deceased people
          |          default: false
          |          type: boolean
          |          example: true
          |    Identifier:
          |      description: List of identifiers that this person has
          |      type: object
          |      properties:
          |        type:
          |          description: The identifier type
          |          type: string
          |          example: NINO
          |        value:
          |          description: The identifier value
          |          type: string
          |          example: AB123456
          |        source:
          |          description: The source of the identifier
          |          type: array
          |          items:
          |            description: The source of the identifier
          |            type: string
          |            example: '["NPS"]'
          |          example:
          |            - NPS
          |        bpid:
          |          description: The BPIDs of any associated identifiers
          |          type: array
          |          items:
          |            description: The BPIDs of any associated identifiers
          |            type: string
          |            example: '["12345678"]'
          |          example:
          |            - "12345678"
          |    MatchJsonResponse:
          |      description: Match by name response
          |      type: object
          |      properties:
          |        person:
          |          description: People who match the request and have scored sufficiently
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/Person"
          |    Person:
          |      description: People who match the request and have scored sufficiently
          |      type: object
          |      properties:
          |        identifiers:
          |          description: List of identifiers that this person has
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/Identifier"
          |        matchScore:
          |          format: double
          |          description: Score of this match for the match request fields
          |          type: number
          |          example: 250
          |        maximumMatchScore:
          |          format: double
          |          description: Maximum attainable score for the match request fields
          |          type: number
          |          example: 500
          |        matchScoreRatio:
          |          format: double
          |          description: Calculated ratio of matchScore from maximumMatchScore
          |          type: number
          |          example: 0.5
          |        matchingAnalytics:
          |          description:
          |            Breakdown of what match request fields contributed to the match
          |            score
          |          type: array
          |          items:
          |            description:
          |              Breakdown of what match request fields contributed to the
          |              match score
          |            type: string
          |            example: '["givenName: 250"]'
          |          example:
          |            - "givenName: 250"
          |    Error:
          |      description: Response error
          |      type: object
          |      properties:
          |        generalErrors:
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/GeneralError"
          |        fieldErrors:
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/FieldError"
          |    FieldError:
          |      type: object
          |      properties:
          |        field:
          |          description: The specific error field name
          |          type: string
          |          example: givenName
          |        message:
          |          description: The specific error message
          |          type: string
          |          example: Mandatory field not supplied
          |    GeneralError:
          |      type: object
          |      properties:
          |        message:
          |          description: General error description
          |          type: string
          |          example: BAD REQUEST
          |    MatchByIdentifierJsonRequest:
          |      required:
          |        - identifierType
          |        - identifierValue
          |      type: object
          |      properties:
          |        identifierType:
          |          description: Identifier type
          |          enum:
          |            - NINO
          |          type: string
          |          example: NINO
          |        identifierValue:
          |          description: Identifier value
          |          type: string
          |          example: AB123456
          |        givenName:
          |          description: Given name of person
          |          type: string
          |          example: martin
          |        familyName:
          |          description: Family name of person
          |          type: string
          |          example: smith
          |        postcode:
          |          description: Postcode of person address
          |          type: string
          |          example: M20 1XX
          |        dateOfBirth:
          |          description: Date of birth of person in ISO 8601 format (YYYY-MM-DD)
          |          type: string
          |          example: 1970-12-31
          |        excludeDeceased:
          |          description: Exclude deceased people
          |          default: false
          |          type: boolean
          |          example: true
          |    HIP-originEnum:
          |      enum:
          |        - HIP
          |        - HoD
          |      type: string
          |    HIP-failureResponse:
          |      required:
          |        - failures
          |      type: object
          |      properties:
          |        failures:
          |          minItems: 1
          |          uniqueItems: true
          |          type: array
          |          items:
          |            required:
          |              - type
          |              - reason
          |            type: object
          |            properties:
          |              type:
          |                type: string
          |              reason:
          |                type: string
          |            additionalProperties: false
          |    HIP-originResponse:
          |      required:
          |        - origin
          |        - response
          |      type: object
          |      properties:
          |        origin:
          |          $ref: "#/components/schemas/HIP-originEnum"
          |        response:
          |          $ref: "#/components/schemas/HIP-failureResponse"
          |      additionalProperties: false
          |  examples:
          |    MatchJsonResponseExample:
          |      summary: Example with a successful match
          |      value: |
          |        {
          |          "person": [
          |            {
          |              "identifiers": [
          |                {
          |                  "type": "NINO",
          |                  "value": "AB123456",
          |                  "source": [
          |                    "NPS"
          |                  ],
          |                  "bpid": [
          |                    "12345678"
          |                  ]
          |                }
          |              ],
          |              "matchScore": 250,
          |              "maximumMatchScore": 500,
          |              "matchScoreRatio": 0.5,
          |              "matchingAnalytics": [
          |                "givenName: 250"
          |              ]
          |            }
          |          ]
          |        }
          |    ErrorExample:
          |      summary: Example error due to missing field
          |      value: |-
          |        {
          |          "generalErrors": [
          |            {
          |              "message": "BAD REQUEST"
          |            }
          |          ],
          |          "fieldErrors": [
          |            {
          |              "field": "givenName",
          |              "message": "Mandatory field not supplied"
          |            }
          |          ]
          |        }
          |  securitySchemes:
          |    oAuth2:
          |      type: oauth2
          |      description: OAuth2 Client Credentials Flow
          |      flows:
          |        clientCredentials:
          |          tokenUrl: /tokenUrl/not-required
          |          scopes:
          |            read:match-person-by-name: Match a person by their name
          |            read:match-person-by-identifier:
          |              Match a person by their identifier e.g.
          |              NINO
          |""".stripMargin
    )
  }

  private def emsKeyingService(): OasDocument = {
    OasDocument(
      id = "ems-keying-service",
      content =
        """
          |---
          |openapi: 3.0.1
          |info:
          |  title: API Documentation for Keying Service
          |  description: |-
          |    This relates to the keying API service.  When a request is sent with details about a person, the API responds with details of a matched person, if a match is found, along with any associated addresses. If no matched person is found, the response will indicate as such.
          |    ```
          |    Change Log
          |    ```
          |
          |      | Version | Date | Author | Description |
          |      |---|-----|------|-----|
          |      | 1.1.0 | 24-11-2022 | Mark Stead | Initial draft - Created from EMS OAS file created by Ankur Mishra |
          |      | 1.1.1 | 20-12-2022 | Mark Stead | Removed version from server parameters and added scope to security |
          |  license:
          |    name: Apache 2.0
          |    url: http://www.apache.org/licenses/LICENSE-2.0
          |  version: "1.1"
          |  x-integration-catalogue:
          |    reviewed-date: 2022-11-17T12:00:00Z
          |    platform: HIP
          |    publisher-reference: ems-keying-service
          |    short-description: Keying Service API
          |    status: ALPHA
          |    backends:
          |      - EMS
          |servers:
          |  - url: https://api.hip.prod.eis.ns2p.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Production
          |  - url: https://api.hip.test.eis.ns2n.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Test
          |  - url: https://hip.ws.hmrc.gov.uk/ems/
          |    description: MDTP - Production
          |  - url: https://hip.ws.ibt.hmrc.gov.uk/ems/
          |    description: MDTP - QA
          |security:
          |  - oAuth2:
          |      - read:address-matching
          |tags:
          |  - name: person-plus-addresses-endpoint
          |    description: Person Plus Addresses Endpoint
          |paths:
          |  /v1/person-details:
          |    post:
          |      tags:
          |        - person-plus-addresses-endpoint
          |      description:
          |        "Send a request containing details about a person. API response\
          |                \ will contain details of a matching person (if found), along with match score\
          |                \ and all addresses linked to that person."
          |      operationId: doPersonLookupUsingPOST_2
          |      parameters:
          |        - name: CorrelationID
          |          in: header
          |          description: DES/IF Correlation ID - used for traceability purposes when present
          |          required: true
          |          schema:
          |            type: string
          |        - name: OriginatingSystem
          |          in: header
          |          description: The Originating System
          |          schema:
          |            type: string
          |      requestBody:
          |        description: the request parameters passed to match an entity
          |        content:
          |          application/json:
          |            schema:
          |              $ref: "#/components/schemas/requestBody"
          |            examples:
          |              requestBodyExample:
          |                $ref: "#/components/examples/RequestBodyExample"
          |              requestBody2Example:
          |                $ref: "#/components/examples/RequestBody2Example"
          |        required: true
          |      responses:
          |        "200":
          |          description: OK
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/MatchPersonAddressResponseBody"
          |              examples:
          |                MatchedPersonAddressResponseBodyExample:
          |                  $ref: "#/components/examples/MatchedPersonAddressResponseBodyExample"
          |                PersonNotFoundResponseExample:
          |                  $ref: "#/components/examples/PersonNotFoundResponseExample"
          |        "201":
          |          description: Created
          |          content: {}
          |        "400":
          |          description: Bad Request
          |          content:
          |            application/json:
          |              schema:
          |                required:
          |                  - origin
          |                  - response
          |                type: object
          |                properties:
          |                  origin:
          |                    $ref: "#/components/schemas/HIP-originEnum"
          |                  response:
          |                    oneOf:
          |                      - $ref: "#/components/schemas/HIP-failureResponse"
          |                      - $ref: "#/components/schemas/SimpleError400Object"
          |                additionalProperties: false
          |              examples: {}
          |        "401":
          |          description: Unauthorized
          |          content: {}
          |        "403":
          |          description: Forbidden
          |          content: {}
          |        "404":
          |          description: Not Found
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/SimpleError404Object"
          |              examples:
          |                Unprocessable1:
          |                  $ref: "#/components/examples/SimpleError404Object1Example"
          |        "422":
          |          description: Unprocessable
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/SimpleError422Object"
          |              examples:
          |                Unprocessable1:
          |                  $ref: "#/components/examples/SimpleError422Object1Example"
          |                Unprocessable2:
          |                  $ref: "#/components/examples/SimpleError422Object2Example"
          |        "500":
          |          description: Internal Server Error
          |          content:
          |            application/json:
          |              schema:
          |                required:
          |                  - origin
          |                  - response
          |                type: object
          |                properties:
          |                  origin:
          |                    $ref: "#/components/schemas/HIP-originEnum"
          |                  response:
          |                    oneOf:
          |                      - $ref: "#/components/schemas/HIP-failureResponse"
          |                      - $ref: "#/components/schemas/SimpleError500Object"
          |                additionalProperties: false
          |              examples: {}
          |      deprecated: false
          |      x-codegen-request-body-name: matchRequest
          |components:
          |  schemas:
          |    MatchPersonAddressResponseBody:
          |      title: MatchPersonAddressResponseBody
          |      type: object
          |      properties:
          |        addressSource:
          |          description:
          |            the source evidence dataset where the matched entity's details
          |            are found
          |          type: string
          |        matchedEntity:
          |          $ref: "#/components/schemas/MatchedPersonWithAddresses"
          |        matchingAnalytics:
          |          $ref: "#/components/schemas/MatchingAnalyticsResponse"
          |        matchingOutcome:
          |          description:
          |            "the outcome of person matching, confirming whether a person\
          |                        \ matching the request parameters was found"
          |          enum:
          |            - IDENTIFIED_A_PERSON
          |            - FAILED_TO_IDENTIFY_THE_PERSON
          |            - FAILED_TO_IDENTIFY_A_SINGLE_BEST_MATCH_FOR_PERSON
          |          type: string
          |        requestNino:
          |          description: National Insurance Number of the person contained in the request
          |          type: string
          |        uuid:
          |          description: uuid of the response
          |          type: string
          |    MatchedPersonWithAddresses:
          |      title: MatchedPersonWithAddresses
          |      type: object
          |      properties:
          |        addresses:
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/OneOfresponseBodyMatchedEntityAddressesItems"
          |        confidence:
          |          format: double
          |          description:
          |            "match score to indicate how good the match is, with a value\
          |                        \ between 0 and 1. The higher the score, the better the match based on\
          |                        \ the parameters contained within the request."
          |          type: number
          |        dateOfBirth:
          |          $ref: "#/components/schemas/PartialDate"
          |        firstname:
          |          description: The first name or forename of the person
          |          type: string
          |        key:
          |          description: key or id of the entity matched
          |          type: string
          |        nino:
          |          description: The National Insurance Number of the person
          |          type: string
          |        surname:
          |          description: The surname or family name of the person
          |          type: string
          |    MatchingAnalyticsResponse:
          |      title: MatchingAnalyticsResponse
          |      type: object
          |      properties:
          |        matchedEntities:
          |          description:
          |            Identifies the fields on which there was a match between the
          |            request and a person in the evidence data
          |          type: string
          |        matchingThreshold:
          |          description:
          |            "the minimum score needed to determine a match between the\
          |                        \ request and a person in the evidence data, as defined in the scorecard"
          |          type: string
          |        maximumPossibleScore:
          |          description: the maximum score achievable in the scorecard
          |          type: string
          |    OneOfresponseBodyMatchedEntityAddressesItems:
          |      title: OneOfresponseBodyMatchedEntityAddressesItems
          |      type: object
          |      properties:
          |        buildingName:
          |          description: name of the building
          |          type: string
          |        buildingNumber:
          |          description: building number
          |          type: string
          |        county:
          |          description: county
          |          type: string
          |        dependentThoroughfare:
          |          description: the thoroughfare
          |          type: string
          |        locality:
          |          description: the locality
          |          type: string
          |        matchingDatasets:
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/AddressMetadataMatchingDatasets"
          |        postTown:
          |          description: the postal town
          |          type: string
          |        postcode:
          |          description: the post code
          |          type: string
          |        subbuildingName:
          |          description: the sub building name
          |          type: string
          |        thoroughfare:
          |          description: the thoroughfare
          |          type: string
          |        uprn:
          |          description:
          |            "Unique Property Reference Number (UPRN) of the address, as\
          |                        \ defined by Ordnance Survey"
          |          type: string
          |    PartialDate:
          |      title: PartialDate
          |      type: object
          |      properties:
          |        day:
          |          type: string
          |        month:
          |          type: string
          |        year:
          |          type: string
          |    AddressMetadataMatchingDatasets:
          |      title: AddressMetadataMatchingDatasets
          |      type: object
          |      properties:
          |        effectiveDate:
          |          format: date
          |          type: string
          |        name:
          |          description: name of the evidence data source
          |          type: string
          |    SimpleError422Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |        message:
          |          description: error message
          |          type: string
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |        status:
          |          description: http status
          |          type: string
          |        errorValue:
          |          description: timestamp
          |          type: string
          |    SimpleError400Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |          example:
          |            "Exception thrown: org.springframework.web.bind.MissingRequestHeaderException:\
          |                        \ Missing request header 'CorrelationID' for method parameter of type\
          |                        \ String"
          |        message:
          |          description: error message
          |          type: string
          |          example:
          |            Missing request header 'CorrelationID' for method parameter of
          |            type String
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |        status:
          |          description: http status
          |          type: string
          |          example: "400"
          |        errorValue:
          |          description: timestamp
          |          type: string
          |          example: ""
          |    SimpleError404Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |        message:
          |          description: error message
          |          type: string
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |        status:
          |          description: http status
          |          type: string
          |        path:
          |          description: resource path
          |          type: string
          |    SimpleError500Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |          example:
          |            "Exception thrown: org.springframework.web.HttpMediaTypeNotSupportedException:\
          |                        \ Content type 'text/plain;charset=UTF-8' not supported"
          |        message:
          |          description: error message
          |          type: string
          |          example: Content type 'text/plain;charset=UTF-8' not supported
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |        status:
          |          description: http status
          |          type: string
          |          example: "500"
          |    requestBody:
          |      title: requestBody
          |      required:
          |        - addressLine1
          |        - country
          |        - dateOfBirth
          |        - firstName
          |        - nino
          |        - surname
          |        - uuid
          |      type: object
          |      properties:
          |        addressLine1:
          |          description: Address Line 1 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 1
          |          type: string
          |        addressLine2:
          |          description: Address Line 2 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine3:
          |          description: Address Line 3 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine4:
          |          description: Address Line 4 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine5:
          |          description: Address Line 5 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        country:
          |          description:
          |            "The country of the person's current or last known address\
          |                        \ address. This should be entered as <q>UNITED KINGDOM</q>, which includes\
          |                        \ England, Scotland, Wales and Northern Ireland but excludes the Isle\
          |                        \ of Man and Channel Islands. For international addresses (anything not\
          |                        \ within the United Kingdom) the description should conform to ISO-3166\
          |                        \ standard e.g. <q>AFGANISTAN</q>."
          |          maxLength: 99
          |          minLength: 1
          |          type: string
          |        dateOfBirth:
          |          format: date
          |          description: The date of birth of the person.
          |          type: string
          |        firstName:
          |          description: The first name or forename of the person
          |          maxLength: 99
          |          minLength: 1
          |          pattern: "^[-a-zA-Z ']{1,99}?$"
          |          type: string
          |        matchingAnalytics:
          |          description: boolean to indicate whether to show score analytics details
          |          type: boolean
          |        nino:
          |          description: The National Insurance Number of the person
          |          pattern: "^((?:[ACEHJLMOPRSWXY][A-CEGHJ-NPR-TW-Z]|B[A-CEHJ-NPR-TW-Z]|G[ACEGHJ-NPR-TW-Z]|[KT][A-CEGHJ-MPR-TW-Z]|N[A-CEGHJL-NPR-SW-Z]|Z[A-CEGHJ-NPR-TW-Y])[0-9]{6}[A-D]?)$"
          |          type: string
          |        postcode:
          |          description:
          |            The postcode of the person's current or last known address.
          |            Postcode should only be populated when country is UNITED KINGDOM.
          |          pattern:
          |            "([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9][A-Za-z]?))))\\\
          |                        s?[0-9][A-Za-z]{2})"
          |          type: string
          |        secondName:
          |          description:
          |            The second or middle name(s) of the person. Should be separated
          |            by spaces where there are multiple
          |          maxLength: 99
          |          minLength: 0
          |          pattern: "^[-a-zA-Z ']{1,99}?$"
          |          type: string
          |        sex:
          |          description: The person's sex or gender
          |          enum:
          |            - Male
          |            - Female
          |            - Not Known
          |            - Not Specified
          |          type: string
          |        surname:
          |          description: The surname or family name of the person.
          |          maxLength: 99
          |          minLength: 1
          |          pattern: "^[-a-zA-Z ']{2,99}?$"
          |          type: string
          |        title:
          |          description: The title of the person
          |          enum:
          |            - Mr
          |            - Mrs
          |            - Miss
          |            - Ms
          |            - Dr
          |            - Rev
          |            - Sir
          |            - Lady
          |            - Lord
          |            - Dame
          |          type: string
          |        uuid:
          |          description: The universally unique identifier
          |          maxLength: 36
          |          minLength: 1
          |          type: string
          |    HIP-originEnum:
          |      enum:
          |        - HIP
          |        - HoD
          |      type: string
          |    HIP-failureResponse:
          |      required:
          |        - failures
          |      type: object
          |      properties:
          |        failures:
          |          minItems: 1
          |          uniqueItems: true
          |          type: array
          |          items:
          |            required:
          |              - type
          |              - reason
          |            type: object
          |            properties:
          |              type:
          |                type: string
          |              reason:
          |                type: string
          |            additionalProperties: false
          |    HIP-originResponse:
          |      required:
          |        - origin
          |        - response
          |      type: object
          |      properties:
          |        origin:
          |          $ref: "#/components/schemas/HIP-originEnum"
          |        response:
          |          $ref: "#/components/schemas/HIP-failureResponse"
          |      additionalProperties: false
          |  examples:
          |    RequestBodyExample:
          |      value:
          |        uuid: 12345678-1234-1234-1234-123456789012
          |        nino: JB224466
          |        firstName: SARAH
          |        surname: MACPHERSON
          |        dateOfBirth: 2004-03-01
          |        addressLine1: 12 CORONATION STREET
          |        country: United Kingdom
          |    RequestBody2Example:
          |      value:
          |        uuid: 12345678-1234-1234-1234-123456789012
          |        nino: JB224466
          |        title: Ms
          |        firstName: SARAH
          |        surname: MACPHERSON
          |        dateOfBirth: 2004-03-01
          |        addressLine1: 12 CORONATION STREET
          |        postcode: SK11 3IJ
          |        country: United Kingdom
          |        matchingAnalytics: true
          |    MatchedPersonAddressResponseBodyExample:
          |      value:
          |        uuid: 12345678-1234-1234-1234-123456789012
          |        requestNino: JB224466
          |        matchingOutcome: IDENTIFIED_A_PERSON
          |        addressSource: Enterprise Matching
          |        matchedEntity:
          |          nino: JB224466
          |          firstname: GEOFF
          |          surname: PHILLIPS
          |          key: P3323704791B
          |          dateOfBirth:
          |            year: "1999"
          |            month: "12"
          |          confidence: 0.09
          |          addresses:
          |            - matchingDatasets:
          |                - name: COMPANIES_HOUSE_PSC
          |                  effectiveDate: 2021-06-05
          |              buildingNumber: 73D
          |              thoroughfare: ST JAMES'S HILL
          |              locality: CLAPHAM
          |              postTown: LONDON
          |              postcode: SW116GV
          |        matchingAnalytics:
          |          matchedEntities: "reference-NINO AND : 5"
          |          matchingThreshold: "0.0"
          |          maximumPossibleScore: "53.0"
          |    PersonNotFoundResponseExample:
          |      value:
          |        uuid: 472ee977-0ca2-448b-ab13-d1e01060b71d
          |        requestNino: JX826162
          |        matchingOutcome: FAILED_TO_IDENTIFY_THE_PERSON
          |        addressSource: Enterprise Matching
          |    SimpleError422Object1Example:
          |      value:
          |        error: Unprocessable
          |        message: Text '1964/04/25' could not be parsed at index 4
          |        timestamp: 2022-11-07T10:28:12.745198
          |        status: "422"
          |        errorValue: ""
          |    SimpleError422Object2Example:
          |      value:
          |        timestamp: 2022-11-07T10:29:15.209218
          |        status: "422"
          |        error: Unprocessable
          |        message: |-
          |          Unrecognized field "dateofBirth" (class uk.gov.hmrc.matching.api.nps.PersonAddressMatchRequest), not marked as ignorable (16 known properties: "sex", "addressLine1", "addressLine2", "addressLine3", "addressLine4", "addressLine5", "country", "title", "dateOfBirth", "uuid", "nino", "secondName", "matchingAnalytics", "postcode", "firstName", "surname"])
          |            at [Source: (PushbackInputStream); line: 7, column: 21] (through reference chain: uk.gov.hmrc.matching.api.nps.PersonAddressMatchRequest["dateofBirth"])
          |        errorValue: ""
          |    SimpleError400Object1Example:
          |      value:
          |        timestamp: 2022-11-07T10:31:19.915802
          |        status: "400"
          |        error:
          |          "Exception thrown: org.springframework.web.bind.MissingRequestHeaderException:\
          |                    \ Missing request header 'CorrelationID' for method parameter of type String"
          |        message:
          |          Missing request header 'CorrelationID' for method parameter of type
          |          String
          |    SimpleError404Object1Example:
          |      value:
          |        timestamp: 2022-11-07T10:32:13.912+0000
          |        status: "404"
          |        error: Not Found
          |        message: No message available
          |        path: /v1/person-details/
          |  securitySchemes:
          |    oAuth2:
          |      type: oauth2
          |      description: Keycloak OAuth2 Client Credentials Flow
          |      flows:
          |        clientCredentials:
          |          tokenUrl: /realms/hip/protocol/openid-connect/token
          |          scopes:
          |            read:address-matching: Return all addresses associated with an individual
          |""".stripMargin
    )
  }

  private def emsAddressWeightingService(): OasDocument = {
    OasDocument(
      id = "ems-address-weighting-service",
      content =
        """
          |---
          |openapi: 3.0.1
          |info:
          |  title: API Documentation for Address Weighting Service
          |  description: |-
          |    This relates to the address weighting API service. When a request is sent with details about a person, the API responds   with details of a matched person, if a match is found. If no matched person is found, the response will indicate as such.
          |    ```
          |    Change Log
          |    ```
          |
          |      | Version | Date | Author | Description |
          |      |---|-----|------|-----|
          |      | 1.0.0 | 24-11-2022 | Mark Stead | Initial draft - created from EMS OAS file created by Ankur Mishra |
          |      | 1.0.1 | 20-12-2022 | Mark Stead | Removed version from server parameters and added scopes |
          |  termsOfService: urn:tos
          |  contact: {}
          |  license:
          |    name: Apache 2.0
          |    url: http://www.apache.org/licenses/LICENSE-2.0
          |  version: "1.0"
          |  x-integration-catalogue:
          |    reviewed-date: 2022-11-17T12:00:00Z
          |    platform: HIP
          |    publisher-reference: ems-address-weighting-service
          |    short-description: Address Weighting Service API
          |    status: ALPHA
          |    backends:
          |      - EMS
          |servers:
          |  - url: https://api.hip.prod.eis.ns2p.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Production
          |  - url: https://api.hip.test.eis.ns2n.corp.hmrc.gov.uk/ems/
          |    description: Corporate - Test
          |  - url: https://hip.ws.hmrc.gov.uk/ems/
          |    description: MDTP - Production
          |  - url: https://hip.ws.ibt.hmrc.gov.uk/ems/
          |    description: MDTP - QA
          |security:
          |  - oAuth2:
          |      - read:address-weighting
          |tags:
          |  - name: person-plus-addresses-endpoint
          |    description: Person Plus Addresses Endpoint
          |paths:
          |  /v1/person-address-match:
          |    post:
          |      tags:
          |        - person-plus-addresses-endpoint
          |      description:
          |        Send a request containing details about a person. API response
          |        will contain details of a matching person (if found) along with match score
          |        and the single best identified address linked to that person
          |      operationId: doPersonLookupUsingPOST
          |      parameters:
          |        - name: CorrelationID
          |          in: header
          |          description: DES/IF Correlation ID - used for traceability purposes when present
          |          required: true
          |          schema:
          |            type: string
          |        - name: OriginatingSystem
          |          in: header
          |          description: The Originating System
          |          schema:
          |            type: string
          |      requestBody:
          |        description: the request parameters passed to match an entity
          |        content:
          |          application/json:
          |            schema:
          |              $ref: "#/components/schemas/PersonAddressMatchRequest"
          |            examples:
          |              PersonAddressMatchRequestExample:
          |                $ref: "#/components/examples/PersonAddressMatchRequestExample"
          |              PersonAddressMatchRequest2Example:
          |                $ref: "#/components/examples/PersonAddressMatchRequest2Example"
          |        required: true
          |      responses:
          |        "200":
          |          description: OK
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/MatchPersonAddressResponseBody"
          |              examples:
          |                responseBodyExample:
          |                  $ref: "#/components/examples/ResponseExample"
          |                personNotFoundResponseBodyExample:
          |                  $ref: "#/components/examples/PersonNotFoundResponseExample"
          |        "201":
          |          description: Created
          |          content: {}
          |        "400":
          |          description: Bad Request
          |          content:
          |            application/json:
          |              schema:
          |                required:
          |                  - origin
          |                  - response
          |                type: object
          |                properties:
          |                  origin:
          |                    $ref: "#/components/schemas/HIP-originEnum"
          |                  response:
          |                    oneOf:
          |                      - $ref: "#/components/schemas/HIP-failureResponse"
          |                      - $ref: "#/components/schemas/SimpleError400Object"
          |                additionalProperties: false
          |              examples:
          |                BadRequest:
          |                  $ref: "#/components/examples/SimpleError400ObjectExample"
          |        "401":
          |          description: Unauthorized
          |          content: {}
          |        "403":
          |          description: Forbidden
          |          content: {}
          |        "404":
          |          description: Not Found
          |          content: {}
          |        "415":
          |          description: Unsupported media-type
          |          content: {}
          |        "422":
          |          description: Unprocessable
          |          content:
          |            application/json:
          |              schema:
          |                $ref: "#/components/schemas/SimpleError422Object"
          |              examples:
          |                Unprocessable:
          |                  $ref: "#/components/examples/SimpleError422ObjectExample"
          |        "500":
          |          description: Internal Server Error
          |          content:
          |            application/json:
          |              schema:
          |                required:
          |                  - origin
          |                  - response
          |                type: object
          |                properties:
          |                  origin:
          |                    $ref: "#/components/schemas/HIP-originEnum"
          |                  response:
          |                    oneOf:
          |                      - $ref: "#/components/schemas/HIP-failureResponse"
          |                      - $ref: "#/components/schemas/SimpleError500Object"
          |                additionalProperties: false
          |              examples:
          |                InternalServerError:
          |                  $ref: "#/components/examples/SimpleError500ObjectExample"
          |      deprecated: false
          |      x-codegen-request-body-name: matchRequest
          |components:
          |  schemas:
          |    AddressMetadataMatchingDatasets:
          |      title: AddressMetadataMatchingDatasets
          |      type: object
          |      properties:
          |        effectiveDate:
          |          type: string
          |          example: yyyy-MM-dd
          |        name:
          |          type: string
          |    MatchPersonAddressResponseBody:
          |      title: MatchPersonAddressResponseBody
          |      type: object
          |      properties:
          |        addressSource:
          |          description:
          |            the source evidence dataset where the matched entity's details
          |            are found
          |          type: string
          |        matchedEntity:
          |          $ref: "#/components/schemas/MatchedPersonWithAddress"
          |        matchingOutcome:
          |          description:
          |            "the outcome of person matching, confirming whether a person\
          |                        \ matching the request parameters was found"
          |          enum:
          |            - IDENTIFIED_A_PERSON
          |            - FAILED_TO_IDENTIFY_THE_PERSON
          |            - FAILED_TO_IDENTIFY_A_SINGLE_BEST_MATCH_FOR_PERSON
          |          type: string
          |        requestNino:
          |          description: National Insurance Number of the person contained in the request
          |          type: string
          |        uuid:
          |          description: uuid of the response
          |          type: string
          |    MatchedPersonWithAddress:
          |      title: MatchedPersonWithAddress
          |      description: object contains person and address details
          |      type: object
          |      properties:
          |        address:
          |          $ref: "#/components/schemas/OneOfresponseBodyMatchedEntityAddressesItems"
          |        confidence:
          |          format: double
          |          description:
          |            "match score to indicate how good the match is, with a value\
          |                        \ between 0 and 1.  The higher the score, the better the match based on\
          |                        \ the parameters contained within the request."
          |          type: number
          |        dateOfBirth:
          |          $ref: "#/components/schemas/PartialDate"
          |        firstname:
          |          description: The first name or forename of the person
          |          type: string
          |        key:
          |          description: key or id of the entity matched
          |          type: string
          |        nino:
          |          description: The National Insurance Number of the person
          |          type: string
          |        surname:
          |          description: The surname of the person
          |          type: string
          |    OneOfresponseBodyMatchedEntityAddressesItems:
          |      title: OneOfresponseBodyMatchedEntityAddressesItems
          |      description: address details of the matched entity
          |      type: object
          |      properties:
          |        buildingName:
          |          description: name of the building
          |          type: string
          |        buildingNumber:
          |          description: building number
          |          type: string
          |        county:
          |          description: county
          |          type: string
          |        dependentThoroughfare:
          |          description: the thoroughfare
          |          type: string
          |        locality:
          |          description: the locality
          |          type: string
          |        postTown:
          |          description: the post town
          |          type: string
          |        postcode:
          |          description: the post code
          |          type: string
          |        subbuildingName:
          |          description: the sub building name
          |          type: string
          |        thoroughfare:
          |          description: the thorough fare
          |          type: string
          |        uprn:
          |          description:
          |            "Unique Property Reference Number (UPRN) of the address, as\
          |                        \ defined by Ordnance Survey"
          |          type: string
          |        latestDate:
          |          format: date
          |          description:
          |            the latest date when the matched entity details are found in
          |            an evidence dataset
          |          type: string
          |        matchingDatasets:
          |          description: the dataset(s) in which the entity details have been matched
          |          type: array
          |          items:
          |            $ref: "#/components/schemas/AddressMetadataMatchingDatasets"
          |        score:
          |          format: int32
          |          description: score for a matched entity based on the scorecard
          |          type: integer
          |    PartialDate:
          |      title: PartialDate
          |      required:
          |        - year
          |      type: object
          |      properties:
          |        day:
          |          type: string
          |        month:
          |          type: string
          |        year:
          |          type: string
          |    PersonAddressMatchRequest:
          |      title: PersonAddressMatchRequest
          |      required:
          |        - addressLine1
          |        - country
          |        - dateOfBirth
          |        - firstName
          |        - nino
          |        - surname
          |        - uuid
          |      type: object
          |      properties:
          |        addressLine1:
          |          description: Address Line 1 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 1
          |          type: string
          |        addressLine2:
          |          description: Address Line 2 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine3:
          |          description: Address Line 3 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine4:
          |          description: Address Line 4 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        addressLine5:
          |          description: Address Line 5 of the person's current or last known address
          |          maxLength: 35
          |          minLength: 0
          |          type: string
          |        country:
          |          description:
          |            "The country of the person's current or last known address\
          |                        \ address. This should be entered as <q>UNITED KINGDOM</q>, which includes\
          |                        \ England, Scotland, Wales and Northern Ireland but excludes the Isle\
          |                        \ of Man and Channel Islands. For international addresses (anything not\
          |                        \ within the United Kingdom) the description should conform to ISO-3166\
          |                        \ standard e.g. <q>AFGANISTAN</q>."
          |          maxLength: 99
          |          minLength: 1
          |          type: string
          |        dateOfBirth:
          |          format: date
          |          description:
          |            The date of birth of the person. Note that the date must not
          |            be before 1875 and the person must be 16 years or older
          |          type: string
          |        firstName:
          |          description: The first name or forename of the person
          |          maxLength: 99
          |          minLength: 1
          |          pattern: "^[-a-zA-Z ']{1,99}?$"
          |          type: string
          |        nino:
          |          description: The National Insurance Number of the person
          |          pattern: "^((?:[ACEHJLMOPRSWXY][A-CEGHJ-NPR-TW-Z]|B[A-CEHJ-NPR-TW-Z]|G[ACEGHJ-NPR-TW-Z]|[KT][A-CEGHJ-MPR-TW-Z]|N[A-CEGHJL-NPR-SW-Z]|Z[A-CEGHJ-NPR-TW-Y])[0-9]{6}[A-D]?)$"
          |          type: string
          |        postcode:
          |          description:
          |            The postcode of the person's current or last known address.
          |            Postcode should only be populated when country is UNITED KINGDOM.
          |          pattern:
          |            "([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([A-Za-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9][A-Za-z]?))))\\\
          |                        s?[0-9][A-Za-z]{2})"
          |          type: string
          |        secondName:
          |          description:
          |            The second or middle name(s) of the person. Should be separated
          |            by spaces where there are multiple
          |          maxLength: 99
          |          minLength: 0
          |          pattern: "^[-a-zA-Z ']{1,99}?$"
          |          type: string
          |        sex:
          |          description: The person's sex or gender
          |          enum:
          |            - Male
          |            - Female
          |            - Not Known
          |            - Not Specified
          |          type: string
          |        surname:
          |          description: The surname or family name of the person.
          |          maxLength: 99
          |          minLength: 1
          |          pattern: "^[-a-zA-Z ']{2,99}?$"
          |          type: string
          |        title:
          |          description: The title of the person
          |          enum:
          |            - Mr
          |            - Mrs
          |            - Miss
          |            - Ms
          |            - Dr
          |            - Rev
          |            - Sir
          |            - Lady
          |            - Lord
          |            - Dame
          |          type: string
          |        uuid:
          |          description: The universally unique identifier
          |          maxLength: 36
          |          minLength: 1
          |          type: string
          |    SimpleError400Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |        message:
          |          description: error message
          |          type: string
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |    SimpleError422Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |        description:
          |          description: error description
          |          type: array
          |          items:
          |            type: object
          |    SimpleError500Object:
          |      title: SimpleErrorObject
          |      type: object
          |      properties:
          |        error:
          |          description: error
          |          type: string
          |        message:
          |          description: error message
          |          type: string
          |        timestamp:
          |          format: date-time
          |          description: timestamp
          |          type: string
          |    HIP-originEnum:
          |      enum:
          |        - HIP
          |        - HoD
          |      type: string
          |    HIP-failureResponse:
          |      required:
          |        - failures
          |      type: object
          |      properties:
          |        failures:
          |          minItems: 1
          |          uniqueItems: true
          |          type: array
          |          items:
          |            required:
          |              - type
          |              - reason
          |            type: object
          |            properties:
          |              type:
          |                type: string
          |              reason:
          |                type: string
          |            additionalProperties: false
          |    HIP-originResponse:
          |      required:
          |        - origin
          |        - response
          |      type: object
          |      properties:
          |        origin:
          |          $ref: "#/components/schemas/HIP-originEnum"
          |        response:
          |          $ref: "#/components/schemas/HIP-failureResponse"
          |      additionalProperties: false
          |  examples:
          |    PersonAddressMatchRequestExample:
          |      value:
          |        firstName: GRAHAM
          |        surname: MACDONALD
          |        country: United Kingdom
          |        addressLine1: ADMIRALS WAY
          |        dateOfBirth: 1979-05-01
          |        uuid: 472ee977-0ca2-448b-ab13-d1e01060b71d
          |        nino: JB224466
          |    PersonAddressMatchRequest2Example:
          |      value:
          |        firstName: GRAHAM
          |        surname: MACDONALD
          |        title: Mr
          |        addressLine1: ADMIRALS WAY
          |        postcode: E14 9XQ
          |        country: United Kingdom
          |        dateOfBirth: 1979-05-01
          |        uuid: 472ee977-0ca2-448b-ab13-d1e01060b71d
          |        nino: JX826162
          |    ResponseExample:
          |      value:
          |        uuid: 472ee977-0ca2-448b-ab13-d1e01060b71d
          |        requestNino: JX826162
          |        matchingOutcome: IDENTIFIED_A_PERSON
          |        addressSource: Enterprise Matching
          |        matchedEntity:
          |          nino: ""
          |          firstname: GRAHAM
          |          surname: MACDONALD
          |          key: P5641494192F
          |          dateOfBirth:
          |            year: "1979"
          |            month: "05"
          |            day: ""
          |          confidence: 0.75
          |          address:
          |            matchingDatasets:
          |              - name: COMPANIES_HOUSE_PSC
          |                effectiveDate: 2021-06-05
          |            uprn: "6063671"
          |            subbuildingName: SUITE 9
          |            buildingName: ENSIGN HOUSE
          |            buildingNumber: "17"
          |            dependentThoroughfare: ""
          |            thoroughfare: ADMIRALS WAY
          |            locality: CANARY WHARF
          |            postTown: LONDON
          |            county: TOWER HAMLETS
          |            postcode: E14 9XQ
          |            latestDate: 2021-06-05
          |            score: 11
          |    PersonNotFoundResponseExample:
          |      value:
          |        uuid: 472ee977-0ca2-448b-ab13-d1e01060b71d
          |        requestNino: JX826162
          |        matchingOutcome: FAILED_TO_IDENTIFY_THE_PERSON
          |        addressSource: Enterprise Matching
          |    SimpleError400ObjectExample:
          |      value:
          |        error:
          |          "Exception thrown: org.springframework.web.bind.MissingRequestHeaderException:\
          |                    \ Missing request header CorrelationID for method parameter of type String"
          |        message:
          |          Missing request header 'CorrelationID' for method parameter of type
          |          String
          |        timestamp: 2022-11-04T17:38:07.456276
          |    SimpleError422ObjectExample:
          |      value:
          |        error: Unprocessable
          |        description:
          |          - dateOfBirth: invalid format
          |    SimpleError500ObjectExample:
          |      value:
          |        error:
          |          "Exception thrown: reactor.core.Exceptions$ReactiveException: javax.net.ssl.SSLHandshakeException:\
          |                    \ PKIX path validation failed: java.security.cert.CertPathValidatorException:\
          |                    \ validity check failed"
          |        message:
          |          "javax.net.ssl.SSLHandshakeException: PKIX path validation failed:\
          |                    \ java.security.cert.CertPathValidatorException: validity check failed"
          |        timestamp: 2022-11-04T17:38:07.456276
          |  securitySchemes:
          |    oAuth2:
          |      type: oauth2
          |      description: Keycloak OAuth2 Client Credentials Flow
          |      flows:
          |        clientCredentials:
          |          tokenUrl: /realms/hip/protocol/openid-connect/token
          |          scopes:
          |            read:address-weighting:
          |              Return the best matching address and weighted
          |              score for an individual
          |""".stripMargin
    )
  }

}
