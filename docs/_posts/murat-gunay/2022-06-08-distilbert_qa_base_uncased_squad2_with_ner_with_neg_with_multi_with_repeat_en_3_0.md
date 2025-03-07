---
layout: model
title: English DistilBertForQuestionAnswering model (from andi611) Squad2 with Neg, Multi, Repeat
author: John Snow Labs
name: distilbert_qa_base_uncased_squad2_with_ner_with_neg_with_multi_with_repeat
date: 2022-06-08
tags: [en, open_source, distilbert, question_answering]
task: Question Answering
language: en
edition: Spark NLP 4.0.0
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `distilbert-base-uncased-squad2-with-ner-with-neg-with-multi-with-repeat` is a English model originally trained by `andi611`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/distilbert_qa_base_uncased_squad2_with_ner_with_neg_with_multi_with_repeat_en_4.0.0_3.0_1654727440196.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = MultiDocumentAssembler() \
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = DistilBertForQuestionAnswering.pretrained("distilbert_qa_base_uncased_squad2_with_ner_with_neg_with_multi_with_repeat","en") \
.setInputCols(["document_question", "document_context"]) \
.setOutputCol("answer")\
.setCaseSensitive(True)

pipeline = Pipeline(stages=[documentAssembler, spanClassifier])

data = spark.createDataFrame([["What is my name?", "My name is Clara and I live in Berkeley."]]).toDF("question", "context")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new MultiDocumentAssembler() 
.setInputCols(Array("question", "context")) 
.setOutputCols(Array("document_question", "document_context"))

val spanClassifer = DistilBertForQuestionAnswering.pretrained("distilbert_qa_base_uncased_squad2_with_ner_with_neg_with_multi_with_repeat","en") 
.setInputCols(Array("document", "token")) 
.setOutputCol("answer")
.setCaseSensitive(true)

val pipeline = new Pipeline().setStages(Array(documentAssembler, spanClassifier))

val data = Seq("What is my name?", "My name is Clara and I live in Berkeley.").toDF("question", "context")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.answer_question.squadv2_conll.distil_bert.base_uncased_with_neg_with_multi_with_repeat.by_andi611").predict("""What is my name?|||"My name is Clara and I live in Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|distilbert_qa_base_uncased_squad2_with_ner_with_neg_with_multi_with_repeat|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document_question, document_context]|
|Output Labels:|[answer]|
|Language:|en|
|Size:|247.5 MB|
|Case sensitive:|false|
|Max sentence length:|512|

## References

- https://huggingface.co/andi611/distilbert-base-uncased-squad2-with-ner-with-neg-with-multi-with-repeat