---
layout: model
title: Detect Diseases in Medical Text
author: John Snow Labs
name: bert_token_classifier_ner_bc5cdr_disease
date: 2022-07-25
tags: [en, ner, clinical, licensed, bertfortokenclassification]
task: Named Entity Recognition
language: en
edition: Spark NLP for Healthcare 4.0.0
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Chemicals, diseases, and their relations are among the most searched topics by PubMed users worldwide as they play central roles in many areas of biomedical research and healthcare, such as drug discovery and safety surveillance.

This model is trained with the `BertForTokenClassification` method from the `transformers` library and imported into Spark NLP. The model detects disease from a medical text

## Predicted Entities

`DISEASE`

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/Certification_Trainings/Healthcare/1.Clinical_Named_Entity_Recognition_Model.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/bert_token_classifier_ner_bc5cdr_disease_en_4.0.0_3.0_1658754395259.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}

```python
document_assembler = DocumentAssembler()\
    .setInputCol("text")\
    .setOutputCol("document")\

sentence_detector = SentenceDetectorDLModel.pretrained("sentence_detector_dl_healthcare", "en", "clinical/models")\
    .setInputCols(["document"])\
    .setOutputCol("sentence")

tokenizer = Tokenizer()\
    .setInputCols(["sentence"])\
    .setOutputCol("token")

ner_model = MedicalBertForTokenClassifier.pretrained("bert_token_classifier_ner_bc5cdr_disease", "en", "clinical/models")\
    .setInputCols(["sentence", "token"])\
    .setOutputCol("ner")\
    .setCaseSensitive(True)\
    .setMaxSentenceLength(512)

ner_converter = NerConverter()\
    .setInputCols(["sentence", "token", "ner"])\
    .setOutputCol("ner_chunk")

pipeline = Pipeline(stages=[
    document_assembler, 
    sentence_detector,
    tokenizer,
    ner_model,
    ner_converter   
    ])

model = pipeline.fit(spark.createDataFrame([[""]]).toDF("text"))

data = spark.createDataFrame([["""Indomethacin resulted in histopathologic findings typical of interstitial cystitis, such as leaky bladder epithelium and mucosal mastocytosis. The true incidence of nonsteroidal anti-inflammatory drug-induced cystitis in humans must be clarified by prospective clinical trials. An open-label phase II study of low-dose thalidomide in androgen-independent prostate cancer."""]]).toDF("text")

result = model.transform(data)
```
```scala
val document_assembler = new DocumentAssembler()
    .setInputCol("text")
    .setOutputCol("document")

val sentence_detector = SentenceDetectorDLModel.pretrained("sentence_detector_dl_healthcare", "en", "clinical/models")
    .setInputCols("document")
    .setOutputCol("sentence")

val tokenizer = new Tokenizer()
    .setInputCols("sentence")
    .setOutputCol("token")

val ner_model = MedicalBertForTokenClassifier.pretrained("bert_token_classifier_ner_bc5cdr_disease", "en", "clinical/models")
    .setInputCols(Array("sentence", "token"))
    .setOutputCol("ner")
    .setCaseSensitive(True)
    .setMaxSentenceLength(512)

val ner_converter = new NerConverter()
    .setInputCols(Array("sentence", "token", "ner"))
    .setOutputCol("ner_chunk")

val pipeline = new PipelineModel().setStages(Array(document_assembler, 
                                                   sentence_detector,
                                                   tokenizer,
                                                   ner_model,
                                                   ner_converter))

val data = Seq("""Indomethacin resulted in histopathologic findings typical of interstitial cystitis, such as leaky bladder epithelium and mucosal mastocytosis. The true incidence of nonsteroidal anti-inflammatory drug-induced cystitis in humans must be clarified by prospective clinical trials. An open-label phase II study of low-dose thalidomide in androgen-independent prostate cancer.""").toDS.toDF("text")

val result = model.fit(data).transform(data)
```
</div>

## Results

```bash
+---------------------+-------+
|ner_chunk            |label  |
+---------------------+-------+
|interstitial cystitis|DISEASE|
|mastocytosis         |DISEASE|
|cystitis             |DISEASE|
|prostate cancer      |DISEASE|
+---------------------+-------+
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_token_classifier_ner_bc5cdr_disease|
|Compatibility:|Spark NLP for Healthcare 4.0.0+|
|License:|Licensed|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[ner]|
|Language:|en|
|Size:|404.2 MB|
|Case sensitive:|true|
|Max sentence length:|512|

## References

[https://github.com/cambridgeltl/MTL-Bioinformatics-2016](https://github.com/cambridgeltl/MTL-Bioinformatics-2016)

## Benchmarking

```bash
 label         precision  recall  f1-score  support 
 B-DISEASE     0.7905     0.9146  0.8480    4424    
 I-DISEASE     0.6521     0.8725  0.7464    2737    
 micro-avg     0.7328     0.8985  0.8072    7161    
 macro-avg     0.7213     0.8935  0.7972    7161    
 weighted-avg  0.7376     0.8985  0.8092    7161  
```
