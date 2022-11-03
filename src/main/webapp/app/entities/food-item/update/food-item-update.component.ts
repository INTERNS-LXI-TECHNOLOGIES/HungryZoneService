import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FoodItemFormService, FoodItemFormGroup } from './food-item-form.service';
import { IFoodItem } from '../food-item.model';
import { FoodItemService } from '../service/food-item.service';

@Component({
  selector: 'jhi-food-item-update',
  templateUrl: './food-item-update.component.html',
})
export class FoodItemUpdateComponent implements OnInit {
  isSaving = false;
  foodItem: IFoodItem | null = null;

  editForm: FoodItemFormGroup = this.foodItemFormService.createFoodItemFormGroup();

  constructor(
    protected foodItemService: FoodItemService,
    protected foodItemFormService: FoodItemFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foodItem }) => {
      this.foodItem = foodItem;
      if (foodItem) {
        this.updateForm(foodItem);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const foodItem = this.foodItemFormService.getFoodItem(this.editForm);
    if (foodItem.id !== null) {
      this.subscribeToSaveResponse(this.foodItemService.update(foodItem));
    } else {
      this.subscribeToSaveResponse(this.foodItemService.create(foodItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFoodItem>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(foodItem: IFoodItem): void {
    this.foodItem = foodItem;
    this.foodItemFormService.resetForm(this.editForm, foodItem);
  }
}
