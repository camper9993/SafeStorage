package ru.danilov.safestorage.data.cryptography

import android.util.Pair
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.math.BigInteger
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Cryptography {
    private const val ITERATION_MULTIPLIER = 10
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = KEY_LENGTH / 8
    private val RANDOM = SecureRandom()

    fun newCipher(passphrase: String): Pair<Cipher, ByteArray> {
        val header = ByteArrayOutputStream()
        val salt = ByteArray(SALT_LENGTH)
        RANDOM.nextBytes(salt)
        val keySpecification: KeySpec =
            PBEKeySpec(passphrase.toCharArray(), salt, ITERATION_MULTIPLIER * 1000, KEY_LENGTH)
        return try {
            val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = keyFactory.generateSecret(keySpecification).encoded
            val key: SecretKey = SecretKeySpec(keyBytes, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val iv = ByteArray(cipher.blockSize)
            RANDOM.nextBytes(iv)
            val ivParams = IvParameterSpec(iv)
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
            header.write(byteArrayOf(ITERATION_MULTIPLIER.toByte()))
            header.write(byteArrayOf(SALT_LENGTH.toByte()))
            header.write(salt)
            header.write(byteArrayOf(iv.size.toByte()))
            header.write(iv)
            Pair(cipher, header.toByteArray())
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun getCipher(passphrase: String, inputStream: InputStream): Cipher {
        return try {
            val iterationMultipler = inputStream.read()
            val saltLength = inputStream.read()
            val salt = ByteArray(saltLength)
            for (i in salt.indices) {
                salt[i] = inputStream.read().toByte()
            }
            val ivLength = inputStream.read()
            val iv = ByteArray(ivLength)
            for (i in iv.indices) {
                iv[i] = inputStream.read().toByte()
            }
            val iterationCount = iterationMultipler * 1000
            val keySpecification: KeySpec =
                PBEKeySpec(passphrase.toCharArray(), salt, iterationCount, KEY_LENGTH)
            val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = keyFactory.generateSecret(keySpecification).encoded
            val key: SecretKey = SecretKeySpec(keyBytes, "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivParams = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams)
            cipher
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun toHex(values: ByteArray): String {
        return String.format("%0" + values.size * 2 + "X", BigInteger(1, values))
    }

    private fun toBytes(hex: String): ByteArray {
        val data = ByteArray(hex.length / 2)
        var i = 0
        while (i < hex.length) {
            data[i / 2] = (Character.digit(hex[i], 16) * 16 + Character.digit(
                hex[i + 1], 16
            )).toByte()
            i += 2
        }
        return data
    }
}