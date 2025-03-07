---
layout: model
title: Detect Assertion Status (assertion_wip)
author: John Snow Labs
name: jsl_assertion_wip
date: 2021-01-18
task: Assertion Status
language: en
edition: Spark NLP for Healthcare 2.7.0
spark_version: 2.4
tags: [clinical, licensed, assertion, en, ner]
supported: true
article_header:
    type: cover
use_language_switcher: "Python-Scala-Java"
---
 
## Description


The deep neural network architecture for assertion status detection in Spark NLP is based on a BiLSTM framework, and is a modified version of the architecture proposed by Fancellu et.al. (Fancellu, Lopez, and Webber 2016). Its goal is to classify the assertions made on given medical concepts as being present, absent, or possible in the patient, conditionally present in the patient under certain circumstances, hypothetically present in the patient at some future point, and mentioned in the patient report but associated with someoneelse (Uzuner et al. 2011).


{:.h2_title}
## Predicted Entities
`Present`, `Absent`, `Possible`, `Planned`, `Someoneelse`, `Past`, `Family`, `None`, `Hypotetical`.


{:.btn-box}
<button class="button button-orange" disabled>Live Demo</button>
[Open in Colab](https://github.com/JohnSnowLabs/spark-nlp-workshop/blob/master/tutorials/Certification_Trainings/Healthcare/2.Clinical_Assertion_Model.ipynb){:.button.button-orange.button-orange-trans.co.button-icon}{:target="_blank"}
[Download](https://s3.amazonaws.com/auxdata.johnsnowlabs.com/clinical/models/jsl_assertion_wip_en_2.6.1_2.4_1606860510166.zip){:.button.button-orange.button-orange-trans.arr.button-icon}


{:.h2_title}
## How to use


Use as part of an nlp pipeline with the following stages: DocumentAssembler, SentenceDetector, Tokenizer, WordEmbeddingsModel, NerDLModel, NerConverter, AssertionDLModel.


<div class="tabs-box" markdown="1">


{% include programmingLanguageSelectScalaPython.html %}

```python
...
word_embeddings = WordEmbeddingsModel.pretrained("embeddings_clinical", "en", "clinical/models")\
  .setInputCols(["sentence", "token"])\
  .setOutputCol("embeddings")

clinical_ner = NerDLModel.pretrained("ner_clinical", "en", "clinical/models") \
  .setInputCols(["sentence", "token", "embeddings"]) \
  .setOutputCol("ner")

ner_converter = NerConverter() \
  .setInputCols(["sentence", "token", "ner"]) \
  .setOutputCol("ner_chunk")

clinical_assertion = AssertionDLModel.pretrained("jsl_assertion_wip", "en", "clinical/models") \
    .setInputCols(["sentence", "ner_chunk", "embeddings"]) \
    .setOutputCol("assertion")
    
nlpPipeline = Pipeline(stages=[documentAssembler, sentenceDetector, tokenizer, word_embeddings, clinical_ner, ner_converter, clinical_assertion])

model = nlpPipeline.fit(spark.createDataFrame([[""]]).toDF("text"))

result = model.transform(spark.createDataFrame([["The patient is a 21-day-old Caucasian male here for 2 days of congestion - mom has been suctioning yellow discharge from the patient's nares, plus she has noticed some mild problems with his breathing while feeding (but negative for any perioral cyanosis or retractions). One day ago, mom also noticed a tactile temperature and gave the patient Tylenol. Baby also has had some decreased p.o. intake. His normal breast-feeding is down from 20 minutes q.2h. to 5 to 10 minutes secondary to his respiratory congestion. He sleeps well, but has been more tired and has been fussy over the past 2 days. The parents noticed no improvement with albuterol treatments given in the ER. His urine output has also decreased; normally he has 8 to 10 wet and 5 dirty diapers per 24 hours, now he has down to 4 wet diapers per 24 hours. Mom denies any diarrhea. His bowel movements are yellow colored and soft in nature."]], ["text"])


```


```scala
...
val word_embeddings = WordEmbeddingsModel.pretrained("embeddings_clinical", "en", "clinical/models")
  .setInputCols(Array("sentence", "token"))
  .setOutputCol("embeddings")

val clinical_ner = NerDLModel.pretrained("ner_clinical", "en", "clinical/models")
  .setInputCols(Array("sentence", "token", "embeddings")) 
  .setOutputCol("ner")

val nerConverter = NerConverter()
  .setInputCols(Array("sentence", "token", "ner"))
  .setOutputCol("ner_chunk")

val clinical_assertion = AssertionDLModel.pretrained("jsl_assertion_wip", "en", "clinical/models")
    .setInputCols(Array("sentence", "ner_chunk", "embeddings"))
    .setOutputCol("assertion")

val pipeline = new Pipeline().setStages(Array(documentAssembler, sentenceDetector, tokenizer, word_embeddings, clinical_ner, ner_converter, clinical_assertion))

val data = Seq("The patient is a 21-day-old Caucasian male here for 2 days of congestion - mom has been suctioning yellow discharge from the patient's nares, plus she has noticed some mild problems with his breathing while feeding (but negative for any perioral cyanosis or retractions). One day ago, mom also noticed a tactile temperature and gave the patient Tylenol. Baby also has had some decreased p.o. intake. His normal breast-feeding is down from 20 minutes q.2h. to 5 to 10 minutes secondary to his respiratory congestion. He sleeps well, but has been more tired and has been fussy over the past 2 days. The parents noticed no improvement with albuterol treatments given in the ER. His urine output has also decreased; normally he has 8 to 10 wet and 5 dirty diapers per 24 hours, now he has down to 4 wet diapers per 24 hours. Mom denies any diarrhea. His bowel movements are yellow colored and soft in nature.").toDF("text")

val result = pipeline.fit(data).transform(data)
```


</div>


{:.h2_title}
## Results
The output is a dataframe with a sentence per row and an ``"assertion"`` column containing all of the assertion labels in the sentence. The assertion column also contains assertion character indices, and other metadata. To get only the entity chunks and assertion labels, without the metadata, select ``"ner_chunk.result"`` and ``"assertion.result"`` from your output dataframe.


```bash
+-----------------------------------------+-----+---+----------------------------+-------+---------+
|chunk                                    |begin|end|ner_label                   |sent_id|assertion|
+-----------------------------------------+-----+---+----------------------------+-------+---------+
|21-day-old                               |17   |26 |Age                         |0      |Family   |
|Caucasian                                |28   |36 |Race_Ethnicity              |0      |Family   |
|male                                     |38   |41 |Gender                      |0      |Family   |
|for 2 days                               |48   |57 |Duration                    |0      |Family   |
|congestion                               |62   |71 |Symptom                     |0      |Present  |
|mom                                      |75   |77 |Gender                      |0      |Family   |
|yellow                                   |99   |104|Modifier                    |0      |Family   |
|discharge                                |106  |114|Symptom                     |0      |Family   |
|nares                                    |135  |139|External_body_part_or_region|0      |Family   |
|she                                      |147  |149|Gender                      |0      |Family   |
|mild                                     |168  |171|Modifier                    |0      |Family   |
|problems with his breathing while feeding|173  |213|Symptom                     |0      |Present  |
|perioral cyanosis                        |237  |253|Symptom                     |0      |Absent   |
|retractions                              |258  |268|Symptom                     |0      |Absent   |
|One day ago                              |272  |282|RelativeDate                |1      |Family   |
|mom                                      |285  |287|Gender                      |1      |Family   |
|Tylenol                                  |345  |351|Drug_BrandName              |1      |Family   |
|Baby                                     |354  |357|Age                         |2      |Family   |
|decreased p.o. intake                    |377  |397|Symptom                     |2      |Family   |
|His                                      |400  |402|Gender                      |3      |Family   |
+-----------------------------------------+-----+---+----------------------------+-------+---------+
```


{:.model-param}
## Model Information


{:.table-model}
|---|---|
|Model Name:|jsl_assertion_wip|
|Type:|ner|
|Compatibility:|Spark NLP 2.7.0+|
|Edition:|Official|
|License:|Licensed|
|Input Labels:|[sentence, ner_chunk, embeddings]|
|Output Labels:|[assertion]|
|Language:|[en]|
|Case sensitive:|false|


{:.h2_title}
## Data Source
Trained on 2010 i2b2/VA challenge on concepts, assertions, and relations in clinical text with 'embeddings_clinical'.
https://portal.dbmi.hms.harvard.edu/projects/n2c2-nlp/


{:.h2_title}
## Benchmarking
```bash
label           prec   rec    f1   
Absent          0.970  0.943  0.956
Someoneelse     0.868  0.775  0.819
Planned         0.721  0.754  0.737
Possible        0.852  0.884  0.868
Past            0.811  0.823  0.817
Present         0.833  0.866  0.849
Family          0.872  0.921  0.896
None            0.609  0.359  0.452
Hypothetical    0.722  0.810  0.763
Macro-average   0.888  0.872  0.880
Micro-average   0.908  0.908  0.908
```
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTg3ODI2NTM2NF19
-->