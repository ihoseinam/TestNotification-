package ir.hoeinahmadi.testnotificationapp

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val context = LocalContext.current
            // تعیین اولیه `hasNotificationPermission`
            val initialPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }

            var hasNotificationPermission by remember { mutableStateOf(initialPermissionState) }

            Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            hasNotificationPermission = isGranted
                        }
                    )
                    Button(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }) {
                        Text(text = "Request permission")
                    }
                    Button(onClick = {
                        if(hasNotificationPermission) {
                            showNotification()
                        }
                    }) {
                        Text(text = "Show notification")
                    }
                }
            }
        }


    private fun showNotification() {
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hello world")
            .setContentText("This is a description\ngyiudfsgyiudfgdsiufgsiufogfhsvuigjhbosfg")
            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}