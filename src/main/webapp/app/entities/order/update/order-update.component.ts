import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrderFormService, OrderFormGroup } from './order-form.service';
import { IOrder } from '../order.model';
import { OrderService } from '../service/order.service';
import { IFoodItem } from 'app/entities/food-item/food-item.model';
import { FoodItemService } from 'app/entities/food-item/service/food-item.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  order: IOrder | null = null;

  foodItemsSharedCollection: IFoodItem[] = [];
  userExtrasSharedCollection: IUserExtra[] = [];

  editForm: OrderFormGroup = this.orderFormService.createOrderFormGroup();

  constructor(
    protected orderService: OrderService,
    protected orderFormService: OrderFormService,
    protected foodItemService: FoodItemService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFoodItem = (o1: IFoodItem | null, o2: IFoodItem | null): boolean => this.foodItemService.compareFoodItem(o1, o2);

  compareUserExtra = (o1: IUserExtra | null, o2: IUserExtra | null): boolean => this.userExtraService.compareUserExtra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      this.order = order;
      if (order) {
        this.updateForm(order);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.orderFormService.getOrder(this.editForm);
    if (order.id !== null) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  protected updateForm(order: IOrder): void {
    this.order = order;
    this.orderFormService.resetForm(this.editForm, order);

    this.foodItemsSharedCollection = this.foodItemService.addFoodItemToCollectionIfMissing<IFoodItem>(
      this.foodItemsSharedCollection,
      order.food
    );
    this.userExtrasSharedCollection = this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(
      this.userExtrasSharedCollection,
      order.recipient
    );
  }

  protected loadRelationshipsOptions(): void {
    this.foodItemService
      .query()
      .pipe(map((res: HttpResponse<IFoodItem[]>) => res.body ?? []))
      .pipe(map((foodItems: IFoodItem[]) => this.foodItemService.addFoodItemToCollectionIfMissing<IFoodItem>(foodItems, this.order?.food)))
      .subscribe((foodItems: IFoodItem[]) => (this.foodItemsSharedCollection = foodItems));

    this.userExtraService
      .query()
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) =>
          this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(userExtras, this.order?.recipient)
        )
      )
      .subscribe((userExtras: IUserExtra[]) => (this.userExtrasSharedCollection = userExtras));
  }
}
