package com.example.layoutcompose.ui.page

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.math.abs

@Composable
fun PointsCard() {
    Box(
        modifier = Modifier
            .height(150.dp)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
    ) {
        Text(
            text = "Item 头部",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}

@Composable
fun KingKongCard() {
    Box(
        modifier = Modifier
            .height(180.dp)
            .padding(top = 10.dp)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp)
                        .background(Color.Magenta, RoundedCornerShape(5.dp))
                ) {
                    Text(
                        text = "Item $it",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun TaskCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
            .height(200.dp)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Magenta, RoundedCornerShape(5.dp))
                .weight(1f)
        ) {
            TextHint("0")
        }
        Spacer(modifier = Modifier.width(10.dp))
        // Second child
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Magenta, RoundedCornerShape(5.dp))
                    .weight(1f)
            ) {
                TextHint("1")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Magenta, RoundedCornerShape(5.dp))
                    .weight(1f)
            ) {
                TextHint("2")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdCard() {
    val colors = arrayOf(Color.Yellow, Color.Cyan)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val timer = remember { Timer() }
    val timerTask = remember {
        object : TimerTask() {
            override fun run() {
                scope.launch {
                    pagerState.animateScrollToPage((pagerState.currentPage + 1) % 2)
                }
            }
        }
    }
    DisposableEffect(Unit) {
        timer.schedule(timerTask, 3000, 3000)
        onDispose {
            timer.cancel()
        }
    }
    HorizontalPager(
        pageCount = 2,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
            .height(120.dp)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors[it], RoundedCornerShape(5.dp))
        ) {
            TextHint("$it, I am AdView ")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(pagerState: PagerState, lazyListState: LazyListState) {
    val scope = rememberCoroutineScope()
    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(Color.LightGray, RoundedCornerShape(5.dp))
            .height(50.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                            lazyListState.animateScrollToItem(it)
                        }
                    }
                    .background(
                        if (pagerState.currentPage == it) Color.Yellow else Color.Magenta,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(5.dp)
            ) {
                TextHint(it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoodsPager(pagerState: PagerState, tabState: LazyListState, parentListState: LazyListState) {
    val colors = arrayOf(
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Green,
        Color.Blue,
        Color.Red,
        Color.Gray,
        Color.DarkGray,
        Color.White,
        Color.LightGray
    )
    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect {
            tabState.animateScrollToItem(it)
        }
    }
    HorizontalPager(
        pageCount = 10,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(800.dp) //The height needs to be calculated based on  your needs,and it is required
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors[it], RoundedCornerShape(5.dp))
        ) {
            GoodsList(
                pageIndex = it,
                lazyListState = parentListState
            )
        }
    }
}

@Composable
fun GoodsList(pageIndex: Int, lazyListState: LazyListState) {
    val scope = rememberCoroutineScope()
    val currentPager = rememberLazyGridState()
    val connection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                "Child LazyColumn==>PostScroll: consumed = $consumed, available = $available, source = $source".loge()
                return super.onPostScroll(consumed, available, source)
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                "Child LazyColumn==>onPreScroll:available = $available, source = $source".loge()
                return if (lazyListState.firstVisibleItemIndex != 4) {
                    if (available.y < 0) {
                        scope.launch {
                            lazyListState.scrollBy(-available.y)
                        }
                        Offset(0f, available.y)
                    } else {
                        Offset.Zero
                    }
                } else {
                    Offset.Zero
                }
            }
        }
    }
    LazyVerticalGrid(
        state = currentPager,
        modifier = Modifier
            .nestedScroll(connection),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(50) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Magenta, RoundedCornerShape(5.dp))
            ) {
                TextHint("$it PagerIndex:$pageIndex ")
            }
        }
    }
}


@Composable
private fun BoxScope.TextHint(it: Any? = "Nothing") {
    Text(
        text = "Item ${it.toString()}",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
    )
}

@Composable
fun Modifier.swipeEvent(onSwipe: (Float, Float) -> Unit) =
    pointerInput(Unit) {
        awaitEachGesture {
            val down = awaitFirstDown(pass = PointerEventPass.Final)
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Final)
                if (event.type == PointerEventType.Move) {
                    val pos = event.changes[0].position
                    if (pos.x < 0 || pos.x > size.width || pos.y < 0 || pos.y > size.height) { //如果越界了，就不再处理
                        break
                    } else {
                        onSwipe(
                            pos.x - down.position.x,
                            pos.y - down.position.y
                        ) //x轴大于y轴，就是水平滑动
                    }
                }
            }
        }
//    detectDragGestures { change, dragAmount ->
//        if (abs(dragAmount.x) > abs(dragAmount.y)) {
//            if (dragAmount.x > 0) {
//                scope.launch {
//                    if (pageIndex > 0) {
//                        pagerState.animateScrollToPage(pageIndex - 1)
//                    }
//                }
//            } else {
//                if (pageIndex < pageSize) {
//                    scope.launch {
//                        pagerState.animateScrollToPage(pageIndex + 1)
//                    }
//                }
//            }
//            return@detectDragGestures
//        }
//    }

    }


fun Any?.loge() {
//    Log.e("TAG", this.toString())
}

fun Any?.logd() {
//    Log.d("TAG", this.toString())
}

fun Any?.logi() {
    Log.w("TAG", this.toString())
}


