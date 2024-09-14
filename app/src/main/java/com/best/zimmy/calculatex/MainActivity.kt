package com.best.zimmy.calculatex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.best.zimmy.calculatex.data.CalculatorRepository
import com.best.zimmy.calculatex.ui.theme.CalculateXTheme
import com.best.zimmy.calculatex.ui.view.CalculatorScreen
import com.best.zimmy.calculatex.ui.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = CalculatorRepository()
        val viewModel = CalculatorViewModel(repository)

        setContent {
            CalculatorApp(viewModel)
        }
    }
}

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel) {
    CalculatorScreen(viewModel)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculateXTheme {
        CalculatorScreen(CalculatorViewModel(CalculatorRepository()))
    }
}