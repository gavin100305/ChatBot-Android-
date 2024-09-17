package com.example.chatbot.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatPage (viewModel: ChatViewModel){
Column(modifier = Modifier.fillMaxSize()) {
 PageHeader()
    MessageList(messageList = viewModel.messageList, modifier = Modifier.weight(1f))
 MessageInput(
     onMessageSend = {
         viewModel.sendMessage(it)

     }
 )
}
}

@Composable
fun PageHeader(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color(0xff1a1a1a))
        ) {
        Text(
            text = "CHATBOT",
            fontSize = 45.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp),
            color = Color.White
        )
    }
}

@Composable
fun MessageInput(onMessageSend : (String) -> Unit){
    var message by remember{ mutableStateOf("") }
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(value = message,
            onValueChange ={
                message = it
            } ,modifier = Modifier
                .width(350.dp)
                .border(BorderStroke(2.dp, Color.Black))
                .background(color = Color.Gray),
            placeholder = {Text(text = "Enter Message.... ")})
        IconButton(onClick = {
            onMessageSend(message)
            message = ""
        }) {
            Icon(imageVector = Icons.Default.Send,
                contentDescription = "Send",
                modifier = Modifier.size(40.dp))
        }


    }
}

@Composable
fun MessageList(modifier: Modifier= Modifier,messageList : List<MessageModel>){
    val scrollState = rememberLazyListState()
    LazyColumn(modifier = modifier.fillMaxSize(),
        state = scrollState,
        reverseLayout = true){
        items(messageList.reversed()){
            MessageRow(messageList = it)
        }
    }

}

@Composable
fun MessageRow(messageList: MessageModel){
    var isModel = messageList.role == "model"
Row(
    verticalAlignment = Alignment.CenterVertically
) {
    Box(modifier = Modifier.fillMaxWidth() ){
        Box(modifier = Modifier
            .align(
                if (isModel)
                    Alignment.BottomStart
                else
                    Alignment.BottomEnd
            )
            .padding(
                start = if (isModel) 8.dp else 70.dp,
                end = if (isModel) 70.dp else 8.dp
            )
            .padding(5.dp)
            .border(
                BorderStroke(2.dp, Color.Black), // Specify the border stroke
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Color(0xff999999),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
            .wrapContentSize())
            {
            Text(text = messageList.message,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Black,
                )
        }

        }


    }
}

