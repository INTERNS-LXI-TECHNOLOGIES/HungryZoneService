import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../food-item.test-samples';

import { FoodItemFormService } from './food-item-form.service';

describe('FoodItem Form Service', () => {
  let service: FoodItemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FoodItemFormService);
  });

  describe('Service methods', () => {
    describe('createFoodItemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFoodItemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quandity: expect.any(Object),
            unit: expect.any(Object),
          })
        );
      });

      it('passing IFoodItem should create a new form with FormGroup', () => {
        const formGroup = service.createFoodItemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quandity: expect.any(Object),
            unit: expect.any(Object),
          })
        );
      });
    });

    describe('getFoodItem', () => {
      it('should return NewFoodItem for default FoodItem initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFoodItemFormGroup(sampleWithNewData);

        const foodItem = service.getFoodItem(formGroup) as any;

        expect(foodItem).toMatchObject(sampleWithNewData);
      });

      it('should return NewFoodItem for empty FoodItem initial value', () => {
        const formGroup = service.createFoodItemFormGroup();

        const foodItem = service.getFoodItem(formGroup) as any;

        expect(foodItem).toMatchObject({});
      });

      it('should return IFoodItem', () => {
        const formGroup = service.createFoodItemFormGroup(sampleWithRequiredData);

        const foodItem = service.getFoodItem(formGroup) as any;

        expect(foodItem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFoodItem should not enable id FormControl', () => {
        const formGroup = service.createFoodItemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFoodItem should disable id FormControl', () => {
        const formGroup = service.createFoodItemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
