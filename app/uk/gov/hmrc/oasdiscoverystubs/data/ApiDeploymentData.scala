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

import uk.gov.hmrc.oasdiscoverystubs.models.ApiDeployment

import java.time.Instant
import java.time.temporal.ChronoUnit

trait ApiDeploymentData {

  import ApiDeploymentData._

  def allApiDeployments(): Seq[ApiDeployment] = Seq(emsIdentity, emsKeyingService, emsAddressWeightingService)

}

object ApiDeploymentData {

  val emsIdentity: ApiDeployment = {
    ApiDeployment(
      id = "ems-identity",
      deploymentTimestamp = Instant.now().minus(10, ChronoUnit.DAYS)
    )
  }

  val emsKeyingService: ApiDeployment = {
    ApiDeployment(
      id = "ems-keying-service",
      deploymentTimestamp = Instant.now().minus(20, ChronoUnit.DAYS)
    )
  }

  val emsAddressWeightingService: ApiDeployment = {
    ApiDeployment(
      id = "ems-address-weighting-service",
      deploymentTimestamp = Instant.now().minus(30, ChronoUnit.DAYS)
    )
  }

}
