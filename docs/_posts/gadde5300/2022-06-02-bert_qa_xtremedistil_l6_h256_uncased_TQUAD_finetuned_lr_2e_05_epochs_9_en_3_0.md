---
layout: model
title: English BertForQuestionAnswering model (from husnu)
author: John Snow Labs
name: bert_qa_xtremedistil_l6_h256_uncased_TQUAD_finetuned_lr_2e_05_epochs_9
date: 2022-06-02
tags: [en, open_source, question_answering, bert]
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

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `xtremedistil-l6-h256-uncased-TQUAD-finetuned_lr-2e-05_epochs-9` is a English model orginally trained by `husnu`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_xtremedistil_l6_h256_uncased_TQUAD_finetuned_lr_2e_05_epochs_9_en_4.0.0_3.0_1654192611826.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
document_assembler = MultiDocumentAssembler() \ 
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_xtremedistil_l6_h256_uncased_TQUAD_finetuned_lr_2e_05_epochs_9","en") \
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
.pretrained("bert_qa_xtremedistil_l6_h256_uncased_TQUAD_finetuned_lr_2e_05_epochs_9","en")
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
nlu.load("en.answer_question.tquad.bert.xtremedistiled_uncased").predict("""What's my name?|||"My name is Clara and I live in Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_xtremedistil_l6_h256_uncased_TQUAD_finetuned_lr_2e_05_epochs_9|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|en|
|Size:|47.7 MB|
|Case sensitive:|false|
|Max sentence length:|512|

## References

- https://huggingface.co/husnu/xtremedistil-l6-h256-uncased-TQUAD-finetuned_lr-2e-05_epochs-9