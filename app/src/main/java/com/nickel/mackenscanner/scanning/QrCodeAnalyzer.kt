package com.nickel.mackenscanner.scanning

import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer{

    private companion object {
        const val SCAN_COOLDOWN = 2000L
        const val TAG = "qr-code-analyzer"
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
                Log.d(TAG, "scanned Code: $scannedCode")
                if (lastScannedCode != scannedCode) {
                    Log.d(TAG, "onSuccess get executed")
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