import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NouisliderModule } from 'ng2-nouislider';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { JwBootstrapSwitchNg2Module } from 'jw-bootstrap-switch-ng2';
import { AgmCoreModule } from '@agm/core';

import { StoryboardComponent } from './storyboard/storyboard.component';
import { LoginComponent } from './login/login.component';
import { ShopArtworkComponent } from './shopArtwork/shopArtwork.component';
import { ShopSelfCareBoxComponent } from './shopSelfCareBoxes/shopSelfCareBoxes.component';
import { ViewSelfCareBoxDetailComponent } from './view-selfCareBox-detail/view-selfCareBox-detail.component';
import { PagesComponent } from './pages.component';
import { RegisterCustomerComponent } from './register-customer/register-customer.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ViewArtworkDetailComponent } from './view-artwork-detail/view-artwork-detail.component';
import { RouterModule } from '@angular/router';
import { ViewAllMyPostsComponent } from './view-all-my-posts/view-all-my-posts.component';
import { UpdatePostComponent } from './update-post/update-post.component';
import { DeletePostComponent } from './delete-post/delete-post.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { FilterPostComponent } from './filter-post/filter-post.component';
import { FilterArtworkComponent } from './filter-artwork/filter-artwork.component';
import { FilterSelfCareBoxComponent } from './filter-selfCareBox/filter-selfCareBox.component';
import { ViewAllMyOrdersComponent } from './view-all-my-orders/view-all-my-orders.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbModule,
        NouisliderModule,
        JwBootstrapSwitchNg2Module,
        AgmCoreModule.forRoot({
            apiKey: 'YOUR_KEY_HERE'
        }),
        RouterModule.forChild([
            { path: 'pages/storyboard', component: StoryboardComponent }
            //{ path: 'pages/updatePost/:postId', component: UpdatePostComponent }
        ])
    ],
    declarations: [
        StoryboardComponent,
        LoginComponent,
        PagesComponent,
        ShopArtworkComponent,
        ShopSelfCareBoxComponent,
        ViewSelfCareBoxDetailComponent,
        RegisterCustomerComponent,
        ShoppingCartComponent,
        ViewArtworkDetailComponent,
        ViewAllMyPostsComponent,
        UpdatePostComponent,
        DeletePostComponent,
        CreatePostComponent,
        FilterPostComponent,
        FilterArtworkComponent,
        FilterSelfCareBoxComponent,
        ViewAllMyOrdersComponent
    ]
})
export class PagesModule { }
