import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FoodItemComponent } from './list/food-item.component';
import { FoodItemDetailComponent } from './detail/food-item-detail.component';
import { FoodItemUpdateComponent } from './update/food-item-update.component';
import { FoodItemDeleteDialogComponent } from './delete/food-item-delete-dialog.component';
import { FoodItemRoutingModule } from './route/food-item-routing.module';

@NgModule({
  imports: [SharedModule, FoodItemRoutingModule],
  declarations: [FoodItemComponent, FoodItemDetailComponent, FoodItemUpdateComponent, FoodItemDeleteDialogComponent],
})
export class FoodItemModule {}
