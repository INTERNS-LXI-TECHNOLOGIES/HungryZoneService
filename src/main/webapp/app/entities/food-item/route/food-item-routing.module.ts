import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FoodItemComponent } from '../list/food-item.component';
import { FoodItemDetailComponent } from '../detail/food-item-detail.component';
import { FoodItemUpdateComponent } from '../update/food-item-update.component';
import { FoodItemRoutingResolveService } from './food-item-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const foodItemRoute: Routes = [
  {
    path: '',
    component: FoodItemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FoodItemDetailComponent,
    resolve: {
      foodItem: FoodItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FoodItemUpdateComponent,
    resolve: {
      foodItem: FoodItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FoodItemUpdateComponent,
    resolve: {
      foodItem: FoodItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(foodItemRoute)],
  exports: [RouterModule],
})
export class FoodItemRoutingModule {}
