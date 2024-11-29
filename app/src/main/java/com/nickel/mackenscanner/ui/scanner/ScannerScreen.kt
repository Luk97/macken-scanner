package com.nickel.mackenscanner.ui.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.mackenscanner.scanning.QrCodeAnalyzer

@Composable
internal fun ScannerScreen(
    modifier: Modifier = Modifier,
    viewModel: ScannerScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    ScannerScreenContent(
        state = state,
        modifier = modifier,
        onScannerStarted = viewModel::onScannerStarted,
        onScannerSucceeded = viewModel::onScannerSucceeded,
        onScannerCanceled = viewModel::onScannerCanceled
    )
}

@Composable
fun ScannerScreenContent(
    state: ScannerScreenState,
    modifier: Modifier = Modifier,
    onScannerStarted: () -> Unit = {},
    onScannerSucceeded: (String) -> Unit = {},
    onScannerCanceled: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (state.scanning) {
            Scanner(
                onScannerSucceeded = onScannerSucceeded,
                onScannerCanceled = onScannerCanceled
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(onScannerStarted) {
                    Text(text = "Start Scanning")
                }
            }
        }
    }
}

@Composable
fun Scanner(
    modifier: Modifier = Modifier,
    onScannerSucceeded: (String) -> Unit = {},
    onScannerCanceled: () -> Unit = {}
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    ScannerContent(
        modifier = modifier,
        onScannerSucceeded = onScannerSucceeded,
        onScannerCanceled = onScannerCanceled
    )
}

@Composable
fun ScannerContent(
    modifier: Modifier = Modifier,
    onScannerSucceeded: (String) -> Unit = {},
    onScannerCanceled: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = {
                val previewView = PreviewView(it)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.surfaceProvider = previewView.surfaceProvider
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(
                        Size(
                            previewView.width,
                            previewView.height
                        )
                    )
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer(onScannerSucceeded)
                )
                try {
                    cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                previewView
            }
        )
        Button(
            onClick = onScannerCanceled,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Cancel Scan")
        }
    }
}