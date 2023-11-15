package com.example.rsa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rsa.ui.theme.RSATheme

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
    Row(modifier = Modifier.fillMaxSize()) {
        Text(text = generateRandomPrime().toString())
        Text(text = generateRandomPrime().toString())
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
