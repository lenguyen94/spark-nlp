---
layout: model
title: English BertForQuestionAnswering Base Cased model (from anas-awadalla)
author: John Snow Labs
name: bert_qa_spanbert_base_cased_few_shot_k_256_finetuned_squad_seed_4
date: 2022-07-07
tags: [en, open_source, bert, question_answering]
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

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `spanbert-base-cased-few-shot-k-256-finetuned-squad-seed-4` is a English model originally trained by `anas-awadalla`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_spanbert_base_cased_few_shot_k_256_finetuned_squad_seed_4_en_4.0.0_3.0_1657192372566.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = MultiDocumentAssembler() \
    .setInputCols(["question", "context"]) \
    .setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_spanbert_base_cased_few_shot_k_256_finetuned_squad_seed_4","en") \
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
 
val spanClassifer = BertForQuestionAnswering.pretrained("bert_qa_spanbert_base_cased_few_shot_k_256_finetuned_squad_seed_4","en") 
    .setInputCols(Array("document", "token")) 
    .setOutputCol("answer")
    .setCaseSensitive(true)

val pipeline = new Pipeline().setStages(Array(documentAssembler, spanClassifier))

val data = Seq("What is my name?", "My name is Clara and I live in Berkeley.").toDF("question", "context")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_spanbert_base_cased_few_shot_k_256_finetuned_squad_seed_4|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document_question, document_context]|
|Output Labels:|[answer]|
|Language:|en|
|Size:|384.2 MB|
|Case sensitive:|true|
|Max sentence length:|512|

## References

- https://huggingface.co/anas-awadalla/spanbert-base-cased-few-shot-k-256-finetuned-squad-seed-4