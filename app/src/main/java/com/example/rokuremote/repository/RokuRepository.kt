package com.example.rokuremote.repository

import com.example.rokuremote.model.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class RokuRepository {
    private val client = OkHttpClient()

    suspend fun discoverDevices(): List<Device> = withContext(Dispatchers.IO) {
        // Implement SSDP discovery here
        // For now, return an empty list
        emptyList()
    }

    suspend fun connectToDevice(device: Device): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = client.newCall(
                Request.Builder()
                    .url("http://${device.ip}:8060/query/device-info")
                    .build()
            ).execute()
            response.isSuccessful
        } catch (e: IOException) {
            false
        }
    }

    suspend fun sendCommand(command: String, device: Device): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = client.newCall(
                Request.Builder()
                    .url("http://${device.ip}:8060/keypress/$command")
                    .post(ByteArray(0).toRequestBody(null, 0))
                    .build()
            ).execute()
            response.isSuccessful
        } catch (e: IOException) {
            false
        }
    }

    suspend fun launchChannel(channelId: String, device: Device): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = client.newCall(
                Request.Builder()
                    .url("http://${device.ip}:8060/launch/$channelId")
                    .post(ByteArray(0).toRequestBody(null, 0))
                    .build()
            ).execute()
            response.isSuccessful
        } catch (e: IOException) {
            false
        }
    }
}