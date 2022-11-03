import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFoodItem } from '../food-item.model';
import { FoodItemService } from '../service/food-item.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './food-item-delete-dialog.component.html',
})
export class FoodItemDeleteDialogComponent {
  foodItem?: IFoodItem;

  constructor(protected foodItemService: FoodItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.foodItemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
