---
layout: model
title: Arabic Part of Speech Tagger (from CAMeL-Lab)
author: John Snow Labs
name: bert_pos_bert_base_arabic_camelbert_ca_pos_msa
date: 2022-05-09
tags: [bert, pos, part_of_speech, ar, open_source]
task: Part of Speech Tagging
language: ar
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Part of Speech model model, uploaded to Hugging Face, adapted and imported into Spark NLP. `bert-base-arabic-camelbert-ca-pos-msa` is a Arabic model orginally trained by `CAMeL-Lab`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_pos_bert_base_arabic_camelbert_ca_pos_msa_ar_3.4.2_3.0_1652092479978.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = DocumentAssembler() \
        .setInputCol("text") \
        .setOutputCol("document")

sentenceDetector = SentenceDetectorDLModel.pretrained("sentence_detector_dl", "xx")\
       .setInputCols(["document"])\
       .setOutputCol("sentence")

tokenizer = Tokenizer() \
    .setInputCols("sentence") \
    .setOutputCol("token")

tokenClassifier = BertForTokenClassification.pretrained("bert_pos_bert_base_arabic_camelbert_ca_pos_msa","ar") \
    .setInputCols(["sentence", "token"]) \
    .setOutputCol("pos")

pipeline = Pipeline(stages=[documentAssembler, sentenceDetector, tokenizer, tokenClassifier])

data = spark.createDataFrame([["أنا أحب الشرارة NLP"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
          .setInputCol("text") 
          .setOutputCol("document")

val sentenceDetector = SentenceDetectorDLModel.pretrained("sentence_detector_dl", "xx")
       .setInputCols(Array("document"))
       .setOutputCol("sentence")

val tokenizer = new Tokenizer() 
    .setInputCols(Array("sentence"))
    .setOutputCol("token")

val tokenClassifier = BertForTokenClassification.pretrained("bert_pos_bert_base_arabic_camelbert_ca_pos_msa","ar") 
    .setInputCols(Array("sentence", "token")) 
    .setOutputCol("pos")

val pipeline = new Pipeline().setStages(Array(documentAssembler,sentenceDetector, tokenizer, tokenClassifier))

val data = Seq("أنا أحب الشرارة NLP").toDF("text")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_pos_bert_base_arabic_camelbert_ca_pos_msa|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document, token]|
|Output Labels:|[ner]|
|Language:|ar|
|Size:|407.3 MB|
|Case sensitive:|true|
|Max sentence length:|128|

## References

- https://huggingface.co/CAMeL-Lab/bert-base-arabic-camelbert-ca-pos-msa
- https://dl.acm.org/doi/pdf/10.5555/1621804.1621808
- https://arxiv.org/abs/2103.06678
- https://github.com/CAMeL-Lab/CAMeLBERT
- https://github.com/CAMeL-Lab/camel_tools