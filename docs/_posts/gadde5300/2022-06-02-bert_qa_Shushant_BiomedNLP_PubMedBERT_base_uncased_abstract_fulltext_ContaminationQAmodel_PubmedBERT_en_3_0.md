---
layout: model
title: English Medical BertForQuestionAnswering model (Uncased, Base, PubMed)
author: John Snow Labs
name: bert_qa_Shushant_BiomedNLP_PubMedBERT_base_uncased_abstract_fulltext_ContaminationQAmodel_PubmedBERT
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

Pretrained Medical Question Answering model, trained on PubMed, adapted from Hugging Face and curated to provide scalability and production-readiness using Spark NLP. This is an English model originally trained by `Shushant`.

This model is a fine-tuned version of `microsoft/BiomedNLP-PubMedBERT-base-uncased-abstract-fulltext`

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_qa_Shushant_BiomedNLP_PubMedBERT_base_uncased_abstract_fulltext_ContaminationQAmodel_PubmedBERT_en_4.0.0_3.0_1654176466849.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
document_assembler = MultiDocumentAssembler() \ 
.setInputCols(["question", "context"]) \
.setOutputCols(["document_question", "document_context"])

spanClassifier = BertForQuestionAnswering.pretrained("bert_qa_Shushant_BiomedNLP_PubMedBERT_base_uncased_abstract_fulltext_ContaminationQAmodel_PubmedBERT","en") \
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
.pretrained("bert_qa_Shushant_BiomedNLP_PubMedBERT_base_uncased_abstract_fulltext_ContaminationQAmodel_PubmedBERT","en")
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
nlu.load("en.answer_question.pubmed.bert.base_uncased.by_Shushant").predict("""What's my name?|||"My name is Clara and I live in Berkeley.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_qa_Shushant_BiomedNLP_PubMedBERT_base_uncased_abstract_fulltext_ContaminationQAmodel_PubmedBERT|
|Compatibility:|Spark NLP 4.0.0+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[embeddings]|
|Language:|en|
|Size:|408.7 MB|
|Case sensitive:|false|
|Max sentence length:|512|

## References

- https://huggingface.co/Shushant/BiomedNLP-PubMedBERT-base-uncased-abstract-fulltext-ContaminationQAmodel_PubmedBERT
