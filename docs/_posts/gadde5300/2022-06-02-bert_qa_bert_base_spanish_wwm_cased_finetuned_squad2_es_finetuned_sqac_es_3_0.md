---
layout: model
title: Spanish BertForQuestionAnswering model (from MMG)
author: John Snow Labs
name: bert_qa_bert_base_spanish_wwm_cased_finetuned_squad2_es_finetuned_sqac
date: 2022-06-02
tags: [es, open_source, question_answering, bert]
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

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `bert-base-spanish-wwm-cased-finetuned-squad2-es-finetuned-sqac` is a Spanish model orginally trained by `MMG`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_bert_base_spanish_wwm_cased_finetuned_squad2_es_finetuned_sqac_es_4.0.0_3.0_1654180544867.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
document_assembler = MultiDocumentAssembler() \ 
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_bert_base_spanish_wwm_cased_finetuned_squad2_es_finetuned_sqac","es") \
.setInputCols(["document_question", "document_context"]) \
.setOutputCol("answer") \
.setCaseSensitive(True)

pipeline = Pipeline().setStages([
document_assembler,
spanClassifier
])

example = spark.createDataFrame([["What's my name?", "My name is Clara and I live in Berkeley."]]).toDF("question", "context")

result = pipeline.fit(example).transform(example)
```
```scala
val document = new MultiDocumentAssembler()
.setInputCols("question", "context")
.setOutputCols("document_question", "document_context")

val spanClassifier = BertForQuestionAnswering
.pretrained("bert_qa_bert_base_spanish_wwm_cased_finetuned_squad2_es_finetuned_sqac","es")
.setInputCols(Array("document_question", "document_context"))
.setOutputCol("answer")
.setCaseSensitive(true)
.setMaxSentenceLength(512)

val pipeline = new Pipeline().setStages(Array(document, spanClassifier))

val example = Seq(
("Where was John Lenon born?", "John Lenon was born in London and lived in Paris. My name is Sarah and I live in London."),
("What's my name?", "My name is Clara and I live in Berkeley."))
.toDF("question", "context")

val result = pipeline.fit(example).transform(example)
```


{:.nlu-block}
```python
import nlu
nlu.load("es.answer_question.squadv2_sqac.bert.base_cased_v2.by_MMG").predict("""What's my name?|||"My name is Clara and I live in Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_bert_base_spanish_wwm_cased_finetuned_squad2_es_finetuned_sqac|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|es|
|Size:|410.0 MB|
|Case sensitive:|true|
|Max sentence length:|512|

## References

- https://huggingface.co/MMG/bert-base-spanish-wwm-cased-finetuned-squad2-es-finetuned-sqac