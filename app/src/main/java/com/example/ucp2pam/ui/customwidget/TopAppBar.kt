import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    title: String,
    modifier: Modifier = Modifier
) = with(MaterialTheme) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val backgroundGradient = Brush.verticalGradient(
        listOf(
            Color(0x00FF0000).copy(alpha = 0.95f),
            Color(0x00FFE5E5).copy(alpha = 0.85f)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundGradient)
            .padding(
                top = statusBarHeight + 35.dp,
                bottom = 15.dp,
                start = 15.dp,
                end = 15.dp
            ),
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -50 })
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (showBackButton) {
                    BackButton(
                        onBack = onBack,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }

                TitleWithShadow(
                    title = title,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onBack,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(Color.White.copy(alpha = 0.1f))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Kembali",
            tint = Color.White
        )
    }
}

@Composable
private fun TitleWithShadow(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.3f),
            modifier = Modifier.offset(x = 1.dp, y = 1.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                letterSpacing = 0.5.sp
            )
        )
        Text(
            text = title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge.copy(
                letterSpacing = 0.5.sp
            )
        )
    }
}