---
layout: model
title: Spanish DistilBertForQuestionAnswering model (from CenIA) SQAC
author: John Snow Labs
name: distilbert_qa_distillbert_base_spanish_uncased_finetuned_qa_sqac
date: 2022-06-08
tags: [es, open_source, distilbert, question_answering]
task: Question Answering
language: es
edition: Spark NLP 4.0.0
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `distillbert-base-spanish-uncased-finetuned-qa-sqac` is a English model originally trained by `CenIA`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/distilbert_qa_distillbert_base_spanish_uncased_finetuned_qa_sqac_es_4.0.0_3.0_1654728123375.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = MultiDocumentAssembler() \
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = DistilBertForQuestionAnswering.pretrained("distilbert_qa_distillbert_base_spanish_uncased_finetuned_qa_sqac","es") \
.setInputCols(["document_question", "document_context"]) \
.setOutputCol("answer")\
.setCaseSensitive(True)

pipeline = Pipeline(stages=[documentAssembler, spanClassifier])

data = spark.createDataFrame([["¿Cuál es mi nombre?", "Mi nombre es Clara y vivo en Berkeley."]]).toDF("question", "context")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new MultiDocumentAssembler() 
.setInputCols(Array("question", "context")) 
.setOutputCols(Array("document_question", "document_context"))

val spanClassifer = DistilBertForQuestionAnswering.pretrained("distilbert_qa_distillbert_base_spanish_uncased_finetuned_qa_sqac","es") 
.setInputCols(Array("document", "token")) 
.setOutputCol("answer")
.setCaseSensitive(true)

val pipeline = new Pipeline().setStages(Array(documentAssembler, spanClassifier))

val data = Seq("¿Cuál es mi nombre?", "Mi nombre es Clara y vivo en Berkeley.").toDF("question", "context")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("es.answer_question.sqac.distil_bert.base_uncased").predict("""¿Cuál es mi nombre?|||"Mi nombre es Clara y vivo en Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|distilbert_qa_distillbert_base_spanish_uncased_finetuned_qa_sqac|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document_question, document_context]|
|Output Labels:|[answer]|
|Language:|es|
|Size:|250.5 MB|
|Case sensitive:|false|
|Max sentence length:|512|

## References

- https://huggingface.co/CenIA/distillbert-base-spanish-uncased-finetuned-qa-sqac