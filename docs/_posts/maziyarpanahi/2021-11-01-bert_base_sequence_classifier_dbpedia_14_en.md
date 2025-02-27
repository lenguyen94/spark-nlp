---
layout: model
title: BERT Sequence Classification Base - DBpedia14 (bert_base_sequence_classifier_dbpedia_14)
author: John Snow Labs
name: bert_base_sequence_classifier_dbpedia_14
date: 2021-11-01
tags: [bert, en, english, dbpedia, open_source, sequence_classification]
task: Text Classification
language: en
edition: Spark NLP 3.3.2
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

BERT Model with sequence classification/regression head on top (a linear layer on top of the pooled output) e.g. for multi-class document classification tasks.

`bert_base_sequence_classifier_dbpedia_14 ` is a fine-tuned BERT model that is ready to be used for Sequence Classification tasks such as sentiment analysis or multi-class text classification and it achieves state-of-the-art performance.

We used TFBertForSequenceClassification to train this model and used BertForSequenceClassification annotator in Spark NLP 🚀 for prediction at scale!

## Predicted Entities

`Album`, `Animal`, `Artist`, `Athlete`, `Building`, `Company`, `EducationalInstitution`, `Film`, `MeanOfTransportation`, `NaturalPlace`, `OfficeHolder`, `Plant`, `Village`, `WrittenWork`

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_base_sequence_classifier_dbpedia_14_en_3.3.2_3.0_1635776368230.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
document_assembler = DocumentAssembler() \
.setInputCol('text') \
.setOutputCol('document')

tokenizer = Tokenizer() \
.setInputCols(['document']) \
.setOutputCol('token')

sequenceClassifier = BertForSequenceClassification \
.pretrained('bert_base_sequence_classifier_dbpedia_14', 'en') \
.setInputCols(['token', 'document']) \
.setOutputCol('class') \
.setCaseSensitive(True) \
.setMaxSentenceLength(512)

pipeline = Pipeline(stages=[
document_assembler,
tokenizer,
sequenceClassifier
])

example = spark.createDataFrame([['Disney Comics was a comic book publishing company operated by The Walt Disney Company which ran from 1990 to 1993.']]).toDF("text")
result = pipeline.fit(example).transform(example)
```
```scala
val document_assembler = DocumentAssembler()
.setInputCol("text")
.setOutputCol("document")

val tokenizer = Tokenizer()
.setInputCols("document")
.setOutputCol("token")

val tokenClassifier = BertForSequenceClassification.pretrained("bert_base_sequence_classifier_dbpedia_14", "en")
.setInputCols("document", "token")
.setOutputCol("class")
.setCaseSensitive(true)
.setMaxSentenceLength(512)

val pipeline = new Pipeline().setStages(Array(document_assembler, tokenizer, sequenceClassifier))

val example = Seq("Disney Comics was a comic book publishing company operated by The Walt Disney Company which ran from 1990 to 1993.").toDS.toDF("text")

val result = pipeline.fit(example).transform(example)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.classify.bert_sequence.dbpedia_14").predict("""Disney Comics was a comic book publishing company operated by The Walt Disney Company which ran from 1990 to 1993.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_base_sequence_classifier_dbpedia_14|
|Compatibility:|Spark NLP 3.3.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[token, document]|
|Output Labels:|[class]|
|Language:|en|
|Case sensitive:|true|
|Max sentense length:|512|

## Data Source

[https://huggingface.co/datasets/dbpedia_14](https://huggingface.co/datasets/dbpedia_14)

## Benchmarking

```bash
precision    recall  f1-score   support

Album       1.00      1.00      1.00      5004
Animal       1.00      1.00      1.00      4998
Artist       0.99      0.99      0.99      5012
Athlete       1.00      1.00      1.00      5002
Building       0.99      0.99      0.99      5007
Company       0.98      0.98      0.98      4999
EducationalInstitution       0.99      0.99      0.99      4998
Film       0.99      1.00      1.00      4978
MeanOfTransportation       1.00      1.00      1.00      5002
NaturalPlace       1.00      1.00      1.00      5005
OfficeHolder       0.99      0.99      0.99      5001
Plant       1.00      1.00      1.00      4994
Village       1.00      1.00      1.00      5003
WrittenWork       0.99      0.99      0.99      4997

accuracy                           0.99     70000
macro avg       0.99      0.99      0.99     70000
weighted avg       0.99      0.99      0.99     70000
```