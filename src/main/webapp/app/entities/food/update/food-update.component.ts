import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FoodFormService, FoodFormGroup } from './food-form.service';
import { IFood } from '../food.model';
import { FoodService } from '../service/food.service';
import { IFoodItem } from 'app/entities/food-item/food-item.model';
import { FoodItemService } from 'app/entities/food-item/service/food-item.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-food-update',
  templateUrl: './food-update.component.html',
})
export class FoodUpdateComponent implements OnInit {
  isSaving = false;
  food: IFood | null = null;

  foodItemsSharedCollection: IFoodItem[] = [];
  categoriesSharedCollection: ICategory[] = [];
  userExtrasSharedCollection: IUserExtra[] = [];

  editForm: FoodFormGroup = this.foodFormService.createFoodFormGroup();

  constructor(
    protected foodService: FoodService,
    protected foodFormService: FoodFormService,
    protected foodItemService: FoodItemService,
    protected categoryService: CategoryService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFoodItem = (o1: IFoodItem | null, o2: IFoodItem | null): boolean => this.foodItemService.compareFoodItem(o1, o2);

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  compareUserExtra = (o1: IUserExtra | null, o2: IUserExtra | null): boolean => this.userExtraService.compareUserExtra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ food }) => {
      this.food = food;
      if (food) {
        this.updateForm(food);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const food = this.foodFormService.getFood(this.editForm);
    if (food.id !== null) {
      this.subscribeToSaveResponse(this.foodService.update(food));
    } else {
      this.subscribeToSaveResponse(this.foodService.create(food));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFood>>): void {
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

  protected updateForm(food: IFood): void {
    this.food = food;
    this.foodFormService.resetForm(this.editForm, food);

    this.foodItemsSharedCollection = this.foodItemService.addFoodItemToCollectionIfMissing<IFoodItem>(
      this.foodItemsSharedCollection,
      food.food
    );
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      food.category
    );
    this.userExtrasSharedCollection = this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(
      this.userExtrasSharedCollection,
      food.donor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.foodItemService
      .query()
      .pipe(map((res: HttpResponse<IFoodItem[]>) => res.body ?? []))
      .pipe(map((foodItems: IFoodItem[]) => this.foodItemService.addFoodItemToCollectionIfMissing<IFoodItem>(foodItems, this.food?.food)))
      .subscribe((foodItems: IFoodItem[]) => (this.foodItemsSharedCollection = foodItems));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) => this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.food?.category))
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));

    this.userExtraService
      .query()
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) => this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(userExtras, this.food?.donor))
      )
      .subscribe((userExtras: IUserExtra[]) => (this.userExtrasSharedCollection = userExtras));
  }
}
