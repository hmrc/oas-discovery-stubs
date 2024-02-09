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

import com.google.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.oasdiscoverystubs.data.{ApiDeploymentData, OasDocumentData}
import uk.gov.hmrc.oasdiscoverystubs.models.ApiDeployment
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.Instant
import scala.collection.concurrent.TrieMap

@Singleton
class OasDiscoveryController @Inject()(cc: ControllerComponents)
  extends BackendController(cc)
  with ApiDeploymentData
  with OasDocumentData {

  private val apiDeployments: TrieMap[String, ApiDeployment] = {
    val apiDeployments = TrieMap[String, ApiDeployment]()

    allApiDeployments().foreach(
      apiDeployment =>
        apiDeployments.put(apiDeployment.id, apiDeployment)
    )

    apiDeployments
  }

  private val oasDocuments: Map[String, String] = {
    allOasDocuments()
      .map(oasDocument => oasDocument.id -> oasDocument.content)
      .toMap
  }

  def allDeployments(): Action[AnyContent] = Action {
    Ok(Json.toJson(apiDeployments.values))
  }

  def oas(id: String): Action[AnyContent] = Action {
    oasDocuments
      .get(id)
      .map(Ok(_).withHeaders(CONTENT_TYPE -> "application/yaml"))
      .getOrElse(NotFound)
  }

  def deployNow(id: String): Action[AnyContent] = Action {
    apiDeployments
      .replace(id, ApiDeployment(id, Instant.now()))
      .map(_ => NoContent)
      .getOrElse(NotFound)
  }

}
