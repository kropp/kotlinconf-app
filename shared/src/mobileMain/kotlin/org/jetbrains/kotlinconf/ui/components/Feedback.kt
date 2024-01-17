package org.jetbrains.kotlinconf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.jetbrains.kotlinconf.Score
import org.jetbrains.kotlinconf.theme.Icons
import org.jetbrains.kotlinconf.theme.grey50
import org.jetbrains.kotlinconf.theme.grey5Black
import org.jetbrains.kotlinconf.theme.greyGrey5
import org.jetbrains.kotlinconf.theme.greyGrey50
import org.jetbrains.kotlinconf.theme.greyGrey80
import org.jetbrains.kotlinconf.theme.greyWhite
import org.jetbrains.kotlinconf.theme.t2
import org.jetbrains.kotlinconf.theme.whiteGrey
import org.jetbrains.kotlinconf.ui.HDivider

@Composable
fun VoteAndFeedback(
    vote: Score?,
    showFeedbackBlock: Boolean,
    onVote: (Score?) -> Unit,
    onSubmitFeedback: (String) -> Unit,
    onCloseFeedback: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.whiteGrey)
    ) {
        Row(Modifier.padding(16.dp)) {
            Text(
                "How was the talk?", style = MaterialTheme.typography.t2.copy(
                    color = MaterialTheme.colors.greyGrey50
                ),
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            VoteBlock(vote, onVote)
        }
        HDivider()

        if (showFeedbackBlock) {
            FeedbackForm(onSend = {
                onSubmitFeedback(it)
            }, onClose = {
                onCloseFeedback()
            })
        }
    }
}

@Composable
fun FeedbackForm(onSend: (String) -> Unit, onClose: () -> Unit) {
    var feedback by remember { mutableStateOf("") }

    Box {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.grey5Black)
        ) {
            OutlinedTextField(
                value = feedback,
                onValueChange = {
                    feedback = it
                },
                placeholder = {
                    Text(
                        "Would you like to share a comment?",
                        style = MaterialTheme.typography.t2.copy(
                            color = MaterialTheme.colors.greyGrey80
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (feedback.isNotEmpty()) onSend(feedback)
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .onKeyEvent {
                        if (it.key != Key.Enter) return@onKeyEvent false
                        if (feedback.isNotEmpty()) onSend(feedback)
                        true
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.grey5Black,
                    focusedBorderColor = MaterialTheme.colors.grey5Black,
                    unfocusedBorderColor = MaterialTheme.colors.grey5Black,
                    disabledTextColor = grey50,
                    disabledLabelColor = MaterialTheme.colors.grey5Black
                ),
            )

            Button(
                onClick = { if (feedback.isNotEmpty()) onSend(feedback) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.whiteGrey,
                    contentColor = MaterialTheme.colors.greyGrey5
                ),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(
                    "SEND MY COMMENT",
                    style = MaterialTheme.typography.t2.copy(
                        color = if (feedback.isEmpty()) grey50 else MaterialTheme.colors.greyGrey5
                    )
                )
            }
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onClose() }, Modifier.padding(4.dp)) {
                Icon(
                    painter = Icons.CLOSE,
                    contentDescription = "Close",
                    tint = MaterialTheme.colors.greyGrey5
                )
            }
        }
    }
}

@Composable
fun VoteBlock(vote: Score?, onVote: (Score?) -> Unit) {
    Row {
        VoteButton(
            vote = Score.GOOD,
            active = vote == Score.GOOD,
            icon = Icons.SMILE_HAPPY,
            activeIcon = Icons.SMILE_HAPPY_ACTIVE,
            onVote = onVote
        )
        VoteButton(
            vote = Score.OK,
            active = vote == Score.OK,
            icon = Icons.SMILE_NEUTRAL,
            activeIcon = Icons.SMILE_NEUTRAL_ACTIVE,
            onVote = onVote
        )
        VoteButton(
            vote = Score.BAD,
            active = vote == Score.BAD,
            icon = Icons.SMILE_SAD,
            activeIcon = Icons.SMILE_SAD_ACTIVE,
            onVote = onVote
        )
    }
}

@Composable
fun VoteButton(
    vote: Score,
    active: Boolean,
    icon: Painter,
    activeIcon: Painter,
    onVote: (Score?) -> Unit
) {
    IconButton(onClick = { onVote(if (active) null else vote) }) {
        Icon(
            painter = if (active) activeIcon else icon,
            contentDescription = vote.toString(),
            tint = MaterialTheme.colors.greyWhite
        )
    }
}