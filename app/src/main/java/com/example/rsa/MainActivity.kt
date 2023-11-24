package com.example.rsa

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rsa.ui.theme.RSATheme
import java.math.BigInteger
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RSA()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RSA() {
    var p:BigInteger
    var q:BigInteger
    var e:BigInteger
    var n:BigInteger
    var euler:BigInteger
    var d:BigInteger
    var message:String
    var encryptedMessage:String
    var decryptedMessage:String


    Column {
        Row {
            Text("p:")
            p = remember { BigInteger(generateRandomPrime().toString()) }
            Text(p.toString())
        }
        Row {
            Text("q:")
            q = remember { BigInteger(generateRandomPrime().toString()) }
            Text(q.toString())
        }
        Row {
            Text("n:")
            n = remember { returnN(p.toInt(),q.toInt()) }
            Text(n.toString())
        }
        Row {
            Text("euler:")
            euler = remember { eulerFunction(p.toInt(),q.toInt()) }
            Text(euler.toString())
        }
        Row {
            Text("e:")
            e = remember { BigInteger(chooseE(euler.toInt()).toString()) }
            Text(e.toString())
        }
        Row {
            Text("d:")
            d = remember { returnD(e.toInt(),euler.toInt()) }
            Text(d.toString())
        }
        Row {
            Text("Public key:")
            Text("($e,$n)")
        }
        Row {
            Text("Private key:")
            Text("($d,$n)")
        }
        Row {
            Text("Message:")
            //val message = remember { BigInteger("123456789") }
            Text(message.toString())
        }
        Row {
            Text("Encrypted message:")
            //val encryptedMessage = remember { message.modPow(e,n) }
            Text(encryptedMessage.toString())
        }
        Row {
            Text("Decrypted message:")
            //val decryptedMessage = remember { encryptedMessage.modPow(d,n) }
            Text(decryptedMessage.toString())
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Encrypt")
        }
    }
}

fun mod(int: Int, mod: Int): Int {
    var result = int % mod
    if (result < 0) {
        result += mod
    }
    return result
}

fun mmi(a: Int, b: Int): Int = (1 until b).dropWhile { (a * it) % b != 1 }.firstOrNull() ?: 1

fun gcd(a: Int, b: Int): Int{
    var gcd = 1
    generateSequence(1) { it + 1 }
        .takeWhile { it <= a && it <= b }
        .forEach { if (a % it == 0 && b % it == 0) gcd = it }
    return gcd
}

fun returnN(p:Int,q:Int): BigInteger {
    val p = BigInteger(p.toString())
    val q = BigInteger(q.toString())
    val n = p.multiply(q)
    return n
}

fun eulerFunction(p:Int,q: Int): BigInteger {
    val p = BigInteger(p.toString())
    val q = BigInteger(q.toString())
    val eulerFunction = p.subtract(BigInteger("1"))*q.subtract(BigInteger("1"))
    return eulerFunction
}

fun chooseE(euler :Int):Int{
    for (e in 1..euler){
        if (gcd(e, euler)==1){
            return e
        }
    }
    return 0
}

fun returnD(e:Int, euler:Int): BigInteger {
    if (isPrime(e)) {
        val r = euler.toDouble().pow(e - 2) * (e - 1)
        val d = (1 + r * euler / e)
        val result = BigInteger(d.toString())
        return result
    }
    return BigInteger("0")
}

fun generateRandomPrime(): Int {
    val start = 100000000
    val end = 1000000000
    val random = java.util.Random()
    for (i in 0..100) {
        val number = start + random.nextInt(end - start + 1)
        if (isPrime(number)) {
            return number
        }
    }
    return 0
}

fun isPrime(number: Int): Boolean {
    if (number <= 1) {
        return false
    }
    for (i in 2..number / 2) {
        if (number % i == 0) {
            return false
        }
    }
    return true
}
