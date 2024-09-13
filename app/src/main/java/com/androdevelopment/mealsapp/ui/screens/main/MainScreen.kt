package com.androdevelopment.mealsapp.ui.screens.main

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.androdevelopment.domain.entity.meals.MealEntity
import com.androdevelopment.mealsapp.R
import com.androdevelopment.mealsapp.ui.theme.Roboto

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onEvent: (MainScreenUiEvents) -> Unit,
) {

    MainScreenContent(
        modifier = modifier,
        state = state,
        onEvent = onEvent
    )

}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onEvent: (MainScreenUiEvents) -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF212121)),
    ) {

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item(span = { GridItemSpan(2) }) {
                Top()
            }
            item(span = { GridItemSpan(2) }) {
                Column {
                    Pager(state, modifier, onEvent)
                }
            }
            item(span = { GridItemSpan(2) }) {
                Column {
                    Categories(state, onEvent)
                }
            }
            items(state.meals.shuffled(), key = { it.mealId }) { meal ->
                MealItem(onEvent, meal)
            }

        }
    }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if (state.isLoading) CircularProgressIndicator(
                color = Color(0xFFf2661e),
                strokeCap = StrokeCap.Butt
            )
        }

}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridItemScope.MealItem(
    onEvent: (MainScreenUiEvents) -> Unit,
    meal: MealEntity,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(300.dp)
            .animateItem()
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF313131))
                .align(Alignment.BottomCenter)
                .clickable {
                    onEvent(MainScreenUiEvents.OnMealClick(meal.mealId))
                },
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = meal.mealName ?: "Unknown Meal",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = Roboto,
                    fontWeight = FontWeight.W400,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                meal.ingredients.forEach { ingredient ->
                    Text(
                        text = ingredient,
                        fontSize = 12.sp,
                        color = Color(0xFFa8a8a8),
                        fontFamily = Roboto,
                        modifier = Modifier.padding(top = 4.dp)

                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        Icons.Rounded.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFFec641d),
                        modifier = Modifier
                            .size(18.dp)
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text = meal.area ?: "Unknown Area",
                        fontSize = 12.sp,
                        color = Color(0xFFa8a8a8),
                        fontFamily = Roboto,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
        AsyncImage(
            model = meal.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(32.dp)
                .clip(RoundedCornerShape(12.dp))
                .heightIn(max = 150.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Categories(
    state: MainScreenState,
    onEvent: (MainScreenUiEvents) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Meal Category",
            fontSize = 22.sp,
            color = Color.White,
            fontFamily = Roboto,
            fontWeight = FontWeight.Medium
        )
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.categories) { category ->
            val isSelected = state.selectedCategory == category.categoryName
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color(0xFFf4661e) else Color(0xFF313131))
                    .clickable {
                        onEvent(MainScreenUiEvents.OnCategoryClick(category.categoryName))
                    }
                    .padding(8.dp)
                    .animateItemPlacement(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = category.categoryThumbUrl,
                    contentDescription = category.categoryName,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(22.dp)
                )

                Spacer(Modifier.width(2.dp))
                Text(
                    text = category.categoryName,
                    fontSize = 14.sp,
                    color = if (isSelected) Color(0xFF212121) else Color.White,
                    fontFamily = Roboto
                )
            }
        }
    }
}

@Composable
private fun Pager(
    state: MainScreenState,
    modifier: Modifier,
    onEvent: (MainScreenUiEvents) -> Unit,
) {
    val pagerState = rememberPagerState { state.randomMeals.size }
    HorizontalPager(
        state = pagerState
    ) {
        val meal = state.randomMeals[it]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF313131))
                .clickable {
                    onEvent(MainScreenUiEvents.OnMealClick(meal.mealId))
                }
                .padding(16.dp)
        ) {
            MealDescription(meal)

            Spacer(modifier.width(6.dp))

            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.mealName,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.FillBounds
            )
        }
    }

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) {
            val color = if (pagerState.currentPage == it) Color(0xFFf2661e) else Color(0xFF616161)
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp),
            )
            Spacer(Modifier.width(1.dp))

        }
    }
}

@Composable
private fun RowScope.MealDescription(meal: MealEntity) {
    Column(
        modifier = Modifier
            .weight(1f)
    ) {
        Text(
            text = "Popular",
            fontSize = 14.sp,
            color = Color(0xFFa8a8a8),
            fontFamily = Roboto
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = meal.mealName ?: "Unknown Meal",
            fontSize = 22.sp,
            color = Color.White,
            fontFamily = Roboto
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.fire),
                contentDescription = null,
                tint = Color(0xFFf66a1b)
            )

            Spacer(Modifier.width(1.dp))

            Text(
                text = meal.tags ?: "Unknown Tag",
                fontSize = 14.sp,
                color = Color(0xFF8f8f8f),
                fontFamily = Roboto
            )
        }
    }
}

@Composable
private fun Top() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hey,",
                fontSize = 22.sp,
                color = Color.White,
                fontFamily = Roboto
            )

            Text(
                text = "Ready to cook something?",
                fontSize = 16.sp,
                color = Color(0xFFb8b8b8),
                fontFamily = Roboto
            )
        }

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFe69e37))
        )
    }
}

