import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFoodItem } from '../food-item.model';
import { FoodItemService } from '../service/food-item.service';

@Injectable({ providedIn: 'root' })
export class FoodItemRoutingResolveService implements Resolve<IFoodItem | null> {
  constructor(protected service: FoodItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFoodItem | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((foodItem: HttpResponse<IFoodItem>) => {
          if (foodItem.body) {
            return of(foodItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
