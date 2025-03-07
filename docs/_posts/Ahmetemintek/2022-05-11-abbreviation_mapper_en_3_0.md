---
layout: model
title: Mapping Abbreviations and Acronyms of Medical Regulatory Activities with Their Definitions
author: John Snow Labs
name: abbreviation_mapper
date: 2022-05-11
tags: [en, abbreviation, definition, licensed, clinical,  chunk_mapper]
task: Chunk Mapping
language: en
edition: Spark NLP for Healthcare 3.5.1
spark_version: 3.0
supported: true
article_header:
type: cover
use_language_switcher: "Python-Scala-Java"
---


## Description


This pretrained model maps abbreviations and acronyms of medical regulatory activities with their `definition`.


## Predicted Entities


`definition`


{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
[Open in Colab](https://colab.research.google.com/github/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/Certification_Trainings/Healthcare/26.Chunk_Mapping.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/abbreviation_mapper_en_3.5.1_3.0_1652307379928.zip){:.button.button-orange.button-orange-trans.arr.button-icon}


## How to use


<div class="tabs-box" markdown="1">
{% include programmingLanguageSelectScalaPythonNLU.html %}

```python
document_assembler = DocumentAssembler()\
.setInputCol("text")\
.setOutputCol("document")

sentence_detector = SentenceDetector()\
.setInputCols(["document"])\
.setOutputCol("sentence")

tokenizer = Tokenizer()\
.setInputCols("sentence")\
.setOutputCol("token")

word_embeddings = WordEmbeddingsModel.pretrained("embeddings_clinical", "en", "clinical/models")\
.setInputCols(["sentence", "token"])\
.setOutputCol("embeddings")

#NER model to detect abbreviations in the text
abbr_ner = MedicalNerModel.pretrained("ner_abbreviation_clinical", "en", "clinical/models") \
.setInputCols(["sentence", "token", "embeddings"]) \
.setOutputCol("abbr_ner")

abbr_converter = NerConverter() \
.setInputCols(["sentence", "token", "abbr_ner"]) \
.setOutputCol("abbr_ner_chunk")\

chunkerMapper = ChunkMapperModel.pretrained("abbreviation_mapper", "en", "clinical/models")\
.setInputCols(["abbr_ner_chunk"])\
.setOutputCol("mappings")\
.setRel("definition") 

pipeline = Pipeline().setStages([document_assembler,
sentence_detector,
tokenizer, 
word_embeddings,
abbr_ner, 
abbr_converter, 
chunkerMapper])


text = ["""Gravid with estimated fetal weight of 6-6/12 pounds.
LABORATORY DATA: Laboratory tests include a CBC which is normal. 
HIV: Negative. One-Hour Glucose: 117. Group B strep has not been done as yet."""]


data = spark.createDataFrame([text]).toDF("text")
result = pipeline.fit(data).transform(data)
```
```scala
val document_assembler = new DocumentAssembler()
.setInputCol("text")
.setOutputCol("document")

val sentence_detector = new SentenceDetector()
.setInputCols(Array("document"))
.setOutputCol("sentence")

val tokenizer = new Tokenizer()
.setInputCols("sentence")
.setOutputCol("token")

val word_embeddings = WordEmbeddingsModel.pretrained("embeddings_clinical", "en", "clinical/models")
.setInputCols(Array("sentence", "token"))
.setOutputCol("embeddings")

val abbr_ner = MedicalNerModel.pretrained("ner_abbreviation_clinical", "en", "clinical/models") 
.setInputCols(Array("sentence", "token", "embeddings")) 
.setOutputCol("abbr_ner")

val abbr_converter = NerConverter() 
.setInputCols(Array("sentence", "token", "abbr_ner")) 
.setOutputCol("abbr_ner_chunk")

val chunkerMapper = ChunkMapperModel.pretrained("abbreviation_mapper", "en", "clinical/models")
.setInputCols("abbr_ner_chunk")
.setOutputCol("mappings")
.setRel("definition") 


val pipeline = new Pipeline().setStages(Array(
				 document_assembler,
sentence_detector,
tokenizer, 
word_embeddings,
abbr_ner, 
abbr_converter, 
chunkerMapper))


val test_sentence = """Gravid with estimated fetal weight of 6-6/12 pounds.
LABORATORY DATA: Laboratory tests include a CBC which is normal. 
HIV: Negative. One-Hour Glucose: 117. Group B strep has not been done as yet.""" 


val data = Seq(test_sentence).toDS.toDF("text")

val res= pipeline.fit(data).transform(data)
```


{:.nlu-block}
```python
import nlu
nlu.load("en.map_entity.abbreviation_to_definition").predict("""Gravid with estimated fetal weight of 6-6/12 pounds.
LABORATORY DATA: Laboratory tests include a CBC which is normal. 
HIV: Negative. One-Hour Glucose: 117. Group B strep has not been done as yet.""")
```

</div>


## Results


```bash
+----------+------------------------------+
|ner_chunk |mapping_result                |
+----------+------------------------------+
|CBC       |complete blood count          |
|HIV       |human immunodeficiency virus  |
+----------+------------------------------+
```


{:.model-param}
## Model Information


{:.table-model}
|---|---|
|Model Name:|abbreviation_mapper|
|Compatibility:|Spark NLP for Healthcare 3.5.1+|
|License:|Licensed|
|Edition:|Official|
|Input Labels:|[abbr_ner_chunk]|
|Output Labels:|[mappings]|
|Language:|en|
|Size:|214.8 KB|


## References


https://www.johnsnowlabs.com/marketplace/list-of-abbreviations-and-acronyms-for-medical-regulatory-activities/
<!--stackedit_data:
eyJoaXN0b3J5IjpbMjQ1NDQzNTkzLDExNjYwOTI0NThdfQ==
-->
