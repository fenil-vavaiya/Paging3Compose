package com.example.paging3compose.ui.screen.home

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.paging3compose.R
import com.example.paging3compose.data.model.PhotoEntity
import com.example.paging3compose.presentation.WallpaperViewModel
import com.example.paging3compose.ui.theme.Frastha
import com.example.paging3compose.ui.theme.Jost
import com.example.paging3compose.util.Const.TAG
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(/*weatherState: NetworkResponse<ImageDataModel>*/) {/* when (weatherState) {
         is NetworkResponse.Error -> {
             val errorState = weatherState as NetworkResponse.Error
             val errorMessage = when (errorState.errorType) {
                 ErrorType.NETWORK -> "Network error: ${errorState.message}"
                 ErrorType.TIMEOUT -> "Timeout error: ${errorState.message}"
                 ErrorType.UNKNOWN -> "Unknown error: ${errorState.message}"
             }
             // Show error message
             Text(text = errorMessage, color = Color.Red)

             Log.d(TAG, "HomeScreen: errorMessage = $errorMessage")
         }

         is NetworkResponse.Loading -> {
             CircularProgressIndicator()
         }

         is NetworkResponse.Success -> {
             val weatherData = weatherState.data
             // Display weather data

         }

         is NetworkResponse.DoNone -> {

         }
     }*/
    HomeData()
}

@Composable
fun HomeData(viewModel: WallpaperViewModel = hiltViewModel()) {

    val listState = rememberLazyListState()

    val lazyPagingItems = viewModel.pager.collectAsLazyPagingItems()

    LazyColumn(state = listState) {
        item {
            Text(
                text = "Hello, Fenil",
                style = TextStyle(
                    fontFamily = Jost,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = 34.sp
                ), modifier = Modifier.padding(start = 15.dp)
            )
        }
        Log.d(TAG, "HomeData: lazyPagingItems.itemCount = ${lazyPagingItems.itemCount}")
        items(lazyPagingItems.itemCount) { index ->
            val photo = lazyPagingItems[index] // Ensure safe access
            photo?.let { ItemFeed(it){} }
        }
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            is LoadState.Error -> item {
                val error = lazyPagingItems.loadState.refresh as LoadState.Error
                ErrorItem(message = error.error.localizedMessage ?: "Error", onClickRetry = { lazyPagingItems.retry() })
            }
            else -> Unit
        }
        /*when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }

            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }

            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                val e = lazyPagingItems.loadState.refresh as LoadState.Error
                item {
                    ErrorItem(message = e.error.localizedMessage ?: "Unknown Error",
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { lazyPagingItems.retry() })
                }
            }

            lazyPagingItems.loadState.append is LoadState.Error -> {
                val e = lazyPagingItems.loadState.append as LoadState.Error
                item {
                    ErrorItem(message = e.error.localizedMessage ?: "Unknown Error",
                        onClickRetry = { lazyPagingItems.retry() })
                }
            }

            else -> {
                Log.d(TAG, "HomeData: No errors, so no items to display ")
                // No errors, so no items to display
            }
        }*/

    }

}

@Composable
fun LoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Loading more...", color = MaterialTheme.colorScheme.primary)
    }
}


@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Loading...", color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            maxLines = 2,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(modifier = Modifier.padding(bottom = 100.dp), onClick = onClickRetry) {
            Text("Retry", color = Color.White)
        }
    }
}

@Composable
fun ItemFeed(item: PhotoEntity, onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    // Animate scale from 0.9x (shrink) to 1x (normal)
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
        label = "Click Animation"
    )

    // ✅ Properly reset animation after delay
    LaunchedEffect(isClicked) {
        if (isClicked) {
            delay(200) // Wait for animation to play
            isClicked = false // Reset scale
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(400.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .scale(scale) // Apply animation
            .clickable {
                isClicked = true
                onClick()
            }
    ) {
        BlurryToClearImage(item.original)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = item.photographer, style = TextStyle(
                    fontFamily = Frastha,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 36.sp
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.ic_save),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = item.width.toString(),
                    style = TextStyle(
                        fontFamily = Jost,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

val iOSRoundedShape: Shape = GenericShape { size, _ ->
    addRoundRect(
        RoundRect(
            rect = androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height),
            topLeft = CornerRadius(50f, 50f),
            topRight = CornerRadius(50f, 50f),
            bottomLeft = CornerRadius(50f, 50f),
            bottomRight = CornerRadius(50f, 50f)
        )
    )
}

@Composable
fun BlurryToClearImage(imageUrl: String) {
    var isFirstLoad by rememberSaveable { mutableStateOf(true) } // Persist state across recompositions

    // Animate blur from 20.dp to 0.dp only on first load
    val blurRadius by animateDpAsState(
        targetValue = if (isFirstLoad) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
        label = "Blur Animation"
    )

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
            .placeholder(R.drawable.dumy) // Placeholder while loading
            .error(R.drawable.dumy) // Error image if loading failsw
            .listener(onSuccess = { _, _ ->
                isFirstLoad = false
            }) // Set false only after first load
            .build(),
        contentDescription = "Sample Image",
        contentScale = ContentScale.FillBounds, // Stretches to parent size
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(iOSRoundedShape)
            .blur(blurRadius) // Apply blur animation
    )

}

@Composable
fun FadingImage(imageUrl: String) {
    var isImageLoaded by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (isImageLoaded) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "fade-in"
    )

    AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
        .build(),
        contentDescription = "Sample Image",
        modifier = Modifier
            .fillMaxWidth()
            .size(150.sdp)
            .padding(vertical = 5.sdp)
            .alpha(alphaAnim), // Apply fade animation
        onSuccess = { isImageLoaded = true } // Trigger fade-in after loading
    )
}
