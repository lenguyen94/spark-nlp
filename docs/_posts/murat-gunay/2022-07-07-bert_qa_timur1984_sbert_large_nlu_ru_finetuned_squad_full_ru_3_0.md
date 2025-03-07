---
layout: model
title: Russian BertForQuestionAnswering Large Cased model (from Timur1984)
author: John Snow Labs
name: bert_qa_timur1984_sbert_large_nlu_ru_finetuned_squad_full
date: 2022-07-07
tags: [ru, open_source, bert, question_answering]
task: Question Answering
language: ru
edition: Spark NLP 4.0.0
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `sbert_large_nlu_ru-finetuned-squad-full` is a Russian model originally trained by `Timur1984`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_timur1984_sbert_large_nlu_ru_finetuned_squad_full_ru_4.0.0_3.0_1657191520660.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = MultiDocumentAssembler() \
    .setInputCols(["question", "context"]) \
    .setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_timur1984_sbert_large_nlu_ru_finetuned_squad_full","ru") \
    .setInputCols(["document_question", "document_context"]) \
    .setOutputCol("answer")\
    .setCaseSensitive(True)
    
pipeline = Pipeline(stages=[documentAssembler, spanClassifier])

data = spark.createDataFrame([["Как меня зовут?", "Меня зовут Клара, и я живу в Беркли."]]).toDF("question", "context")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new MultiDocumentAssembler() 
      .setInputCols(Array("question", "context")) 
      .setOutputCols(Array("document_question", "document_context"))
 
val spanClassifer = BertForQuestionAnswering.pretrained("bert_qa_timur1984_sbert_large_nlu_ru_finetuned_squad_full","ru") 
    .setInputCols(Array("document", "token")) 
    .setOutputCol("answer")
    .setCaseSensitive(true)

val pipeline = new Pipeline().setStages(Array(documentAssembler, spanClassifier))

val data = Seq("Как меня зовут?", "Меня зовут Клара, и я живу в Беркли.").toDF("question", "context")

val result = pipeline.fit(data).transform(data)
```
</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_timur1984_sbert_large_nlu_ru_finetuned_squad_full|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[document_question, document_context]|
|Output Labels:|[answer]|
|Language:|ru|
|Size:|1.6 GB|
|Case sensitive:|true|
|Max sentence length:|512|

## References

- https://huggingface.co/Timur1984/sbert_large_nlu_ru-finetuned-squad-full