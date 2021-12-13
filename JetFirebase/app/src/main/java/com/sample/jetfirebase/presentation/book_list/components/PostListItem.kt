package com.sample.jetfirebase.presentation.post_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.sample.jetfirebase.model.Post

@ExperimentalMaterialApi
@Composable
fun PostListItem(
    post: Post,
    onItemClick: (String) -> Unit
) {
  Card(
    elevation = 0.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable { onItemClick(post.id) },
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = rememberImagePainter(post.image),
        contentDescription = "",
        modifier = Modifier
          .width(300.dp)
          .height(360.dp)
          .padding(8.dp)
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = post.title,
          style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
          )
        )

        Text(
          modifier = Modifier.fillMaxWidth(),
          text = post.content,
          style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.DarkGray
          )
        )

                  Text(
                  modifier = Modifier.fillMaxWidth(),
          text = post.address,
          style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.DarkGray
          )
        )

        Text(
          modifier = Modifier.fillMaxWidth(),
          text = post.phone,
          style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.DarkGray
          )
        )
      }
    }
  }
}