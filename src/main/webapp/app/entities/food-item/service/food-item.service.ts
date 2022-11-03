import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFoodItem, NewFoodItem } from '../food-item.model';

export type PartialUpdateFoodItem = Partial<IFoodItem> & Pick<IFoodItem, 'id'>;

export type EntityResponseType = HttpResponse<IFoodItem>;
export type EntityArrayResponseType = HttpResponse<IFoodItem[]>;

@Injectable({ providedIn: 'root' })
export class FoodItemService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/food-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(foodItem: NewFoodItem): Observable<EntityResponseType> {
    return this.http.post<IFoodItem>(this.resourceUrl, foodItem, { observe: 'response' });
  }

  update(foodItem: IFoodItem): Observable<EntityResponseType> {
    return this.http.put<IFoodItem>(`${this.resourceUrl}/${this.getFoodItemIdentifier(foodItem)}`, foodItem, { observe: 'response' });
  }

  partialUpdate(foodItem: PartialUpdateFoodItem): Observable<EntityResponseType> {
    return this.http.patch<IFoodItem>(`${this.resourceUrl}/${this.getFoodItemIdentifier(foodItem)}`, foodItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFoodItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFoodItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFoodItemIdentifier(foodItem: Pick<IFoodItem, 'id'>): number {
    return foodItem.id;
  }

  compareFoodItem(o1: Pick<IFoodItem, 'id'> | null, o2: Pick<IFoodItem, 'id'> | null): boolean {
    return o1 && o2 ? this.getFoodItemIdentifier(o1) === this.getFoodItemIdentifier(o2) : o1 === o2;
  }

  addFoodItemToCollectionIfMissing<Type extends Pick<IFoodItem, 'id'>>(
    foodItemCollection: Type[],
    ...foodItemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const foodItems: Type[] = foodItemsToCheck.filter(isPresent);
    if (foodItems.length > 0) {
      const foodItemCollectionIdentifiers = foodItemCollection.map(foodItemItem => this.getFoodItemIdentifier(foodItemItem)!);
      const foodItemsToAdd = foodItems.filter(foodItemItem => {
        const foodItemIdentifier = this.getFoodItemIdentifier(foodItemItem);
        if (foodItemCollectionIdentifiers.includes(foodItemIdentifier)) {
          return false;
        }
        foodItemCollectionIdentifiers.push(foodItemIdentifier);
        return true;
      });
      return [...foodItemsToAdd, ...foodItemCollection];
    }
    return foodItemCollection;
  }
}
