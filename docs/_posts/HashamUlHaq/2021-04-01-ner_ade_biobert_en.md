---
layout: model
title: Detect Adverse Drug Events (biobert)
author: John Snow Labs
name: ner_ade_biobert
date: 2021-04-01
tags: [ner, clinical, licensed, en]
task: Named Entity Recognition
language: en
edition: Spark NLP for Healthcare 3.0.0
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

Detect adverse drug events in tweets, reviews, and medical text using pretrained NER model.

## Predicted Entities

`DRUG`, `ADE`

{:.btn-box}
[Live Demo](https://demo.johnsnowlabs.com/healthcare/PP_ADE/){:.button.button-orange}
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/Certification_Trainings/Healthcare/1.Clinical_Named_Entity_Recognition_Model.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/ner_ade_biobert_en_3.0.0_3.0_1617260850526.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python

...
embeddings_clinical = BertEmbeddings.pretrained("biobert_pubmed_base_cased")  .setInputCols(["sentence", "token"])  .setOutputCol("embeddings")
clinical_ner = MedicalNerModel.pretrained("ner_ade_biobert", "en", "clinical/models")   .setInputCols(["sentence", "token", "embeddings"])   .setOutputCol("ner")
...
nlpPipeline = Pipeline(stages=[document_assembler, sentence_detector, tokenizer, embeddings_clinical, clinical_ner, ner_converter])
model = nlpPipeline.fit(spark.createDataFrame([[""]]).toDF("text"))
results = model.transform(spark.createDataFrame([["EXAMPLE_TEXT"]]).toDF("text"))
```
```scala

...
val embeddings_clinical = BertEmbeddings.pretrained("biobert_pubmed_base_cased")
.setInputCols(Array("sentence", "token"))
.setOutputCol("embeddings")
val ner = MedicalNerModel.pretrained("ner_ade_biobert", "en", "clinical/models")
.setInputCols(Array("sentence", "token", "embeddings"))
.setOutputCol("ner")
...
val pipeline = new Pipeline().setStages(Array(document_assembler, sentence_detector, tokenizer, embeddings_clinical, ner, ner_converter))
val result = pipeline.fit(Seq.empty[String]).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.med_ner.ade_biobert").predict("""Put your text here.""")
```

</div>

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|ner_ade_biobert|
|Compatibility:|Spark NLP for Healthcare 3.0.0+|
|License:|Licensed|
|Edition:|Official|
|Input Labels:|[sentence, token, embeddings]|
|Output Labels:|[ner]|
|Language:|en|


## Benchmarking

```bash
precision    recall  f1-score   support

B-ADE       0.48      0.82      0.60      3582
B-DRUG       0.87      0.65      0.75     11763
I-ADE       0.48      0.76      0.59      4309
I-DRUG       0.95      0.28      0.43      7654
O       0.97      0.98      0.97    303457

accuracy                           0.95    330765
macro avg       0.75      0.70      0.67    330765
weighted avg       0.95      0.95      0.94    330765
```