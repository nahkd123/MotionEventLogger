package io.github.nahkd123.motioneventlogger

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import io.github.nahkd123.motioneventlogger.ui.theme.MotionEventLoggerTheme
import kotlin.math.PI

class MainActivity : ComponentActivity() {
    private data class AxisInfo(val type: Int, val name: String, val format: (Float) -> String = { "%.4f".format(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val axes = listOf(
            AxisInfo(MotionEvent.AXIS_X, "X"),
            AxisInfo(MotionEvent.AXIS_Y, "Y"),
            AxisInfo(MotionEvent.AXIS_Z, "Z"),
            AxisInfo(MotionEvent.AXIS_DISTANCE, "Distance"),
            AxisInfo(MotionEvent.AXIS_PRESSURE, "Pressure", { "%.2f".format(it * 100f) + "%" }),
            AxisInfo(MotionEvent.AXIS_ORIENTATION, "Orientation", { "%.2f".format(it * 180f / PI.toFloat()) + "\u00B0" }),
            AxisInfo(MotionEvent.AXIS_TILT, "Tilt", { "%.2f".format(it * 180f / PI.toFloat()) + "\u00B0" }),
            AxisInfo(MotionEvent.AXIS_RX, "Rotation X"),
            AxisInfo(MotionEvent.AXIS_RY, "Rotation Y"),
            AxisInfo(MotionEvent.AXIS_RZ, "Rotation Z"),
            AxisInfo(MotionEvent.AXIS_GENERIC_1, "Generic 1"),
            AxisInfo(MotionEvent.AXIS_GENERIC_2, "Generic 2"),
            AxisInfo(MotionEvent.AXIS_GENERIC_3, "Generic 3"),
            AxisInfo(MotionEvent.AXIS_GENERIC_4, "Generic 4"),
            AxisInfo(MotionEvent.AXIS_GENERIC_5, "Generic 5"),
            AxisInfo(MotionEvent.AXIS_GENERIC_6, "Generic 6"),
            AxisInfo(MotionEvent.AXIS_GENERIC_7, "Generic 7"),
            AxisInfo(MotionEvent.AXIS_GENERIC_8, "Generic 8"),
            AxisInfo(MotionEvent.AXIS_GENERIC_9, "Generic 9"),
            AxisInfo(MotionEvent.AXIS_GENERIC_10, "Generic 10"),
            AxisInfo(MotionEvent.AXIS_GENERIC_11, "Generic 11"),
            AxisInfo(MotionEvent.AXIS_GENERIC_12, "Generic 12"),
            AxisInfo(MotionEvent.AXIS_GENERIC_13, "Generic 13"),
            AxisInfo(MotionEvent.AXIS_GENERIC_14, "Generic 14"),
            AxisInfo(MotionEvent.AXIS_GENERIC_15, "Generic 15"),
            AxisInfo(MotionEvent.AXIS_GENERIC_16, "Generic 16"),
        )

        setContent {
            var values by remember { mutableStateOf<List<Float>?>(null) }

            MotionEventLoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize()
                            .motionEventSpy { event ->
                                values = axes.map { event.getAxisValue(it.type) }
                            }) {
                        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyMedium) {
                            val values = values

                            if (values == null) {
                                Text("Touch the screen with finger or pen")
                            } else {
                                for (i in values.indices) {
                                    val axis = axes[i]
                                    val value = values[i]
                                    Text("${axis.name}: ${axis.format(value)}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}