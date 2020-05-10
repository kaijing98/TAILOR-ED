import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { LandingComponent } from './landing/landing.component';
import { StoryboardComponent } from './pages/storyboard/storyboard.component';
import { LoginComponent } from './pages/login/login.component';
import { ShopArtworkComponent } from './pages/shopArtwork/shopArtwork.component';
import { ShopSelfCareBoxComponent } from './pages/shopSelfCareBoxes/shopSelfCareBoxes.component';
import { ViewSelfCareBoxDetailComponent } from './pages/view-selfCareBox-detail/view-selfCareBox-detail.component';
import { ShoppingCartComponent } from './pages/shopping-cart/shopping-cart.component';
import { RegisterCustomerComponent } from './pages/register-customer/register-customer.component';
import { ViewAllMyPostsComponent } from './pages/view-all-my-posts/view-all-my-posts.component';
import { UpdatePostComponent } from './pages/update-post/update-post.component';
import { DeletePostComponent } from './pages/delete-post/delete-post.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';

import { ViewArtworkDetailComponent } from './pages/view-artwork-detail/view-artwork-detail.component';
import { FilterPostComponent } from './pages/filter-post/filter-post.component';
import { FilterArtworkComponent } from './pages/filter-artwork/filter-artwork.component';
import { FilterSelfCareBoxComponent } from './pages/filter-selfCareBox/filter-selfCareBox.component';
import { ViewAllMyOrdersComponent } from './pages/view-all-my-orders/view-all-my-orders.component';


const routes: Routes =[
    { path: '', redirectTo: 'index', pathMatch: 'full' },
    { path: 'index',                component: LandingComponent },
    { path: 'pages/storyboard',     component: StoryboardComponent },
    { path: 'pages/login',       component: LoginComponent },
    { path: 'pages/shopArtwork',     component: ShopArtworkComponent },
    { path: 'pages/shopSelfCareBoxes',     component: ShopSelfCareBoxComponent },
    { path: 'pages/viewSelfCareBoxDetails/:selfCareBoxID', component: ViewSelfCareBoxDetailComponent},
    { path: 'pages/shoppingCart', component: ShoppingCartComponent },
    { path: 'pages/registerCustomer', component: RegisterCustomerComponent},
    { path: 'pages/viewArtworkDetails/:artworkID', component: ViewArtworkDetailComponent},
    { path: 'pages/viewAllMyPosts', component: ViewAllMyPostsComponent },
    { path: 'pages/updatePost', component: UpdatePostComponent },
    { path: 'pages/createPost', component: CreatePostComponent },
    { path: 'pages/updatePost/:postId', component: UpdatePostComponent },
    { path: 'pages/deletePost', component: DeletePostComponent },
    { path: 'pages/deletePost/:postId', component: DeletePostComponent },
    { path: 'pages/filterPost', component: FilterPostComponent },
    { path: 'pages/filterPost/:tagId', component: FilterPostComponent },
    { path: 'pages/filterArtwork', component: FilterArtworkComponent },
    { path: 'pages/filterArtwork/:tagId', component: FilterArtworkComponent },
    { path: 'pages/filterSelfCareBox', component: FilterSelfCareBoxComponent },
    { path: 'pages/filterSelfCareBox/:tagId', component: FilterSelfCareBoxComponent },
    { path: 'pages/viewAllMyOrders', component: ViewAllMyOrdersComponent },

];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
})
export class AppRoutingModule { }
