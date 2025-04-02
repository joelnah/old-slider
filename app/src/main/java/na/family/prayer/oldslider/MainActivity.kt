package na.family.prayer.oldslider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import na.family.prayer.library.OldSlider
import na.family.prayer.oldslider.ui.theme.OldSliderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OldSliderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var value1 by remember { mutableStateOf(15f) }
    var value2 by remember { mutableStateOf(300f) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello $name!",
            fontSize = value1.sp,
            fontWeight = FontWeight(value2.toInt()),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().height(100.dp)
        )

        OldSlider(
            value = value1,
            valueRange= 10f..50f,
            onValueChange = {
                value1 = it
            },
        )

        OldSlider(
            value = value2,
            valueRange= 100f..900f,
            steps = 9,
            onValueChange = {
                value2 = it
            },
        )
    }

}
