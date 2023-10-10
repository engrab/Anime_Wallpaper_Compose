package com.example.anime.presentation.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.anime.data.model.Wallpaper
import com.example.anime.presentation.MainViewModel
import com.example.anime.presentation.bottom.BottomScreenRoute
import com.example.anime.presentation.drawer.AppBar
import com.example.anime.presentation.drawer.DrawerBody
import com.example.anime.presentation.drawer.DrawerHeader
import com.example.anime.presentation.drawer.MenuItem
import com.example.anime.presentation.screens.home.CategoryScreen
import com.example.anime.presentation.screens.home.FavouriteScreen
import com.example.anime.presentation.screens.home.WallpaperScreen
import com.example.anime.presentation.screens.search.SearchScreen
import com.example.anime.presentation.screens.setWallpaper.Gallery
import com.example.anime.presentation.screens.setWallpaper.SetWallpaper
import com.example.anime.presentation.screens.splash.SplashScreen
import com.example.anime.presentation.screens.wallpaperList.CategoryWallpapers
//import com.google.accompanist.navigation.animation.AnimatedNavHost
//import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalPagerApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun App(
    context: Activity,
    showAds: () -> Unit
) {
    val navController = rememberAnimatedNavController()
    val systemUiController = rememberSystemUiController()
    val backgroundColor = MaterialTheme.colors.background
    SideEffect {
        systemUiController.setNavigationBarColor(backgroundColor)
        systemUiController.setStatusBarColor(backgroundColor)
    }
    val mainViewModel: MainViewModel = hiltViewModel()
    val wallpapers = mainViewModel.wallpapers.collectAsLazyPagingItems()
    val collections = mainViewModel.collections.collectAsLazyPagingItems()
    val favourites: List<Wallpaper> by remember { mainViewModel.favourites }
    val wallpaperListState = rememberLazyGridState()
    val favouriteListState = rememberLazyGridState()
    val collectionListState = rememberLazyListState()
    val pagerState = rememberPagerState()
    val screens = listOf(
        HomeScreenRoute.Wallpapers,
        HomeScreenRoute.Collections,
        HomeScreenRoute.Favourites,
        HomeScreenRoute.Saved
    )
    val showTopBar = remember {
        mutableStateOf(true)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val data = Uri.encode(uri.toString())
            navController.navigate(MainScreenRoute.Gallery.route.plus("/$data"))
        }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
//            AnimatedVisibility(
//                visible = currentRoute == MainScreenRoute.Home.route
//            ) {

//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .height(56.dp)) {

            if (showTopBar.value){
                AppBar(
                    navController = navController,
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            }


//                    Row(
//                        Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Icon(
//                            modifier = Modifier
//                                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
//                            painter = painterResource(id = screens[pagerState.currentPage].resId),
//                            contentDescription = "collections"
//                        )
//                        HorizontalPagerIndicator(
//                            pagerState = pagerState,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                        IconButton(onClick = {
//                            navController.navigate(MainScreenRoute.Search.route)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Rounded.Search,
//                                contentDescription = "Search"
//                            )
//                        }
//                    }
//                }
//            }
        },

        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            Divider(
                modifier = Modifier
                    .height(.5.dp)
                    .fillMaxWidth()
            )
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "share",
                        title = "Share App",
                        contentDescription = "Share App",
                        icon = Icons.Default.Share
                    ),
                    MenuItem(
                        id = "rate",
                        title = "Rate Us",
                        contentDescription = "Rate Us",
                        icon = Icons.Default.StarRate
                    ),
                    MenuItem(
                        id = "privacy",
                        title = "Privacy Policy",
                        contentDescription = "Privacy Policy",
                        icon = Icons.Default.PrivacyTip
                    ),
                ),
                onItemClick = {
                    when (it.id) {
                        "share" -> {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_SUBJECT, "Wallpaper")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Download Wallpaper App and make your phone more attractive \n https://play.google.com/store/apps/details?id=${context.packageName}"
                                )
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }

                        "rate" -> {
                            try {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=${context.packageName}")
                                    )
                                )

                            } catch (e: Exception) {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                                    )
                                )
                            }
                        }

                        "privacy" -> {
                            val browserIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                            context.startActivity(browserIntent)
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = navController, showTopBar) }

    ) {
        NavHost(
            navController = navController,
            startDestination = MainScreenRoute.Splash.route
        ) {
            composable(MainScreenRoute.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(HomeScreenRoute.Wallpapers.route) {
                WallpaperScreen(
                    viewModel = mainViewModel,
                    wallpapers = wallpapers,
                    favourites = favourites,
                    listState = wallpaperListState,
                    navController = navController
                )
            }
            composable(HomeScreenRoute.Collections.route) {
                CategoryScreen(
                    collections = collections,
                    listState = collectionListState,
                    navController = navController
                )
            }
            composable(HomeScreenRoute.Favourites.route) {
                FavouriteScreen(
                    viewModel = mainViewModel,
                    favourites = favourites,
                    listState = favouriteListState,
                    navController = navController
                )
            }

//            composable(HomeScreenRoute.Saved.route) {
//                ShowImages(navHostController = navController)
//            }

//            composable(route = MainScreenRoute.Home.route) {
//                HorizontalPager(count = screens.size, state = pagerState) { page ->
//                    when (screens[page]) {
//                        HomeScreenRoute.Wallpapers -> {
//                            WallpaperScreen(
//                                viewModel = mainViewModel,
//                                wallpapers = wallpapers,
//                                favourites = favourites,
//                                listState = wallpaperListState,
//                                navController = navController
//                            )
//                        }
//                        HomeScreenRoute.Collections -> {
//                            CategoryScreen(
//                                collections = collections,
//                                listState = collectionListState,
//                                navController = navController
//                            )
//                        }
//                        HomeScreenRoute.Favourites -> {
//                            FavouriteScreen(
//                                viewModel = mainViewModel,
//                                favourites = favourites,
//                                listState = favouriteListState,
//                                navController = navController
//                            )
//                        }
//                        HomeScreenRoute.Saved -> {
//                            ShowImages(navHostController = navController)
//                        }
//
//                    }
//                }
//            }
//            composable(
//                route = MainScreenRoute.Search.route,
////                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
////                exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
//            ) {
//                SearchScreen(
//                    navController = navController,
//                    favourites = favourites,
//                    addFavourite = { mainViewModel.addFavourite(it) },
//                    removeFavourite = { mainViewModel.removeFavourite(it) }
//                )
//            }
//            composable(
//                route = MainScreenRoute.Gallery.route.plus("/{imageUri}"),
////                enterTransition = { FavouritesAnimations.enterAnimation(this.initialState) },
////                exitTransition = { FavouritesAnimations.exitAnimation(this.targetState) },
//                arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
//            ) { backStack ->
//                backStack.arguments?.getString("imageUri")?.let { u ->
//                    val uri = Uri.parse(u)
//                    Gallery(uri, navController)
//                }
//            }
//            composable(
//                route = MainScreenRoute.SetWallpaper.route.plus("/{wallpaper}"),
////                enterTransition = { SetWallpaperAnimations.enterAnimation() },
////                exitTransition = { SetWallpaperAnimations.exitAnimation() },
//                arguments = listOf(navArgument("wallpaper") { type = NavType.StringType })
//            ) { backStack ->
//                backStack.arguments?.getString("wallpaper")?.let { w ->
//                    val wallpaper = Gson().fromJson(w, Wallpaper::class.java)
//                    SetWallpaper(
//                        navController = navController,
//                        wallpaper = wallpaper,
//                        favourites = favourites,
//                        addFavourite = { mainViewModel.addFavourite(it) },
//                        removeFavourite = { mainViewModel.removeFavourite(it) },
//                        showAds = { showAds() }
//                    )
//                }
//            }
//            composable(
//                route = MainScreenRoute.CategoryWallpaper.route.plus("/{collectionId}/{title}"),
////                enterTransition = { expandIn() },
////                exitTransition = { shrinkOut() },
//                arguments = listOf(
//                    navArgument("collectionId") { type = NavType.StringType },
//                    navArgument("title") { type = NavType.StringType }
//                )
//            ) { backStack ->
//                backStack.arguments?.getString("collectionId")?.let { id ->
//                    val title = backStack.arguments?.getString("title")
//                    CategoryWallpapers(
//                        collectionId = id,
//                        collectionName = title ?: "",
//                        favourites = favourites,
//                        addFavourite = { mainViewModel.addFavourite(it) },
//                        removeFavourite = { mainViewModel.removeFavourite(it) },
//                        navController = navController
//                    )
//                }
//            }
            detailsNavGraph(navController = navController, favourites= favourites, mainViewModel = mainViewModel, showAds = {showAds()})
        }
    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalCoilApi::class
)
fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController,
    favourites:List<Wallpaper>,
    mainViewModel:MainViewModel,
    showAds: () -> Unit) {
    navigation(
        route = "Main",
        startDestination = MainScreenRoute.Search.route
    ) {
        composable(
            route = MainScreenRoute.Search.route,
//                enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
//                exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
        ) {
            SearchScreen(
                navController = navController,
                favourites = favourites,
                addFavourite = { mainViewModel.addFavourite(it) },
                removeFavourite = { mainViewModel.removeFavourite(it) }
            )
        }
        composable(
            route = MainScreenRoute.Gallery.route.plus("/{imageUri}"),
//                enterTransition = { FavouritesAnimations.enterAnimation(this.initialState) },
//                exitTransition = { FavouritesAnimations.exitAnimation(this.targetState) },
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStack ->
            backStack.arguments?.getString("imageUri")?.let { u ->
                val uri = Uri.parse(u)
                Gallery(uri, navController)
            }
        }
        composable(
            route = MainScreenRoute.SetWallpaper.route.plus("/{wallpaper}"),
//                enterTransition = { SetWallpaperAnimations.enterAnimation() },
//                exitTransition = { SetWallpaperAnimations.exitAnimation() },
            arguments = listOf(navArgument("wallpaper") { type = NavType.StringType })
        ) { backStack ->
            backStack.arguments?.getString("wallpaper")?.let { w ->
                val wallpaper = Gson().fromJson(w, Wallpaper::class.java)
                SetWallpaper(
                    navController = navController,
                    wallpaper = wallpaper,
                    favourites = favourites,
                    addFavourite = { mainViewModel.addFavourite(it) },
                    removeFavourite = { mainViewModel.removeFavourite(it) },
                    showAds = { showAds() }
                )
            }
        }
        composable(
            route = MainScreenRoute.CategoryWallpaper.route.plus("/{collectionId}/{title}"),
//                enterTransition = { expandIn() },
//                exitTransition = { shrinkOut() },
            arguments = listOf(
                navArgument("collectionId") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStack ->
            backStack.arguments?.getString("collectionId")?.let { id ->
                val title = backStack.arguments?.getString("title")
                CategoryWallpapers(
                    collectionId = id,
                    collectionName = title ?: "",
                    favourites = favourites,
                    addFavourite = { mainViewModel.addFavourite(it) },
                    removeFavourite = { mainViewModel.removeFavourite(it) },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, hideTopBar: MutableState<Boolean>) {
    val screens = listOf(
        BottomScreenRoute.Wallpapers,
        BottomScreenRoute.Collections,
        BottomScreenRoute.Favourites
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        hideTopBar.value = true
        BottomNavigation {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }else{
        hideTopBar.value = false
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomScreenRoute,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.resId,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}






