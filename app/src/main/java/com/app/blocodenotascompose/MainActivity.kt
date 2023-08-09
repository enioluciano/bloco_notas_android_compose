package com.app.blocodenotascompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.blocodenotascompose.datastore.StoreAnotacao
import com.app.blocodenotascompose.ui.theme.BLACK
import com.app.blocodenotascompose.ui.theme.BlocoDeNotasComposeTheme
import com.app.blocodenotascompose.ui.theme.GOLD
import com.app.blocodenotascompose.ui.theme.WHITE
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasComposeTheme {
                BlocoDeNotasComponentes()
            }
        }
    }
}

@Composable
fun BlocoDeNotasComponentes() {
    val context = LocalContext.current
    val storeAnotacao = StoreAnotacao(context)
    val scope = rememberCoroutineScope()

    var anotacao by remember {
        mutableStateOf(value = "")
    }

    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(initial = "")

    anotacao = anotacaoSalva.value


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    8.dp
                ),
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao = anotacao)
                        Toast.makeText(
                            context,
                            "Anotação salva com sucesso", Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                    contentDescription = "salvar anotação"
                )
            }
        },
        topBar = {
            TopAppBar(
                backgroundColor = GOLD
            ) {
                Text(
                    text = "Bloco de Notas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TextField(

                value = anotacao,
                onValueChange = {
                    anotacao = it
                }, label = {
                    Text(text = "Digite sua anotação aqui..")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BlocoDeNotasComposeTheme {
        BlocoDeNotasComponentes()
    }
}