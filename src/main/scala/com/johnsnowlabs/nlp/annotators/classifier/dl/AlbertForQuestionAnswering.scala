/*
 * Copyright 2017-2022 John Snow Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.johnsnowlabs.nlp.annotators.classifier.dl

import com.johnsnowlabs.ml.tensorflow._
import com.johnsnowlabs.ml.tensorflow.sentencepiece.{
  ReadSentencePieceModel,
  SentencePieceWrapper,
  WriteSentencePieceModel
}
import com.johnsnowlabs.nlp._
import com.johnsnowlabs.nlp.annotators.common._
import com.johnsnowlabs.nlp.serialization.MapFeature
import com.johnsnowlabs.nlp.util.io.{ExternalResource, ReadAs, ResourceHelper}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.ml.param.{BooleanParam, IntArrayParam, IntParam}
import org.apache.spark.ml.util.Identifiable
import org.apache.spark.sql.SparkSession

import java.io.File

/** AlbertForQuestionAnswering can load ALBERT Models with a span classification head on top for
  * extractive question-answering tasks like SQuAD (a linear layer on top of the hidden-states
  * output to compute span start logits and span end logits).
  *
  * Pretrained models can be loaded with `pretrained` of the companion object:
  * {{{
  * val spanClassifier = AlbertForQuestionAnswering.pretrained()
  *   .setInputCols(Array("document_question", "document_context"))
  *   .setOutputCol("answer")
  * }}}
  * The default model is `"albert_base_qa_squad2"`, if no name is provided.
  *
  * For available pretrained models please see the
  * [[https://nlp.johnsnowlabs.com/models?task=Question+Answering Models Hub]].
  *
  * To see which models are compatible and how to import them see
  * [[https://github.com/JohnSnowLabs/spark-nlp/discussions/5669]]. and the
  * [[https://github.com/JohnSnowLabs/spark-nlp/blob/master/src/test/scala/com/johnsnowlabs/nlp/annotators/classifier/dl/AlbertForQuestionAnsweringTestSpec.scala AlbertForQuestionAnsweringTestSpec]].
  *
  * ==Example==
  * {{{
  * import spark.implicits._
  * import com.johnsnowlabs.nlp.base._
  * import com.johnsnowlabs.nlp.annotator._
  * import org.apache.spark.ml.Pipeline
  *
  * val document = new MultiDocumentAssembler()
  *   .setInputCols("question", "context")
  *   .setOutputCols("document_question", "document_context")
  *
  * val questionAnswering = AlbertForQuestionAnswering.pretrained()
  *   .setInputCols(Array("document_question", "document_context"))
  *   .setOutputCol("answer")
  *   .setCaseSensitive(false)
  *
  * val pipeline = new Pipeline().setStages(Array(
  *   document,
  *   questionAnswering
  * ))
  *
  * val data = Seq("What's my name?", "My name is Clara and I live in Berkeley.").toDF("question", "context")
  * val result = pipeline.fit(data).transform(data)
  *
  * result.select("label.result").show(false)
  * +---------------------+
  * |result               |
  * +---------------------+
  * |[Clara]              |
  * ++--------------------+
  * }}}
  *
  * @see
  *   [[AlbertForSequenceClassification]] for sequence-level classification
  * @see
  *   [[https://nlp.johnsnowlabs.com/docs/en/annotators Annotators Main Page]] for a list of
  *   transformer based classifiers
  * @param uid
  *   required uid for storing annotator to disk
  * @groupname anno Annotator types
  * @groupdesc anno
  *   Required input and expected output annotator types
  * @groupname Ungrouped Members
  * @groupname param Parameters
  * @groupname setParam Parameter setters
  * @groupname getParam Parameter getters
  * @groupname Ungrouped Members
  * @groupprio param  1
  * @groupprio anno  2
  * @groupprio Ungrouped 3
  * @groupprio setParam  4
  * @groupprio getParam  5
  * @groupdesc param
  *   A list of (hyper-)parameter keys this annotator can take. Users can set and get the
  *   parameter values through setters and getters, respectively.
  */
