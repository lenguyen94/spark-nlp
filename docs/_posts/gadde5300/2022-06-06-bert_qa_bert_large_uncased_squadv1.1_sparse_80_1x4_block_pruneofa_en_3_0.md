---
layout: model
title: English BertForQuestionAnswering model (from Intel)
author: John Snow Labs
name: bert_qa_bert_large_uncased_squadv1.1_sparse_80_1x4_block_pruneofa
date: 2022-06-06
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

Pretrained Question Answering model, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. `bert-large-uncased-squadv1.1-sparse-80-1x4-block-pruneofa` is a English model orginally trained by `Intel`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_bert_large_uncased_squadv1.1_sparse_80_1x4_block_pruneofa_en_4.0.0_3.0_1654536766214.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
document_assembler = MultiDocumentAssembler() \ 
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_bert_large_uncased_squadv1.1_sparse_80_1x4_block_pruneofa","en") \
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
.pretrained("bert_qa_bert_large_uncased_squadv1.1_sparse_80_1x4_block_pruneofa","en")
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
nlu.load("en.answer_question.squad.bert.large_uncased_sparse_80_1x4_block_pruneofa.by_Intel").predict("""What's my name?|||"My name is Clara and I live in Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_bert_large_uncased_squadv1.1_sparse_80_1x4_block_pruneofa|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|en|
|Size:|437.9 MB|
|Case sensitive:|false|
|Max sentence length:|512|

## References

- https://huggingface.co/Intel/bert-large-uncased-squadv1.1-sparse-80-1x4-block-pruneofa
- https://arxiv.org/abs/2111.05754
- https://github.com/IntelLabs/Model-Compression-Research-Package/tree/main/research/prune-once-for-all