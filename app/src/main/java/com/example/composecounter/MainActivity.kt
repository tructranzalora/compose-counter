package com.example.composecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecounter.ui.theme.ComposeCounterTheme

class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Counter()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Counter() {
    var count by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CounterButton(text = "+") {
            count++
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                // Compare the incoming number with the previous number.
                if (targetState > initialState) {
                    // If the target number is larger, it slides up and fades in
                    // while the initial (smaller) number slides up and fades out.
                    slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with
                            slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
                } else {
                    // If the target number is smaller, it slides down and fades in
                    // while the initial number slides down and fades out.
                    slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with
                            slideOutVertically(targetOffsetY = { height -> height })  + fadeOut()
                }.using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            Text(
                text = targetCount.toString(),
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
                fontSize = 50.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CounterButton(text = "-") {
            count--
        }
    }
}

@Composable
fun CounterButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewCounter() {
    ComposeCounterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Counter()
        }
    }
}