class AlbertForQuestionAnswering(override val uid: String)
    extends AnnotatorModel[AlbertForQuestionAnswering]
    with HasBatchedAnnotate[AlbertForQuestionAnswering]
    with WriteTensorflowModel
    with WriteSentencePieceModel
    with HasCaseSensitiveProperties {

  /** Annotator reference id. Used to identify elements in metadata or to refer to this annotator
    * type
    */
  def this() = this(Identifiable.randomUID("AlbertForQuestionAnswering"))

  /** Input Annotator Types: DOCUMENT, DOCUMENT
    *
    * @group anno
    */
  override val inputAnnotatorTypes: Array[String] =
    Array(AnnotatorType.DOCUMENT, AnnotatorType.DOCUMENT)

  /** Output Annotator Types: CHUNK
    *
    * @group anno
    */
  override val outputAnnotatorType: AnnotatorType = AnnotatorType.CHUNK

  /** ConfigProto from tensorflow, serialized into byte array. Get with
    * `config_proto.SerializeToString()`
    *
    * @group param
    */
  val configProtoBytes = new IntArrayParam(
    this,
    "configProtoBytes",
    "ConfigProto from tensorflow, serialized into byte array. Get with config_proto.SerializeToString()")

  /** @group setParam */
  def setConfigProtoBytes(bytes: Array[Int]): AlbertForQuestionAnswering.this.type =
    set(this.configProtoBytes, bytes)

  /** @group getParam */
  def getConfigProtoBytes: Option[Array[Byte]] = get(this.configProtoBytes).map(_.map(_.toByte))

  /** Max sentence length to process (Default: `128`)
    *
    * @group param
    */
  val maxSentenceLength =
    new IntParam(this, "maxSentenceLength", "Max sentence length to process")

  /** @group setParam */
  def setMaxSentenceLength(value: Int): this.type = {
    require(
      value <= 512,
      "ALBERT models do not support sequences longer than 512 because of trainable positional embeddings.")
    require(value >= 1, "The maxSentenceLength must be at least 1")
    set(maxSentenceLength, value)
    this
  }

  /** @group getParam */
  def getMaxSentenceLength: Int = $(maxSentenceLength)

  /** It contains TF model signatures for the laded saved model
    *
    * @group param
    */
  val signatures = new MapFeature[String, String](model = this, name = "signatures")

  /** @group setParam */
  def setSignatures(value: Map[String, String]): this.type = {
    if (get(signatures).isEmpty)
      set(signatures, value)
    this
  }

  /** @group getParam */
  def getSignatures: Option[Map[String, String]] = get(this.signatures)

  private var _model: Option[Broadcast[TensorflowAlbertClassification]] = None

  /** @group setParam */
  def setModelIfNotSet(
      spark: SparkSession,
      tensorflowWrapper: TensorflowWrapper,
      spp: SentencePieceWrapper): AlbertForQuestionAnswering = {
    if (_model.isEmpty) {
      _model = Some(
        spark.sparkContext.broadcast(
          new TensorflowAlbertClassification(
            tensorflowWrapper,
            spp,
            configProtoBytes = getConfigProtoBytes,
            tags = Map.empty[String, Int],
            signatures = getSignatures)))
    }

    this
  }

  /** @group getParam */
  def getModelIfNotSet: TensorflowAlbertClassification = _model.get.value

  /** Whether to lowercase tokens or not (Default: `false`).
    *
    * @group setParam
    */
  override def setCaseSensitive(value: Boolean): this.type = set(this.caseSensitive, value)

  setDefault(batchSize -> 8, maxSentenceLength -> 128, caseSensitive -> false)

  /** takes a document and annotations and produces new annotations of this annotator's annotation
    * type
    *
    * @param batchedAnnotations
    *   Annotations that correspond to inputAnnotationCols generated by previous annotators if any
    * @return
    *   any number of annotations processed for every input annotation. Not necessary one to one
    *   relationship
    */
  override def batchAnnotate(batchedAnnotations: Seq[Array[Annotation]]): Seq[Seq[Annotation]] = {
    batchedAnnotations.map(annotations => {

      val documents = annotations
        .filter(_.annotatorType == AnnotatorType.DOCUMENT)
        .toSeq

      if (documents.nonEmpty) {
        getModelIfNotSet.predictSpan(
          documents,
          $(maxSentenceLength),
          $(caseSensitive),
          MergeTokenStrategy.sentencePiece)
      } else {
        Seq.empty[Annotation]
      }
    })
  }

  override def onWrite(path: String, spark: SparkSession): Unit = {
    super.onWrite(path, spark)
    writeTensorflowModelV2(
      path,
      spark,
      getModelIfNotSet.tensorflowWrapper,
      "_albert_classification",
      AlbertForQuestionAnswering.tfFile,
      configProtoBytes = getConfigProtoBytes)
    writeSentencePieceModel(
      path,
      spark,
      getModelIfNotSet.spp,
      "_albert",
      AlbertForQuestionAnswering.sppFile)
  }
}

