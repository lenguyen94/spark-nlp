---
layout: model
title: Typo Detector Pipeline for English
author: John Snow Labs
name: distilbert_token_classifier_typo_detector_pipeline
date: 2022-03-08
tags: [ner, bert, bert_for_token, typo, en, open_source]
task: Named Entity Recognition
language: en
edition: Spark NLP 3.4.1
spark_version: 3.0
supported: true
article_header:
  type: cover
use_language_switcher: "Python-Scala-Java"
---

## Description

This pretrained pipeline is built on the top of [distilbert_token_classifier_typo_detector](https://nlp.johnsnowlabs.com/2022/01/19/distilbert_token_classifier_typo_detector_en.html).

{:.btn-box}
[Live Demo](https://demo.johnsnowlabs.com/public/TYPO_DETECTOR_EN/){:.button.button-orange}
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/streamlit_notebooks/DistilBertForTokenClassification.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/public/models/distilbert_token_classifier_typo_detector_pipeline_en_3.4.1_3.0_1646736450779.zip){:.button.button-orange.button-orange-trans.arr.button-icon}

## How to use



<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}
```python
typo_pipeline = PretrainedPipeline("distilbert_token_classifier_typo_detector_pipeline", lang = "en")

typo_pipeline.annotate("He had also stgruggled with addiction during his tine in Congress.")
```
```scala
val typo_pipeline = new PretrainedPipeline("distilbert_token_classifier_typo_detector_pipeline", lang = "en")

typo_pipeline.annotate("He had also stgruggled with addiction during his tine in Congress.")
```
</div>

## Results

```bash
+----------+---------+
|chunk     |ner_label|
+----------+---------+
|stgruggled|PO       |
|tine      |PO       |
+----------+---------+
```

{:.model-param}
## Model Information

{:.table-model}
|---|---|
|Model Name:|distilbert_token_classifier_typo_detector_pipeline|
|Type:|pipeline|
|Compatibility:|Spark NLP 3.4.1+|
|License:|Open Source|
|Edition:|Official|
|Language:|en|
|Size:|244.1 MB|

## Included Models

- DocumentAssembler
- TokenizerModel
- DistilBertForTokenClassification
- NerConverter
- Finisher