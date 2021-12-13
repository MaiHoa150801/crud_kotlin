package com.sample.jetfirebase.presentation.post_detail



import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.jetfirebase.ui.theme.Red100


@Composable
fun imagePicker():String{
    var imageUrl by remember{ mutableStateOf<Uri?>(null)}
    var context = LocalContext.current
    var bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    var launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
                                                                                                      uri: Uri? -> imageUrl = uri
    }
    Column{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Red100),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
           Text(
               text = "Pick Image",
               color = Color.White,
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold
           )
        }
        Column(  modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            imageUrl?.let {
                if(Build.VERSION.SDK_INT < 28){
                    bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }else{
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
           bitmap.value?.let { bitmap ->
                    Image(bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Gallery Image",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(
                    text = "Click Image",
                    color = Color.White,
                    fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            }
        }
    }
      return imageUrl.toString();
}


@Composable
fun PostDetailScreen(
    state: PostDetailState,
    addNewPost: (String, String, String, String, String) -> Unit,
    updatePost: (String, String, String, String, String) -> Unit,
) {

    var title by remember(state.post?.title) { mutableStateOf(state.post?.title ?: "") }
    var content by remember(state.post?.content) { mutableStateOf(state.post?.content ?: "") }
    var image by remember(state.post?.content) { mutableStateOf(state.post?.image ?: "") }
    var address by remember(state.post?.content) { mutableStateOf(state.post?.address ?: "") }
    var phone by remember(state.post?.content) { mutableStateOf(state.post?.phone ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(text = "Enter Title")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

                value = content,
                onValueChange = { content = it },
                label = {
                    Text(text = "Enter content")
                }
            )

            image = imagePicker()

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = address,
                onValueChange = { address = it },
                label = {
                    Text(text = "Enter address")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

                singleLine = true,
                value = phone,
                onValueChange = { phone = it },
                label = {
                    Text(text = "Enter phone")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )

        }
    }


        if(state.error.isNotBlank()){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = state.error,
                style = TextStyle(
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            )
        }

        if(state.isLoading){
            CircularProgressIndicator()
        } else {
            if(state.post?.id != null) {
                Button(

                    modifier = Modifier
                        .fillMaxWidth()
                        , onClick = {
                        updatePost(title, content, image, address, phone)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red100
                    )
                ) {
                    Text(
                        text = "Update Post",
                        color = Color.White
                    )
                }
            } else {
                Button(

                    modifier = Modifier

                    .padding(horizontal = 20.dp),
                    onClick = {
                        addNewPost(title, content, image, address, phone)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red100
                    )
                ) {
                    Text(
                        text = "Add New Post",
                        color = Color.White
                    )
                }
            }
        }

    }






















