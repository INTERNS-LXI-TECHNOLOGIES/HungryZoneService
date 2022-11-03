import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFoodItem, NewFoodItem } from '../food-item.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFoodItem for edit and NewFoodItemFormGroupInput for create.
 */
type FoodItemFormGroupInput = IFoodItem | PartialWithRequiredKeyOf<NewFoodItem>;

type FoodItemFormDefaults = Pick<NewFoodItem, 'id'>;

type FoodItemFormGroupContent = {
  id: FormControl<IFoodItem['id'] | NewFoodItem['id']>;
  quandity: FormControl<IFoodItem['quandity']>;
  unit: FormControl<IFoodItem['unit']>;
};

export type FoodItemFormGroup = FormGroup<FoodItemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FoodItemFormService {
  createFoodItemFormGroup(foodItem: FoodItemFormGroupInput = { id: null }): FoodItemFormGroup {
    const foodItemRawValue = {
      ...this.getFormDefaults(),
      ...foodItem,
    };
    return new FormGroup<FoodItemFormGroupContent>({
      id: new FormControl(
        { value: foodItemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      quandity: new FormControl(foodItemRawValue.quandity, {
        validators: [Validators.required],
      }),
      unit: new FormControl(foodItemRawValue.unit, {
        validators: [Validators.required],
      }),
    });
  }

  getFoodItem(form: FoodItemFormGroup): IFoodItem | NewFoodItem {
    return form.getRawValue() as IFoodItem | NewFoodItem;
  }

  resetForm(form: FoodItemFormGroup, foodItem: FoodItemFormGroupInput): void {
    const foodItemRawValue = { ...this.getFormDefaults(), ...foodItem };
    form.reset(
      {
        ...foodItemRawValue,
        id: { value: foodItemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FoodItemFormDefaults {
    return {
      id: null,
    };
  }
}