trait ReadablePretrainedAlbertForQAModel
    extends ParamsAndFeaturesReadable[AlbertForQuestionAnswering]
    with HasPretrained[AlbertForQuestionAnswering] {
  override val defaultModelName: Some[String] = Some("albert_base_qa_squad2")

  /** Java compliant-overrides */
  override def pretrained(): AlbertForQuestionAnswering = super.pretrained()

  override def pretrained(name: String): AlbertForQuestionAnswering = super.pretrained(name)

  override def pretrained(name: String, lang: String): AlbertForQuestionAnswering =
    super.pretrained(name, lang)

  override def pretrained(
      name: String,
      lang: String,
      remoteLoc: String): AlbertForQuestionAnswering =
    super.pretrained(name, lang, remoteLoc)
}

trait ReadAlbertForQATensorflowModel extends ReadTensorflowModel with ReadSentencePieceModel {
  this: ParamsAndFeaturesReadable[AlbertForQuestionAnswering] =>

  override val tfFile: String = "albert_classification_tensorflow"
  override val sppFile: String = "albert_spp"

  def readTensorflow(
      instance: AlbertForQuestionAnswering,
      path: String,
      spark: SparkSession): Unit = {

    val tf = readTensorflowModel(path, spark, "_albert_classification_tf", initAllTables = false)
    val spp = readSentencePieceModel(path, spark, "_albert_spp", sppFile)
    instance.setModelIfNotSet(spark, tf, spp)
  }

  addReader(readTensorflow)

  def loadSavedModel(tfModelPath: String, spark: SparkSession): AlbertForQuestionAnswering = {
    val f = new File(tfModelPath)
    val savedModel = new File(tfModelPath, "saved_model.pb")
    require(f.exists, s"Folder $tfModelPath not found")
    require(f.isDirectory, s"File $tfModelPath is not folder")
    require(
      savedModel.exists(),
      s"savedModel file saved_model.pb not found in folder $tfModelPath")
    val sppModelPath = tfModelPath + "/assets"
    val sppModel = new File(sppModelPath, "spiece.model")
    require(
      sppModel.exists(),
      s"SentencePiece model spiece.model not found in folder $sppModelPath")

    val (wrapper, signatures) =
      TensorflowWrapper.read(tfModelPath, zipped = false, useBundle = true)
    val spp = SentencePieceWrapper.read(sppModel.toString)

    val _signatures = signatures match {
      case Some(s) => s
      case None => throw new Exception("Cannot load signature definitions from model!")
    }

    /** the order of setSignatures is important if we use getSignatures inside setModelIfNotSet */
    new AlbertForQuestionAnswering()
      .setSignatures(_signatures)
      .setModelIfNotSet(spark, wrapper, spp)
  }
}

/** This is the companion object of [[AlbertForQuestionAnswering]]. Please refer to that class for
  * the documentation.
  */
object AlbertForQuestionAnswering
    extends ReadablePretrainedAlbertForQAModel
    with ReadAlbertForQATensorflowModel
