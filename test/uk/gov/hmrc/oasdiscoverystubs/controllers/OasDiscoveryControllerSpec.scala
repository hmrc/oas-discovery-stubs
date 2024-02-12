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

package uk.gov.hmrc.oasdiscoverystubs.controllers

import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.oasdiscoverystubs.data.{ApiDeploymentData, OasDocumentData}
import uk.gov.hmrc.oasdiscoverystubs.models.ApiDeployment

import java.time.{Clock, Instant, ZoneId}

class OasDiscoveryControllerSpec extends AnyFreeSpec with Matchers with ApiDeploymentData with OasDocumentData with OptionValues {

  private val clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())

  "apiDeployments" - {
    "must return all API deployments" in {
      val application = buildApplication()

      running(application) {
        val request = FakeRequest(routes.OasDiscoveryController.allDeployments())
        val result = route(application, request).get

        status(result) mustBe OK
        contentAsJson(result) mustBe Json.toJson(allApiDeployments().sorted)
      }
    }
  }

  "oas" - {
    "must return the OAS document for an API when it exists" in {
      val application = buildApplication()

      running(application) {
        val request = FakeRequest(routes.OasDiscoveryController.oas(ApiDeploymentData.emsIdentity.id))
        val result = route(application, request).get

        status(result) mustBe OK
        contentAsString(result) mustBe OasDocumentData.emsIdentity.content
        contentType(result).value mustBe "application/yaml"
      }
    }

    "must return 4040 Not Found when the API does not exist" in {
      val application = buildApplication()

      running(application) {
        val request = FakeRequest(routes.OasDiscoveryController.oas("test-id"))
        val result = route(application, request).get

        status(result) mustBe NOT_FOUND
      }
    }
  }

  "deployNow" - {
    "must update the last deployment date of an API" in {
      val application = buildApplication()

      running(application) {
        val request1 = FakeRequest(routes.OasDiscoveryController.deployNow(ApiDeploymentData.emsIdentity.id))
        val result1 = route(application, request1).get

        status(result1) mustBe NO_CONTENT

        val request2 = FakeRequest(routes.OasDiscoveryController.allDeployments())
        val result2 =  route(application, request2).get

        val expected = ApiDeployment(ApiDeploymentData.emsIdentity.id, clock.instant())
        status(result2) mustBe OK
        contentAsJson(result2).as[Seq[ApiDeployment]] must contain (expected)
      }
    }
  }

  private def buildApplication(): Application = {
    GuiceApplicationBuilder()
      .overrides(bind[Clock].toInstance(clock))
      .build()
  }

}
