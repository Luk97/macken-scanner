package com.nickel.mackenscanner.scanning

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import java.lang.Exception
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer{

    private companion object {
        const val SCAN_COOLDOWN = 2000L
    }

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888
    )

    private var lastScannedTime: Long = 0
    private var lastScannedCode: String? = null

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats) {

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastScannedTime < SCAN_COOLDOWN) {
                image.close()
                return
            }

            val bytes = image.planes.first().buffer.toByteArray()

            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false

            )
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )

                        )
                    )
                }.decode(binaryBmp)
                val scannedCode = result.text
                if (lastScannedCode != scannedCode) {
                    lastScannedCode = scannedCode
                    lastScannedTime = currentTime
                    onQrCodeScanned(scannedCode)
                }
            } catch(e: Exception) {
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }
}