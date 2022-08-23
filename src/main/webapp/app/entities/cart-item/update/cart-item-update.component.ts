import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CartItemFormService, CartItemFormGroup } from './cart-item-form.service';
import { ICartItem } from '../cart-item.model';
import { CartItemService } from '../service/cart-item.service';
import { IFood } from 'app/entities/food/food.model';
import { FoodService } from 'app/entities/food/service/food.service';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

@Component({
  selector: 'jhi-cart-item-update',
  templateUrl: './cart-item-update.component.html',
})
export class CartItemUpdateComponent implements OnInit {
  isSaving = false;
  cartItem: ICartItem | null = null;

  foodsSharedCollection: IFood[] = [];
  cartsSharedCollection: ICart[] = [];

  editForm: CartItemFormGroup = this.cartItemFormService.createCartItemFormGroup();

  constructor(
    protected cartItemService: CartItemService,
    protected cartItemFormService: CartItemFormService,
    protected foodService: FoodService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFood = (o1: IFood | null, o2: IFood | null): boolean => this.foodService.compareFood(o1, o2);

  compareCart = (o1: ICart | null, o2: ICart | null): boolean => this.cartService.compareCart(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartItem }) => {
      this.cartItem = cartItem;
      if (cartItem) {
        this.updateForm(cartItem);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cartItem = this.cartItemFormService.getCartItem(this.editForm);
    if (cartItem.id !== null) {
      this.subscribeToSaveResponse(this.cartItemService.update(cartItem));
    } else {
      this.subscribeToSaveResponse(this.cartItemService.create(cartItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICartItem>>): void {
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

  protected updateForm(cartItem: ICartItem): void {
    this.cartItem = cartItem;
    this.cartItemFormService.resetForm(this.editForm, cartItem);

    this.foodsSharedCollection = this.foodService.addFoodToCollectionIfMissing<IFood>(this.foodsSharedCollection, cartItem.food);
    this.cartsSharedCollection = this.cartService.addCartToCollectionIfMissing<ICart>(this.cartsSharedCollection, cartItem.cart);
  }

  protected loadRelationshipsOptions(): void {
    this.foodService
      .query()
      .pipe(map((res: HttpResponse<IFood[]>) => res.body ?? []))
      .pipe(map((foods: IFood[]) => this.foodService.addFoodToCollectionIfMissing<IFood>(foods, this.cartItem?.food)))
      .subscribe((foods: IFood[]) => (this.foodsSharedCollection = foods));

    this.cartService
      .query()
      .pipe(map((res: HttpResponse<ICart[]>) => res.body ?? []))
      .pipe(map((carts: ICart[]) => this.cartService.addCartToCollectionIfMissing<ICart>(carts, this.cartItem?.cart)))
      .subscribe((carts: ICart[]) => (this.cartsSharedCollection = carts));
  }
}
