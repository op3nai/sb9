package com.example.rokuremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.speech.RecognizerIntent
import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rokuremote.ui.theme.RokuRemoteTheme
import com.example.rokuremote.viewmodel.RokuViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: RokuViewModel

    private val speechRecognizer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            spokenText?.let { viewModel.processVoiceCommand(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = RokuViewModel()
        setContent {
            RokuRemoteTheme {
                RokuRemoteApp(viewModel, ::startVoiceRecognition)
            }
        }
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        speechRecognizer.launch(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RokuRemoteApp(viewModel: RokuViewModel, onVoiceCommandRequest: () -> Unit) {
    var showCustomButtonDialog by remember { mutableStateOf(false) }
    var showDeviceDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = { showCustomButtonDialog = true }) {
                    Text("+")
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(onClick = { showDeviceDialog = true }) {
                    Text("Devices")
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(onClick = onVoiceCommandRequest) {
                    Text("Mic")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Roku Remote App")
            // Add more UI components here
        }
    }

    if (showCustomButtonDialog) {
        AlertDialog(
            onDismissRequest = { showCustomButtonDialog = false },
            title = { Text("Custom Button") },
            text = { Text("Custom button dialog content") },
            confirmButton = {
                Button(onClick = { showCustomButtonDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showDeviceDialog) {
        AlertDialog(
            onDismissRequest = { showDeviceDialog = false },
            title = { Text("Devices") },
            text = { Text("Device dialog content") },
            confirmButton = {
                Button(onClick = { showDeviceDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}