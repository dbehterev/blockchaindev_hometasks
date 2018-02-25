package mining

import scorex.crypto.hash.CryptographicHash32

import scala.math.BigInt
import scala.util.control.Breaks


class PoWMiner[HF <: CryptographicHash32](hashFunction: HF) {

  private val MaxTarget: BigInt = BigInt(1, Array.fill(32)((-1).toByte))

  def doWork(data: Array[Byte], difficulty: BigInt): ProvedData = {

    var i =  -2147483648;
    val loop = new Breaks;
    var prooved: ProvedData = new ProvedData(data, i);

    while(!validateWork(prooved, difficulty)){
      i += 1;
      prooved =  new ProvedData(data, i);
    }

    /*for( i <- -2147483648 to 2147483647){
      prooved =  new ProvedData(data, i);
      if (validateWork(prooved, difficulty)){
        proovedFound = prooved
        loop.break
      }
    }*/

    return prooved
  }


  def validateWork(data: ProvedData, difficulty: BigInt): Boolean = realDifficulty(data) >= difficulty

  private def realDifficulty(noncedData: ProvedData): BigInt =
    MaxTarget / BigInt(1, hashFunction.hash(noncedData.bytes))

}
