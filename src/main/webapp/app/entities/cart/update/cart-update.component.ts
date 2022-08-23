import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CartFormService, CartFormGroup } from './cart-form.service';
import { ICart } from '../cart.model';
import { CartService } from '../service/cart.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html',
})
export class CartUpdateComponent implements OnInit {
  isSaving = false;
  cart: ICart | null = null;

  usersCollection: IUserExtra[] = [];

  editForm: CartFormGroup = this.cartFormService.createCartFormGroup();

  constructor(
    protected cartService: CartService,
    protected cartFormService: CartFormService,
    protected userExtraService: UserExtraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUserExtra = (o1: IUserExtra | null, o2: IUserExtra | null): boolean => this.userExtraService.compareUserExtra(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cart }) => {
      this.cart = cart;
      if (cart) {
        this.updateForm(cart);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cart = this.cartFormService.getCart(this.editForm);
    if (cart.id !== null) {
      this.subscribeToSaveResponse(this.cartService.update(cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(cart));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>): void {
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

  protected updateForm(cart: ICart): void {
    this.cart = cart;
    this.cartFormService.resetForm(this.editForm, cart);

    this.usersCollection = this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(this.usersCollection, cart.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userExtraService
      .query({ filter: 'cart-is-null' })
      .pipe(map((res: HttpResponse<IUserExtra[]>) => res.body ?? []))
      .pipe(
        map((userExtras: IUserExtra[]) => this.userExtraService.addUserExtraToCollectionIfMissing<IUserExtra>(userExtras, this.cart?.user))
      )
      .subscribe((userExtras: IUserExtra[]) => (this.usersCollection = userExtras));
  }
}
