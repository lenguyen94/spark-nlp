---
layout: model
title: Hindi Part of Speech Tagger (from sagorsarker)
author: John Snow Labs
name: bert_pos_codeswitch_hineng_pos_lince
date: 2022-05-09
tags: [bert, pos, part_of_speech, hi, open_source]
task: Part of Speech Tagging
language: hi
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Part of Speech model model, uploaded to Hugging Face, adapted and imported into Spark NLP. `codeswitch-hineng-pos-lince` is a Hindi model orginally trained by `sagorsarker`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_pos_codeswitch_hineng_pos_lince_hi_3.4.2_3.0_1652091720542.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

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

tokenClassifier = BertForTokenClassification.pretrained("bert_pos_codeswitch_hineng_pos_lince","hi") \
    .setInputCols(["sentence", "token"]) \
    .setOutputCol("pos")

pipeline = Pipeline(stages=[documentAssembler, sentenceDetector, tokenizer, tokenClassifier])

data = spark.createDataFrame([["मुझे स्पार्क एनएलपी बहुत पसंद है"]]).toDF("text")

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

val tokenClassifier = BertForTokenClassification.pretrained("bert_pos_codeswitch_hineng_pos_lince","hi") 
    .setInputCols(Array("sentence", "token")) 
    .setOutputCol("pos")

val pipeline = new Pipeline().setStages(Array(documentAssembler,sentenceDetector, tokenizer, tokenClassifier))

val data = Seq("मुझे स्पार्क एनएलपी बहुत पसंद है").toDF("text")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_pos_codeswitch_hineng_pos_lince|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document, token]|
|Output Labels:|[ner]|
|Language:|hi|
|Size:|665.6 MB|
|Case sensitive:|true|
|Max sentence length:|128|

## References

- https://huggingface.co/sagorsarker/codeswitch-hineng-pos-lince
- https://ritual.uh.edu/lince/home
- https://github.com/sagorbrur/codeswitch