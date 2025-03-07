---
layout: model
title: Detect PHI for Generic Deidentification in Romanian
author: John Snow Labs
name: ner_deid_generic
date: 2022-07-08
tags: [deidentification, word2vec, phi, generic, ner, ro, licensed]
task: Named Entity Recognition
language: ro
edition: Spark NLP for Healthcare 3.5.0
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Named Entity Recognition annotators allow for a generic model to be trained by using a Deep Learning architecture (Char CNNs - BiLSTM - CRF - word embeddings) inspired on a former state of the art model for NER: Chiu & Nicols, Named Entity Recognition with Bidirectional LSTM,CNN.

Deidentification NER (Romanian) is a Named Entity Recognition model that annotates text to find protected health information that may need to be de-identified. It is trained with word2vec embeddings and can detect 7 generic entities.

This NER model is trained with a combination of custom datasets with several data augmentation mechanisms.

## Predicted Entities

`AGE`, `CONTACT`, `DATE`, `ID`, `LOCATION`, `NAME`, `PROFESSION`

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/ner_deid_generic_ro_3.5.0_3.0_1657267952051.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = DocumentAssembler()\
         .setInputCol("text")\
         .setOutputCol("document")
         
 sentenceDetector = SentenceDetector()\
         .setInputCols(["document"])\
         .setOutputCol("sentence")

 tokenizer = Tokenizer()\
         .setInputCols(["sentence"])\
         .setOutputCol("token")

 embeddings = WordEmbeddingsModel.pretrained("w2v_cc_300d","ro")\
 	.setInputCols(["sentence","token"])\
 	.setOutputCol("word_embeddings")

 clinical_ner = MedicalNerModel.pretrained("ner_deid_generic", "ro", "clinical/models")\
 	.setInputCols(["sentence","token","word_embeddings"])\
 	.setOutputCol("ner")

 ner_converter = NerConverter()\
 	.setInputCols(["sentence", "token", "ner"])\
 	.setOutputCol("ner_chunk")
     
 nlpPipeline = Pipeline(stages=[documentAssembler, sentenceDetector, tokenizer, embeddings, clinical_ner, ner_converter])

 text = """ Spitalul Pentru Ochi de Deal, Drumul Oprea Nr. 972 Vaslui, 737405 România
 Tel: +40(235)413773
 Data setului de analize: 25 May 2022 15:36:00
 Nume si Prenume : BUREAN MARIA, Varsta: 77
 Medic : Agota Evelyn Tımar
 C.N.P : 2450502264401"""

 data = spark.createDataFrame([[text]]).toDF("text")

 results = nlpPipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler()
        .setInputCol("text")
        .setOutputCol("document")

val sentenceDetector = new SentenceDetector()
        .setInputCols(Array("document"))
        .setOutputCol("sentence")

val tokenizer = new Tokenizer()
        .setInputCols(Array("sentence"))
        .setOutputCol("token")

val embeddings = WordEmbeddingsModel.pretrained("w2v_cc_300d","ro")
        .setInputCols(Array("sentence","token"))
        .setOutputCol("word_embeddings")

val clinical_ner = MedicalNerModel.pretrained("ner_deid_generic", "ro", "clinical/models")
        .setInputCols(Array("sentence","token","word_embeddings"))
        .setOutputCol("ner")

val ner_converter = new NerConverter()
        .setInputCols(Array("sentence", "token", "ner"))
        .setOutputCol("ner_chunk")
  
val pipeline = new Pipeline().setStages(Array(documentAssembler, sentenceDetector, tokenizer, embeddings, clinical_ner, ner_converter))

val text = """Spitalul Pentru Ochi de Deal, Drumul Oprea Nr. 972 Vaslui, 737405 România
Tel: +40(235)413773
Data setului de analize: 25 May 2022 15:36:00
Nume si Prenume : BUREAN MARIA, Varsta: 77
Medic : Agota Evelyn Tımar
C.N.P : 2450502264401"""

val data = Seq(text).toDS.toDF("text")

val results = pipeline.fit(data).transform(data)
```
</div>

## Results

```bash
+----------------------------+---------+
|chunk                       |ner_label|
+----------------------------+---------+
|Spitalul Pentru Ochi de Deal|LOCATION |
|Drumul Oprea Nr.            |LOCATION |
|Vaslui                      |LOCATION |
|737405 România              |LOCATION |
|+40(235)413773              |CONTACT  |
|25 May 2022                 |DATE     |
|BUREAN MARIA                |NAME     |
|77                          |AGE      |
|Agota Evelyn Tımar          |NAME     |
|2450502264401               |ID       |
+----------------------------+---------+
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|ner_deid_generic|
|Compatibility:|Spark NLP for Healthcare 3.5.0+|
|License:|Licensed|
|Edition:|Official|
|Input Labels:|[sentence, token, embeddings]|
|Output Labels:|[ner]|
|Language:|ro|
|Size:|15.0 MB|

## References

- Custom John Snow Labs datasets
- Data augmentation techniques

## Benchmarking

```bash
       label  precision    recall  f1-score   support

         AGE       0.96      0.98      0.97      1235
     CONTACT       0.98      0.99      0.99       379
        DATE       0.90      0.85      0.87      5006
          ID       1.00      1.00      1.00       694
    LOCATION       0.89      0.90      0.90      1746
        NAME       0.95      0.98      0.96      3018
  PROFESSION       0.78      0.72      0.75       173

   micro-avg       0.93      0.91      0.92     12251
   macro-avg       0.92      0.92      0.92     12251
weighted-avg       0.92      0.91      0.92     12251
```