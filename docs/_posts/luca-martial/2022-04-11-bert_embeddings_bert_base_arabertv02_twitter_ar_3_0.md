---
layout: model
title: Arabic Bert Embeddings (Base, Arabert Model, v02, Twitter)
author: John Snow Labs
name: bert_embeddings_bert_base_arabertv02_twitter
date: 2022-04-11
tags: [bert, embeddings, ar, open_source]
task: Embeddings
language: ar
edition: Spark NLP 3.4.2
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Pretrained Bert Embeddings model, uploaded to Hugging Face, adapted and imported into Spark NLP. `bert-base-arabertv02-twitter` is a Arabic model orginally trained by `aubmindlab`.

{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
<button class="button button-orange" disabled>Open in Colab</button>
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/bert_embeddings_bert_base_arabertv02_twitter_ar_3.4.2_3.0_1649677246211.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
documentAssembler = DocumentAssembler() \
.setInputCol("text") \
.setOutputCol("document")

tokenizer = Tokenizer() \
.setInputCols("document") \
.setOutputCol("token")

embeddings = BertEmbeddings.pretrained("bert_embeddings_bert_base_arabertv02_twitter","ar") \
.setInputCols(["document", "token"]) \
.setOutputCol("embeddings")

pipeline = Pipeline(stages=[documentAssembler, tokenizer, embeddings])

data = spark.createDataFrame([["أنا أحب شرارة NLP"]]).toDF("text")

result = pipeline.fit(data).transform(data)
```
```scala
val documentAssembler = new DocumentAssembler() 
.setInputCol("text") 
.setOutputCol("document")

val tokenizer = new Tokenizer() 
.setInputCols(Array("document"))
.setOutputCol("token")

val embeddings = BertEmbeddings.pretrained("bert_embeddings_bert_base_arabertv02_twitter","ar") 
.setInputCols(Array("document", "token")) 
.setOutputCol("embeddings")

val pipeline = new Pipeline().setStages(Array(documentAssembler, tokenizer, embeddings))

val data = Seq("أنا أحب شرارة NLP").toDF("text")

val result = pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("ar.embed.bert_base_arabertv02_twitter").predict("""أنا أحب شرارة NLP""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|bert_embeddings_bert_base_arabertv02_twitter|
|Compatibility:|Spark NLP 3.4.2+|
|License:|Open Source|
|Edition:|Official|
|Input Labels:|[sentence, token]|
|Output Labels:|[bert]|
|Language:|ar|
|Size:|507.9 MB|
|Case sensitive:|true|

## References

- https://huggingface.co/aubmindlab/bert-base-arabertv02-twitter
- https://github.com/google-research/bert
- https://arxiv.org/abs/2003.00104
- https://github.com/WissamAntoun/pydata_khobar_meetup
- https://sites.aub.edu.lb/mindlab/
- https://www.yakshof.com/#/
- https://www.behance.net/rahalhabib
- https://www.linkedin.com/in/wissam-antoun-622142b4/
- https://twitter.com/wissam_antoun
- https://github.com/WissamAntoun
- https://www.linkedin.com/in/fadybaly/
- https://twitter.com/fadybaly
- https://github.com/fadybaly