import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFoodItem } from '../food-item.model';

@Component({
  selector: 'jhi-food-item-detail',
  templateUrl: './food-item-detail.component.html',
})
export class FoodItemDetailComponent implements OnInit {
  foodItem: IFoodItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ foodItem }) => {
      this.foodItem = foodItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
