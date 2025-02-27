/*
 * Copyright 2017-2022 John Snow Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.johnsnowlabs.nlp.annotators.btm

import com.johnsnowlabs.storage.{RocksDBConnection, StorageReadWriter}

class TMEdgesReadWriter(
    protected override val connection: RocksDBConnection,
    protected override val caseSensitiveIndex: Boolean)
    extends TMEdgesReader(connection, caseSensitiveIndex)
    with StorageReadWriter[Int] {

  def add(word: (Int, Int), content: Int): Unit = super.add(word.toString(), content)

  override protected def writeBufferSize: Int = 10000

  override def toBytes(content: Int): Array[Byte] = {
    BigInt(content).toByteArray
  }

}
