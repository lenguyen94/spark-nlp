---
layout: model
title: Amharic Named Entity Recognition (from mbeukman)
author: John Snow Labs
name: xlmroberta_ner_xlm_roberta_base_finetuned_swahili_finetuned_ner_amharic
date: 2022-05-17
tags: [xlm_roberta, ner, token_classification, am, open_source]
task: Named Entity Recognition
language: am
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Named Entity Recognition model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `xlm-roberta-base-finetuned-swahili-finetuned-ner-amharic` is a Amharic model orginally trained by `mbeukman`.

## Predicted Entities

`PER`, `ORG`, `LOC`, `DATE`

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/xlmroberta_ner_xlm_roberta_base_finetuned_swahili_finetuned_ner_amharic_am_3.4.2_3.0_1652810385568.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

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

tokenClassifier = XlmRoBertaForTokenClassification.pretrained("xlmroberta_ner_xlm_roberta_base_finetuned_swahili_finetuned_ner_amharic","am") \
    .setInputCols(["sentence", "token"]) \
    .setOutputCol("ner")

pipeline = Pipeline(stages=[documentAssembler, sentenceDetector, tokenizer, tokenClassifier])

data = spark.createDataFrame([["ስካርቻ nlp እወዳለሁ"]]).toDF("text")

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

val tokenClassifier = XlmRoBertaForTokenClassification.pretrained("xlmroberta_ner_xlm_roberta_base_finetuned_swahili_finetuned_ner_amharic","am") 
    .setInputCols(Array("sentence", "token")) 
    .setOutputCol("ner")

val pipeline = new Pipeline().setStages(Array(documentAssembler,sentenceDetector, tokenizer, tokenClassifier))

val data = Seq("ስካርቻ nlp እወዳለሁ").toDF("text")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|xlmroberta_ner_xlm_roberta_base_finetuned_swahili_finetuned_ner_amharic|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document, token]|
|Output Labels:|[ner]|
|Language:|am|
|Size:|1.0 GB|
|Case sensitive:|true|
|Max sentence length:|128|

## References

- https://huggingface.co/mbeukman/xlm-roberta-base-finetuned-swahili-finetuned-ner-amharic
- https://arxiv.org/abs/2103.11811
- https://github.com/Michael-Beukman/NERTransfer
- https://www.apache.org/licenses/LICENSE-2.0
- https://github.com/Michael-Beukm
