package com.example.layoutcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.layoutcompose.ui.page.AdCard
import com.example.layoutcompose.ui.page.GoodsPager
import com.example.layoutcompose.ui.page.KingKongCard
import com.example.layoutcompose.ui.page.PointsCard
import com.example.layoutcompose.ui.page.TabLayout
import com.example.layoutcompose.ui.page.TaskCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Home()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home() {
    val listState = rememberLazyListState()
    val pagerState = rememberPagerState()
    val tabState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(10.dp)
    ) {
        item { PointsCard() }
        item { KingKongCard() }
        item { TaskCard() }
        item { AdCard() }
        stickyHeader { TabLayout(pagerState, tabState) }
        item { GoodsPager(pagerState, tabState, listState) }
    }
}